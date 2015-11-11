package project.com.br.controlefrota_treinamento;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.com.br.controlefrota_treinamento.enumeration.AvariasEnum;
import project.com.br.controlefrota_treinamento.model.Avaria;
import project.com.br.controlefrota_treinamento.model.Veiculo;
import project.com.br.controlefrota_treinamento.util.Constants;

/**
 * Created by mbueno on 18/08/2015.
 */
public class SaidaVeiculosCheckListActivity extends Activity {

    private AlertDialog alerta;

    private Map<Integer, Avaria> mapAvarias;
    private Veiculo veiculoAvaria;
    private ArrayList<Avaria> listaAvariasExternas;

    private Switch swExtintor, swDocumento, swCombustivel, swPneu, swMacaco, swTriangulo;
    private RadioGroup nivelCombustivel;

    private Long idCarro, idMotorista;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        List<Avaria> listaAvariasInternas;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.saida_veiculos_checklist);

        if (savedInstanceState != null) {
            idCarro = (Long) savedInstanceState.getSerializable(Constants.CARRO_SELECIONADO);
            idMotorista = (Long) savedInstanceState.getSerializable(Constants.MOTORISTA_SELECIONADO);
            listaAvariasExternas = savedInstanceState.getParcelableArrayList(Constants.LISTA_AVARIAS);
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                // Algum erro ocorreu, a activity não recebeu nenhum carro
                Toast.makeText(getBaseContext(), getResources().getString(R.string.generic_error), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                idCarro = (Long) extras.getSerializable(Constants.CARRO_SELECIONADO);
                idMotorista = (Long) extras.getSerializable(Constants.MOTORISTA_SELECIONADO);
                listaAvariasExternas = extras.getParcelableArrayList(Constants.LISTA_AVARIAS);
            }
        }

        listaAvariasInternas = Select.from(Avaria.class).where(Condition.prop("veiculo").eq(idCarro)).list();
        veiculoAvaria = Select.from(Veiculo.class).where(Condition.prop("codigo").eq(idCarro)).first();

        swExtintor = (Switch) findViewById(R.id.sw_extintor);
        swDocumento = (Switch)findViewById(R.id.sw_documento);
        swCombustivel = (Switch) findViewById(R.id.sw_cartao_combustivel);
        swPneu = (Switch) findViewById(R.id.sw_pneu_reserva);
        swMacaco = (Switch) findViewById(R.id.sw_macaco);
        swTriangulo = (Switch) findViewById(R.id.sw_triangulo);

        nivelCombustivel  = (RadioGroup) findViewById(R.id.radio_group_combustivel);

        mapAvarias = new HashMap<>();

        for(Avaria avaria : listaAvariasInternas) {
            int idViewPeca = AvariasEnum.getButtonId(avaria.getPeca(), Constants.TIPO_PECA_INTERNA);

            if (idViewPeca != -1) {
                mapAvarias.put(idViewPeca, avaria);
                // Se componente for tipo combustivel, setar radio correta
                // Caso não seja, componente é um switch
                if (avaria.getPeca() == AvariasEnum.NIVEL_COMBUSTIVEL.getIdPeca()) {
                    if(avaria.getDescricao().equals("1")){
                        nivelCombustivel.check(R.id.cb_nivel_combustivel_um_quarto_id);
                    }
                    else if(avaria.getDescricao().equals("2")){
                        nivelCombustivel.check(R.id.cb_nivel_combustivel_meio_id);
                    }
                    else if(avaria.getDescricao().equals("3")){
                        nivelCombustivel.check(R.id.cb_nivel_combustivel_tres_quartos_id);
                    }
                    else if(avaria.getDescricao().equals("4")){
                        nivelCombustivel.check(R.id.cb_nivel_combustivel_cheio_id);
                    }
                } else {
                    if(avaria.getPeca() == 1)
                        swExtintor.setSelected(Boolean.parseBoolean(avaria.getDescricao()));
                    else if(avaria.getPeca() == 2)
                        swDocumento.setChecked(Boolean.parseBoolean(avaria.getDescricao()));
                    else if(avaria.getPeca() == 3)
                        swCombustivel.setChecked(Boolean.parseBoolean(avaria.getDescricao()));
                    else if(avaria.getPeca() == 33)
                        swPneu.setChecked(Boolean.parseBoolean(avaria.getDescricao()));
                    else if(avaria.getPeca() == 34)
                        swMacaco.setChecked(Boolean.parseBoolean(avaria.getDescricao()));
                    else if(avaria.getPeca() == 35)
                        swTriangulo.setChecked(Boolean.parseBoolean(avaria.getDescricao()));
                }
            }
        }

        if (mapAvarias.isEmpty() || mapAvarias.size() == 0) {
            for (AvariasEnum ae : AvariasEnum.values()) {
                if (ae.getTipoAvaria() == Constants.TIPO_PECA_INTERNA) {
                    Avaria novaAvaria = new Avaria();
                    novaAvaria.setPeca(ae.getIdPeca());
                    novaAvaria.setStatus(1);
                    novaAvaria.setDataAtualizacao(new Date());
                    novaAvaria.setVeiculo(veiculoAvaria);
                    novaAvaria.setDataCadastro(new Date());
                    novaAvaria.setSentToServer(0);

                    if (ae.getBtAvaria() == R.id.radio_group_combustivel) {
                        switch (nivelCombustivel.getCheckedRadioButtonId()) {
                            case (R.id.cb_nivel_combustivel_um_quarto_id):
                                novaAvaria.setDescricao("1");
                                break;
                            case (R.id.cb_nivel_combustivel_meio_id):
                                novaAvaria.setDescricao("2");
                                break;
                            case (R.id.cb_nivel_combustivel_tres_quartos_id):
                                novaAvaria.setDescricao("3");
                                break;
                            case (R.id.cb_nivel_combustivel_cheio_id):
                                novaAvaria.setDescricao("4");
                                break;
                        }
                    } else {
                        Switch item = (Switch) findViewById(ae.getBtAvaria());
                        novaAvaria.setDescricao(String.valueOf(item.isChecked()));
                    }
                    mapAvarias.put(ae.getBtAvaria(), novaAvaria);
                }
            }
        }

        if (getActionBar() != null) {
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void proximo(View v){
        Intent intent;

        switch (v.getId()){
            case R.id.btn_proximo_saida_checklist_id:
                if(nivelCombustivel.getCheckedRadioButtonId() == -1){
                    validationAlert();
                }
                else{
                    ArrayList<Avaria> tempAvarias = new ArrayList<Avaria>(mapAvarias.values());
                    ArrayList<Avaria> listaAvarias = new ArrayList<Avaria>();
                    for(Avaria avaria : tempAvarias) {
                        if (avaria != null && avaria.getPeca() != null)
                            switch (AvariasEnum.getButtonId(avaria.getPeca(), Constants.TIPO_PECA_INTERNA)) {
                                case (R.id.sw_extintor):
                                    avaria.setDescricao(String.valueOf(swExtintor.isChecked()));
                                    break;
                                case (R.id.sw_documento):
                                    avaria.setDescricao(String.valueOf(swDocumento.isChecked()));
                                    break;
                                case (R.id.sw_cartao_combustivel):
                                    avaria.setDescricao(String.valueOf(swCombustivel.isChecked()));
                                    break;
                                case (R.id.sw_pneu_reserva):
                                    avaria.setDescricao(String.valueOf(swPneu.isChecked()));
                                    break;
                                case (R.id.sw_macaco):
                                    avaria.setDescricao(String.valueOf(swMacaco.isChecked()));
                                    break;
                                case (R.id.sw_triangulo):
                                    avaria.setDescricao(String.valueOf(swTriangulo.isChecked()));
                                    break;
                                case (R.id.radio_group_combustivel):
                                    switch (nivelCombustivel.getCheckedRadioButtonId()) {
                                        case (R.id.cb_nivel_combustivel_um_quarto_id):
                                            avaria.setDescricao("1");
                                            break;
                                        case (R.id.cb_nivel_combustivel_meio_id):
                                            avaria.setDescricao("2");
                                            break;
                                        case (R.id.cb_nivel_combustivel_tres_quartos_id):
                                            avaria.setDescricao("3");
                                            break;
                                        case (R.id.cb_nivel_combustivel_cheio_id):
                                            avaria.setDescricao("4");
                                            break;
                                    }
                                    break;
                            }
                        listaAvarias.add(avaria);
                    }

                    listaAvarias.addAll(listaAvariasExternas);

                    intent = new Intent(this, SaidaVeiculosConfirmacaoActivity.class);
                    intent.putExtra(Constants.CARRO_SELECIONADO, idCarro);
                    intent.putExtra(Constants.MOTORISTA_SELECIONADO, idMotorista);
                    intent.putParcelableArrayListExtra(Constants.LISTA_AVARIAS, listaAvarias);
                    startActivity(intent);
                    break;
                }
        }
    }

    public void anterior(View v) {
        switch (v.getId()){
            case R.id.btn_anterior_saida_carros_checklist_id:
                onBackPressed();
                break;
        }
    }

    private void validationAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensagem");
        builder.setMessage("Selecione o nível do combustível.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alerta.dismiss();
            }
        });
        alerta = builder.create();
        alerta.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
