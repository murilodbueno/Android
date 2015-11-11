package project.com.br.controlefrota_treinamento;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.com.br.controlefrota_treinamento.adapter.ListViewSaidaVeiculosCarrosAdapter;
import project.com.br.controlefrota_treinamento.bean.SaidaEntradaVeiculosBean;
import project.com.br.controlefrota_treinamento.model.Motorista;
import project.com.br.controlefrota_treinamento.model.Transacao;
import project.com.br.controlefrota_treinamento.model.Veiculo;
import project.com.br.controlefrota_treinamento.util.Constants;

/**
 * Created by mbueno on 18/08/2015.
 */
public class SaidaVeiculosCarrosActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView listView;
    List<SaidaEntradaVeiculosBean> listaSaida;
    private AlertDialog alerta;
    private boolean isSelected = false;
    private List<Veiculo> listaVeiculos;
    private List<Motorista> listaMotoristas;

    private long idMotoristaSelecionado, idCarroSelecionado;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saida_veiculos_carros);

        TextView msgListaVazia = (TextView) findViewById(R.id.msg_lista_vazia);
        listView = (ListView) findViewById(R.id.listview_saida_carros_id);

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
                "t1.data_transacao_add = t2.maxDate where t1.tipo_transacao = 1 or t1.tipo_transacao = 3");

        listaVeiculos = new ArrayList<>();
        listaMotoristas = new ArrayList<>();

        for (int i = 0; i < listaTransacao.size(); i++) {
            listaVeiculos.add(listaTransacao.get(i).getVeiculo());
            listaMotoristas.add(listaTransacao.get(i).getMotorista());
            listaTransacao.get(i).getVeiculo();
        }

        listaSaida = new ArrayList<SaidaEntradaVeiculosBean>();
        for (int i = 0; i < listaTransacao.size(); i++) {
            SaidaEntradaVeiculosBean item = new SaidaEntradaVeiculosBean(listaVeiculos.get(i), listaMotoristas.get(i));
            listaSaida.add(item);
        }

        if (listaSaida != null && (!listaSaida.isEmpty())) {
            ListViewSaidaVeiculosCarrosAdapter adapter = new ListViewSaidaVeiculosCarrosAdapter(this, listaSaida);
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
        isSelected = true;
        idMotoristaSelecionado = listaSaida.get(position).getMotoristas().getCodigo();
        idCarroSelecionado = listaSaida.get(position).getCarros().getCodigo();
    }

    public void proximo(View v){
        Intent intent;

        switch (v.getId()){
            case R.id.btn_proximo_saida_carros_id:
                if(isSelected) {
                    intent = new Intent(this, SaidaVeiculosAvariasActivity.class);
                    intent.putExtra(Constants.CARRO_SELECIONADO, idCarroSelecionado);
                    intent.putExtra(Constants.MOTORISTA_SELECIONADO, idMotoristaSelecionado);
                    startActivity(intent);
                } else {
                    validationAlert();
                }
                break;
        }
    }

    private void validationAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensagem");
        builder.setMessage("Selecione ao menos um item.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alerta.dismiss();
            }
        });
        alerta = builder.create();
        alerta.show();
    }
}
