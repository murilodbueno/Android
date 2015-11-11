package luxfacta.com.br.controlefrota_treinamento;

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

import luxfacta.com.br.controlefrota_treinamento.adapter.ListViewMotoristaAdapter;
import luxfacta.com.br.controlefrota_treinamento.enumeration.TipoTransacaoEnum;
import luxfacta.com.br.controlefrota_treinamento.model.Motorista;
import luxfacta.com.br.controlefrota_treinamento.model.TipoTransacao;
import luxfacta.com.br.controlefrota_treinamento.model.Transacao;
import luxfacta.com.br.controlefrota_treinamento.model.Unidade;
import luxfacta.com.br.controlefrota_treinamento.model.Veiculo;
import luxfacta.com.br.controlefrota_treinamento.util.Constants;

/**
 * Created by Luxfacta on 18/08/2015.
 */
public class EntregaChaveMotoristasActivity extends Activity{

    private Button btnAnterior;
    private ListView listViewMotoristas;
    private AlertDialog alerta;
    private String placaCarroSelecionado;
    private String motoristaSelecionado;
    private String descricaoCarroSelecionado;
    private int itemSelecionadoId;
    private List<Motorista> lista;
    private String cpfMotorista;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrega_chave_motoristas);

        TextView msgListaVazia = (TextView) findViewById(R.id.msg_lista_vazia);
        listViewMotoristas = (ListView) findViewById(R.id.listview_motoristas_id);
        btnAnterior = (Button) findViewById(R.id.btn_anterior_motoristas_id);

        if (getActionBar() != null) {
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lista  = Motorista.findWithQuery(Motorista.class, "select * from motorista m where m.status_transacao = 0");

        descricaoCarroSelecionado = getIntent().getStringExtra(Constants.CARRO_SELECIONADO);
        placaCarroSelecionado = getIntent().getStringExtra(Constants.PLACA_CARRO_SELECIONADO);

        if(lista != null && (!lista.isEmpty())) {
            ListViewMotoristaAdapter adapter = new ListViewMotoristaAdapter(this, lista);
            listViewMotoristas.setAdapter(adapter);
            listViewMotoristas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int j = 0; j < parent.getChildCount(); j++) {
                        parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                    }
                    view.setBackgroundColor(getResources().getColor(R.color.list_selected));
                    itemSelecionadoId = (int) id;
                    motoristaSelecionado = lista.get(itemSelecionadoId).getCpf();
                    String funcionario = lista.get(itemSelecionadoId).getNome() + " " + lista.get(itemSelecionadoId).getCpf();
                    validationAlert(funcionario, descricaoCarroSelecionado);
                    cpfMotorista = lista.get(itemSelecionadoId).getCpf();
                }
            });

            msgListaVazia.setVisibility(View.GONE);
        } else {
            listViewMotoristas.setVisibility(View.GONE);
        }
    }

    public void anterior(View v) {
        switch (v.getId()){
            case R.id.btn_anterior_motoristas_id:
                onBackPressed();
                break;
        }
    }

    private void validationAlert(String funcionario, String carro){
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.confimation_dialog, null);
        final Dialog builder = new Dialog(this);

        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(dialogView);

        TextView title = (TextView) builder.findViewById(R.id.title);
        title.setText(R.string.lbl_confirmar);

        TextView text = (TextView) builder.findViewById(R.id.text);
        String formattedText = getResources().getString(R.string.entrega_chave_confirmation_dialog, funcionario, carro);
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
                Motorista buscaMotorista = Select.from(Motorista.class).where(Condition.prop("cpf").eq(cpfMotorista)).first();
                buscaMotorista.setStatusTransacao(1);
                buscaMotorista.save();

                Veiculo buscaVeiculo = Select.from(Veiculo.class).where(Condition.prop("placa").eq(placaCarroSelecionado)).first();
                buscaVeiculo.setStatusTransacao(2);
                buscaVeiculo.save();

                Transacao transacao = new Transacao();
                transacao.setTipoTransacao(1l);
                transacao.setVeiculo(buscaVeiculo);
                transacao.setSentToServer(0);
                transacao.setMotorista(buscaMotorista);
                transacao.setDataTransacaoAdd(new Date());
                transacao.save();

                Toast.makeText(getBaseContext(), R.string.entrega_chave_msg_success, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EntregaChaveMotoristasActivity.this, MainActivity.class));
            }
        });

        builder.show();
    }
}
