package luxfacta.com.br.controlefrota_treinamento;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import luxfacta.com.br.controlefrota_treinamento.adapter.DevolucaoListAdapter;
import luxfacta.com.br.controlefrota_treinamento.bean.SaidaEntradaVeiculosBean;
import luxfacta.com.br.controlefrota_treinamento.model.Motorista;
import luxfacta.com.br.controlefrota_treinamento.model.Transacao;
import luxfacta.com.br.controlefrota_treinamento.model.Veiculo;

/**
 * Created by Luxfacta on 19/08/2015.
 */
public class DevolucaoActivity  extends ListActivity implements
        AdapterView.OnItemClickListener {

    private AlertDialog confirmationDialog;
    private int idSelectedItem;

    List<SaidaEntradaVeiculosBean> listaDevolucao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devolucao);

        TextView msgListaVazia = (TextView) findViewById(R.id.msg_lista_vazia);

        List<Transacao> listaTransacao = Transacao.findWithQuery(Transacao.class, "select distinct * from TRANSACAO t1 inner join " +
                "(select distinct veiculo, MAX(data_transacao_add) maxDate " +
                "from TRANSACAO group by veiculo) t2 " +
                "on " +
                "t1.veiculo = t2.veiculo " +
                "and " +
                "t1.data_transacao_add = t2.maxDate where t1.tipo_transacao = 3 or t1.tipo_transacao = 1");

        List<Veiculo> listaCarros = new ArrayList<>();
        List<Motorista> listaMotoristas = new ArrayList<>();

        for (int i = 0; i < listaTransacao.size(); i++) {
            listaCarros.add(listaTransacao.get(i).getVeiculo());
            listaMotoristas.add(listaTransacao.get(i).getMotorista());
            listaTransacao.get(i).getVeiculo();
        }

        listaDevolucao = new ArrayList<SaidaEntradaVeiculosBean>();
        for (int i = 0; i < listaCarros.size(); i++) {
            SaidaEntradaVeiculosBean item = new SaidaEntradaVeiculosBean(listaCarros.get(i), listaMotoristas.get(i));
            listaDevolucao.add(item);
        }

        if (listaDevolucao.isEmpty()) {
            getListView().setVisibility(View.GONE);
        } else {
            ArrayAdapter adapter = new DevolucaoListAdapter(this, R.layout.devolucao_list_view, listaDevolucao);
            getListView().setAdapter(adapter);
            getListView().setOnItemClickListener(this);

            msgListaVazia.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (int j = 0; j < parent.getChildCount(); j++){
            parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
        }

        view.setBackgroundColor(getResources().getColor(R.color.list_selected));

        String carro = listaDevolucao.get((int) id).getCarros().toString();
        String motorista = listaDevolucao.get((int) id).getMotoristas().toString();
        idSelectedItem = (int) id;

        validationAlert(motorista, carro);
    }

    private void validationAlert(String funcionario, String carro) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.confimation_dialog, null);
        final Dialog builder = new Dialog(this);

        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(dialogView);

        TextView title = (TextView) builder.findViewById(R.id.title);
        title.setText(R.string.lbl_confirmar);

        TextView text = (TextView) builder.findViewById(R.id.text);
        String formattedText = getResources().getString(R.string.devolucao_confimation_dialog, funcionario, carro);
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
                Veiculo vei = listaDevolucao.get(idSelectedItem).getCarros();
                vei.setStatusTransacao(1);
                vei.save();

                Motorista motorista = listaDevolucao.get(idSelectedItem).getMotoristas();
                motorista.setStatusTransacao(0);
                motorista.save();

                Transacao transacao = new Transacao();
                transacao.setTipoTransacao(4l);
                transacao.setDataTransacaoAdd(new Date());
                transacao.setVeiculo(vei);
                transacao.setSentToServer(0);
                transacao.setMotorista(motorista);
                transacao.save();

                Toast.makeText(getBaseContext(), R.string.devolucao_msg_success, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DevolucaoActivity.this, MainActivity.class));
            }
        });

        builder.show();
    }
}
