package project.com.br.controlefrota_treinamento;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.com.br.controlefrota_treinamento.adapter.ListViewSaidaVeiculosCarrosAdapter;
import project.com.br.controlefrota_treinamento.bean.SaidaEntradaVeiculosBean;
import project.com.br.controlefrota_treinamento.model.Motorista;
import project.com.br.controlefrota_treinamento.model.Transacao;
import project.com.br.controlefrota_treinamento.model.Veiculo;

/**
 * Created by mbueno on 19/08/2015.
 */
public class EntradaVeiculosActivity extends Activity implements AdapterView.OnItemClickListener{


    ListView listView;
    List<SaidaEntradaVeiculosBean> listaEntrada;
    private AlertDialog alerta;
    private int idSelected;
    private List<Veiculo> listaCarros;
    private List<Motorista> listaMotoristas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrada_veiculos);

        TextView msgListaVazia = (TextView) findViewById(R.id.msg_lista_vazia);
        listView = (ListView) findViewById(R.id.entrada_veiculos_lista_id);

        if (getActionBar() != null) {
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        List<Transacao> listaTransacao = Transacao.findWithQuery(Transacao.class, "select distinct * from TRANSACAO t1 inner join " +
                "(select distinct veiculo, MAX(data_transacao_add) maxDate " +
                "from TRANSACAO group by veiculo) t2 " +
                "on " +
                "t1.veiculo = t2.veiculo " +
                "and " +
                "t1.data_transacao_add = t2.maxDate where t1.tipo_transacao = 2");

        List<Veiculo> listaCarros = new ArrayList<>();
        List<Motorista> listaMotoristas = new ArrayList<>();

        for (int i = 0; i < listaTransacao.size(); i++) {
            listaCarros.add(listaTransacao.get(i).getVeiculo());
            listaMotoristas.add(listaTransacao.get(i).getMotorista());
            listaTransacao.get(i).getVeiculo();
        }

        listaEntrada = new ArrayList<SaidaEntradaVeiculosBean>();
        for (int i = 0; i < listaCarros.size(); i++) {
            SaidaEntradaVeiculosBean item = new SaidaEntradaVeiculosBean(listaCarros.get(i), listaMotoristas.get(i));
            listaEntrada.add(item);
        }

        if (listaEntrada != null && (!listaEntrada.isEmpty())) {
            ListViewSaidaVeiculosCarrosAdapter adapter = new ListViewSaidaVeiculosCarrosAdapter(this, listaEntrada);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);

            msgListaVazia.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (int j = 0; j < parent.getChildCount(); j++){
            parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
        }
        view.setBackgroundColor(getResources().getColor(R.color.list_selected));
        idSelected = (int)id;
        String carro = listaEntrada.get(idSelected).getCarros().toString();
        String motorista = listaEntrada.get(idSelected).getMotoristas().toString();
        String funcionarioCarro = carro.concat(" responsável pelo funcionário " + motorista);
        validationAlert(funcionarioCarro);
    }

    private void validationAlert(String funcionario){
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.confimation_dialog, null);
        final Dialog builder = new Dialog(this);

        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(dialogView);

        TextView title = (TextView) builder.findViewById(R.id.title);
        title.setText(R.string.lbl_confirmar);

        TextView text = (TextView) builder.findViewById(R.id.text);
        String formattedText = getResources().getString(R.string.entrada_veiculos_confirmation_dialog, funcionario);
        text.setText(formattedText);

        Button cancelButton = (Button) builder.findViewById(R.id.cancel_button);
        Button confirmButton = (Button) builder.findViewById(R.id.confirm_button);

        cancelButton.setText(R.string.lbl_cancelar);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        confirmButton.setText(R.string.lbl_confirmar);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Veiculo buscaVeiculo = Select.from(Veiculo.class).where(Condition.prop("placa").eq(listaEntrada.get(idSelected).getCarros().getPlaca())).first();
                buscaVeiculo.setStatusTransacao(2);
                buscaVeiculo.save();

                Motorista buscaMotorista = Select.from(Motorista.class).where(Condition.prop("cpf").eq(listaEntrada.get(idSelected).getMotoristas().getCpf())).first();
                buscaMotorista.setStatusTransacao(1);
                buscaMotorista.save();

                Transacao transacao = new Transacao();
                transacao.setTipoTransacao(3l);
                transacao.setDataTransacaoAdd(new Date());
                transacao.setSentToServer(0);
                transacao.setVeiculo(buscaVeiculo);
                transacao.setMotorista(buscaMotorista);
                transacao.save();

                Toast.makeText(getBaseContext(), R.string.entrada_veiculos_msg_success, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EntradaVeiculosActivity.this, MainActivity.class));
            }
        });

        builder.show();
    }
}