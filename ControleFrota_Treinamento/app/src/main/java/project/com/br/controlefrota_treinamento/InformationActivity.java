package project.com.br.controlefrota_treinamento;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.com.br.controlefrota_treinamento.enumeration.AvariasEnum;
import project.com.br.controlefrota_treinamento.enumeration.StatusTransacaoMotoristaEnum;
import project.com.br.controlefrota_treinamento.enumeration.StatusTransacaoVeiculoEnum;
import project.com.br.controlefrota_treinamento.enumeration.TipoTransacaoEnum;
import project.com.br.controlefrota_treinamento.model.Avaria;
import project.com.br.controlefrota_treinamento.model.Motorista;
import project.com.br.controlefrota_treinamento.model.Transacao;
import project.com.br.controlefrota_treinamento.model.Veiculo;
import project.com.br.controlefrota_treinamento.rest.model.Avarias;
import project.com.br.controlefrota_treinamento.rest.model.SincronizacaoRequest;
import project.com.br.controlefrota_treinamento.rest.model.SincronizacaoResponse;
import project.com.br.controlefrota_treinamento.rest.model.Transacoes;
import project.com.br.controlefrota_treinamento.rest.service.RestClient;
import project.com.br.controlefrota_treinamento.util.Constants;
import project.com.br.controlefrota_treinamento.util.FormatDateUtil;
import project.com.br.controlefrota_treinamento.util.MyPreferences;
import project.com.br.controlefrota_treinamento.util.Utils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mbueno on 18/08/2015.
 */
public class InformationActivity extends Activity {

    private ProgressBar bar;
    SharedPreferences myPreferences;
    private Context context;
    private Dialog dialog;
    private String unidadeApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView appVersion, lastUpdate;
        String lastUpdateFmt;
        Button btnSincronizar;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomation);

        myPreferences = getBaseContext().getSharedPreferences(MyPreferences.PREFERENCES_FILE, MODE_PRIVATE);
        lastUpdateFmt = (myPreferences.getString(MyPreferences.DATE_LAST_SINC, FormatDateUtil.formateDate(new Date())));
        unidadeApp = (myPreferences.getString(MyPreferences.UNIDADE_APLICACAO, "")).toUpperCase();

        if (getActionBar() != null) {
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        context = getApplicationContext();

        appVersion = (TextView) findViewById(R.id.app_version);
        appVersion.setText(Utils.getAppVersionName(getBaseContext()));

        lastUpdate = (TextView) findViewById(R.id.last_update);
        lastUpdate.setText(lastUpdateFmt);

        btnSincronizar = (Button) findViewById(R.id.btn_sincronizar);
        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isOnline(getBaseContext())) {
                    sincDialog();
                } else {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.lbl_conexao_indisponivel), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sincDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.synchronization_dialog, null);
        dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);

        bar = (ProgressBar) dialog.findViewById(R.id.progress_bar);
        bar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        dialog.show();
        bar.setVisibility(View.VISIBLE);

        sincronizar();
    }

    //TODO ajustar
    public void sincronizar(){
        //transacoes
        List<Transacoes> transacoesList = new ArrayList<>();

        //avarias
        List<Avarias> avariasList = new ArrayList<>();

        //buscas transacoes na base para enviar ao servidor
        List<Transacao> ultimaTrans = Select.from(Transacao.class).where(Condition.prop(Constants.SENT_TO_SERVER).eq(0)).list();

        for (int i =0; i < ultimaTrans.size(); i ++){
            Transacoes transacoes = new Transacoes();
            transacoes.setIdMotorista(ultimaTrans.get(i).getMotorista().getCodigo());
            transacoes.setIdVeiculo(ultimaTrans.get(i).getVeiculo().getCodigo());
            transacoes.setTipo(ultimaTrans.get(i).getTipoTransacao());
            transacoes.setDtOcorrencia(FormatDateUtil.formateDate(ultimaTrans.get(i).getDataTransacaoAdd()));
            transacoesList.add(i, transacoes);
            ultimaTrans.get(i).setSentToServer(1);
            ultimaTrans.get(i).save();
        }

        //TODO testar sinc de avarias
        List<Avaria> ultimasAvarias = Select.from(Avaria.class).where(Condition.prop(Constants.SENT_TO_SERVER).eq(0)).list();

        for (int i =0; i< ultimasAvarias.size(); i ++){
            Avarias avarias = new Avarias();
            avarias.setIdVeiculo(ultimasAvarias.get(i).getVeiculo().getCodigo());
            avarias.setIdPeca((long) ultimasAvarias.get(i).getPeca());
            avarias.setImagem(ultimasAvarias.get(i).getFoto());
            avarias.setDescricao(ultimasAvarias.get(i).getDescricao());
            avarias.setStatus(ultimasAvarias.get(i).getStatus());
            avarias.setDtOcorrencia(FormatDateUtil.formateDate(ultimasAvarias.get(i).getDataAtualizacao()));
            avariasList.add(avarias);
            ultimasAvarias.get(i).setSentToServer(1);
            ultimasAvarias.get(i).save();
        }

        final String data_sinc = (myPreferences.getString(MyPreferences.DATE_LAST_SINC, ""));

        SincronizacaoRequest sinc = new SincronizacaoRequest(Utils.getAppVersionName(getBaseContext()), "sinc", unidadeApp,
                data_sinc, transacoesList, avariasList);

        //TODO ajustar data de atualizacao.
        RestClient.getClientAPI().getResponseSync(sinc, new Callback<SincronizacaoResponse>() {
            @Override
            public void success(SincronizacaoResponse sinc, Response response) {
                try {
                    if (Utils.isOnline(getBaseContext())) {
                        if (sinc.getStatus() == -1) {
                            //ERRO FATAL
                            Toast.makeText(context, getResources().getString(R.string.sinc_erro_fatal), Toast.LENGTH_LONG).show();
                        } else if (sinc.getStatus() == 1) {
                            if (sinc.getVeiculos().isEmpty() && sinc.getMotoristas().isEmpty()) {
                                Toast.makeText(context, getResources().getString(R.string.sinc_sucesso), Toast.LENGTH_LONG).show();
                            } else {
                                // SUCESSO

                                //salvar motoristas
                                for (int i = 0; i < sinc.getMotoristas().size(); i++) {
                                    Motorista motorista = new Motorista();
                                    Motorista motorista_check = Select.from(Motorista.class).where(Condition.prop(Constants.CODIGO).eq(sinc.getMotoristas().get(i).getIdMotorista())).first();
                                    if (motorista_check == null) {
                                        if (sinc.getMotoristas().get(i).getIdMotorista() != null)
                                            motorista.setCodigo(sinc.getMotoristas().get(i).getIdMotorista());
                                        if (sinc.getMotoristas().get(i).getCpf() != null)
                                            motorista.setCpf(sinc.getMotoristas().get(i).getCpf());
                                        if (sinc.getMotoristas().get(i).getNome() != null)
                                            motorista.setNome(sinc.getMotoristas().get(i).getNome());
                                        motorista.setStatusTransacao(StatusTransacaoMotoristaEnum.MOT_SEM_TRANSACAO.getTipoTransacaoMotorista()); //chave no painel - motorista sem transacao//chave no painel - motorista sem transacao
                                        motorista.setDataAtualizacao(new Date());
                                        motorista.setDataCadastro(new Date());
                                        motorista.setUnidade(unidadeApp);
                                        motorista.save();
                                    }
                                }

                                //salvar veiculos
                                for (int i = 0; i < sinc.getVeiculos().size(); i++) {
                                    Veiculo veiculo = new Veiculo();
                                    Veiculo veiculo_check = Select.from(Veiculo.class).where(
                                            Condition.prop(Constants.CODIGO).
                                                    eq(sinc.getVeiculos().get(i).getIdVeiculo())).first();

                                    if (veiculo_check == null) {
                                        if (sinc.getVeiculos().get(i).getIdVeiculo() != null)
                                            veiculo.setCodigo(sinc.getVeiculos().get(i).getIdVeiculo());
                                        if (sinc.getVeiculos().get(i).getPlaca() != null)
                                            veiculo.setPlaca(sinc.getVeiculos().get(i).getPlaca());
                                        if (sinc.getVeiculos().get(i).getMarca() != null)
                                            veiculo.setMarca(sinc.getVeiculos().get(i).getMarca());
                                        if (sinc.getVeiculos().get(i).getModelo() != null)
                                            veiculo.setModelo(sinc.getVeiculos().get(i).getModelo());
                                        if (sinc.getVeiculos().get(i).getCodChave() != null)
                                            veiculo.setChaveCodigo(sinc.getVeiculos().get(i).getCodChave());
                                        if (sinc.getVeiculos().get(i).getTransacao() != null) {
                                            Motorista checkMotorista = Select.from(Motorista.class).where(Condition.prop(Constants.CODIGO).eq(sinc.getVeiculos().get(i).getTransacao().getIdMotorista())).first();
                                            if (sinc.getVeiculos().get(i).getTransacao().getTipo() == TipoTransacaoEnum.ENTREGA_CHAVES.getIdTipoTransacao()) { //entrega chave para motorista
                                                veiculo.setStatusTransacao(StatusTransacaoVeiculoEnum.CHAVE_MOTORISTA_PATIO.getTipoTransacaoVeiculo()); //chave com motorista
                                                checkMotorista.setStatusTransacao(StatusTransacaoMotoristaEnum.MOT_COM_TRANSACAO.getTipoTransacaoMotorista()); //motorista com chave
                                            } else if (sinc.getVeiculos().get(i).getTransacao().getTipo() == TipoTransacaoEnum.SAIDA_VEICULO.getIdTipoTransacao()) { // carro sai da garagem
                                                veiculo.setStatusTransacao(StatusTransacaoVeiculoEnum.VEICULO_RUA.getTipoTransacaoVeiculo()); // carro fora da garagem
                                                checkMotorista.setStatusTransacao(StatusTransacaoMotoristaEnum.MOT_COM_TRANSACAO.getTipoTransacaoMotorista()); // motorista com carro fora da garagem
                                            } else if (sinc.getVeiculos().get(i).getTransacao().getTipo() == TipoTransacaoEnum.ENTRADA_VEICULO.getIdTipoTransacao()) { //carro volta para garagem
                                                veiculo.setStatusTransacao(StatusTransacaoVeiculoEnum.CHAVE_MOTORISTA_PATIO.getTipoTransacaoVeiculo()); // veiculo na garagem
                                                checkMotorista.setStatusTransacao(StatusTransacaoMotoristaEnum.MOT_COM_TRANSACAO.getTipoTransacaoMotorista()); // motorista com chave
                                            } else if (sinc.getVeiculos().get(i).getTransacao().getTipo() == TipoTransacaoEnum.DEVOLUCAO_CHAVES.getIdTipoTransacao()) { // devolucao de chave
                                                veiculo.setStatusTransacao(StatusTransacaoVeiculoEnum.CHAVE_PAINEL.getTipoTransacaoVeiculo()); //chave no painel
                                                checkMotorista.setStatusTransacao(StatusTransacaoMotoristaEnum.MOT_SEM_TRANSACAO.getTipoTransacaoMotorista()); //motorista sem transacao
                                            }
                                            checkMotorista.save();
                                        } else {
                                            veiculo.setStatusTransacao(StatusTransacaoMotoristaEnum.MOT_COM_TRANSACAO.getTipoTransacaoMotorista());
                                        }
                                        veiculo.setDataAtualizacao(new Date());
                                        veiculo.setDataCadastro(new Date());
                                        veiculo.save();
                                    }

                                    //transacao
                                    Transacao transacao = new Transacao();
                                    if (sinc.getVeiculos().get(i).getTransacao() != null && !"".equals(sinc.getVeiculos().get(i).getTransacao())) {

                                        transacao.setTipoTransacao(sinc.getVeiculos().get(i).getTransacao().getTipo());

                                        Motorista buscaMotorista = Select.from(Motorista.class).where(Condition.prop(Constants.CODIGO).eq(sinc.getVeiculos().get(i).getTransacao().getIdMotorista())).first();
                                        if (buscaMotorista != null) {
                                            buscaMotorista.setStatusTransacao(1);
                                            buscaMotorista.save();

                                            transacao.setMotorista(buscaMotorista);

                                            if (sinc.getVeiculos().get(i).getTransacao().getIdVeiculo() != null) {
                                                Veiculo veiculo_busca = Select.from(Veiculo.class).where(Condition.prop(Constants.CODIGO).eq(sinc.getVeiculos().get(i).getTransacao().getIdVeiculo())).first();
                                                if (veiculo_busca != null) {
                                                    transacao.setVeiculo(veiculo_busca);

                                                    if (sinc.getVeiculos().get(i).getTransacao().getTipo() != null) {
                                                        transacao.setTipoTransacao(TipoTransacaoEnum.findTipoTransacaoById(sinc.getVeiculos().get(i).getTransacao().getTipo()));
                                                    }
                                                    if (sinc.getVeiculos().get(i).getTransacao().getDtOcorrencia() != null)
                                                        transacao.setDataTransacaoAdd(FormatDateUtil.formatDate(sinc.getVeiculos().get(i).getTransacao().getDtOcorrencia()));
                                                    transacao.setSentToServer(1);
                                                    transacao.save();
                                                }
                                            }
                                        }
                                    }

                                    //avarias
                                    for (int j = 0; j < sinc.getVeiculos().get(i).getAvarias().size(); j++) {
                                        Avaria avaria = new Avaria();
                                        if (sinc.getVeiculos().get(i).getAvarias() != null) {
                                            Veiculo veiculo_busca = Select.from(Veiculo.class).where(Condition.prop(Constants.CODIGO).eq(sinc.getVeiculos().get(i).getAvarias().get(j).getIdVeiculo())).first();
                                            if (veiculo_busca != null)
                                                avaria.setVeiculo(veiculo_busca);

                                            if (sinc.getVeiculos().get(i).getAvarias().get(j).getIdPeca() != null) {
                                                int idPeca = Integer.valueOf(String.valueOf(sinc.getVeiculos().get(i).getAvarias().get(j).getIdPeca()));
                                                int peca_busca = AvariasEnum.findPecaExterna(idPeca);
                                                if (peca_busca == -1) {
                                                    peca_busca = AvariasEnum.findPecaInterna(idPeca);
                                                }
                                                if (peca_busca != -1) {
                                                    avaria.setPeca(peca_busca);
                                                }
                                            }
                                            if (sinc.getVeiculos().get(i).getAvarias().get(j).getImagem() != null)
                                                avaria.setFoto(sinc.getVeiculos().get(i).getAvarias().get(j).getImagem());
                                            if (sinc.getVeiculos().get(i).getAvarias().get(j).getDescricao() != null)
                                                avaria.setDescricao(sinc.getVeiculos().get(i).getAvarias().get(j).getDescricao());
                                            if (sinc.getVeiculos().get(i).getAvarias().get(j).getStatus() > -1)
                                                avaria.setStatus(sinc.getVeiculos().get(i).getAvarias().get(j).getStatus());
                                            if (sinc.getVeiculos().get(i).getAvarias().get(j).getDtOcorrencia() != null)
                                                avaria.setDataCadastro(FormatDateUtil.formatDate(sinc.getVeiculos().get(i).getAvarias().get(j).getDtOcorrencia()));
                                            if (!sinc.getVeiculos().get(i).getAvarias().get(j).getDtOcorrencia().isEmpty())
                                                avaria.setDataAtualizacao(FormatDateUtil.formatDate(sinc.getVeiculos().get(i).getAvarias().get(j).getDtOcorrencia()));
                                            avaria.setSentToServer(1);
                                            avaria.save();
                                        }
                                    }
                                }

                                Date dtLastUpdate = FormatDateUtil.formatDate(sinc.getDtUltAtualizacao());

                                SharedPreferences myPreferences = getBaseContext().getSharedPreferences(MyPreferences.PREFERENCES_FILE, MODE_PRIVATE);
                                SharedPreferences.Editor edit = myPreferences.edit();

                                edit.putString(MyPreferences.DATE_LAST_SINC, FormatDateUtil.formateDate(dtLastUpdate));
                                edit.apply();

                                Toast.makeText(context, getResources().getString(R.string.sinc_sucesso), Toast.LENGTH_LONG).show();
                            }

                        } else if (sinc.getStatus() == 2) {
                            //ERRO DE NEGOCIO
                            Toast.makeText(context, getResources().getString(R.string.sinc_erro_negocio), Toast.LENGTH_LONG).show();
                        }

                        if (dialog.isShowing()) {
                            dialog.dismiss();
                            bar.setVisibility(View.GONE);
                        }
                    }
                    else {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.lbl_conexao_indisponivel), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                        bar.setVisibility(View.GONE);
                    }
                    Toast.makeText(context, getResources().getString(R.string.sinc_erro_negocio), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    bar.setVisibility(View.GONE);
                }
            }
        });
    }
}

