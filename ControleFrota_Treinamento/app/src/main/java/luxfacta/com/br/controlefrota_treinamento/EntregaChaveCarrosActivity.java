package luxfacta.com.br.controlefrota_treinamento;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;
import java.util.prefs.Preferences;

import luxfacta.com.br.controlefrota_treinamento.adapter.ListViewCarroAdapter;
import luxfacta.com.br.controlefrota_treinamento.model.Unidade;
import luxfacta.com.br.controlefrota_treinamento.model.Veiculo;
import luxfacta.com.br.controlefrota_treinamento.util.Constants;
import luxfacta.com.br.controlefrota_treinamento.util.MyPreferences;
import luxfacta.com.br.controlefrota_treinamento.util.Utils;

/**
 * Created by Luxfacta on 17/08/2015.
 */
public class EntregaChaveCarrosActivity extends Activity {

    private Button btnProximo;
    private ListView listViewCarros;
    private boolean isSelected = false;
    private AlertDialog alerta;
    private List<Veiculo> lista;
    private int itemSelectedId;
    private String descricaoCarro;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrega_chave_carros);

        if (getActionBar() != null) {
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView msgListaVazia = (TextView) findViewById(R.id.msg_lista_vazia);
        listViewCarros = (ListView) findViewById(R.id.listview_carros_id);
        btnProximo = (Button) findViewById(R.id.btn_proximo_carros_id);

        lista  = Veiculo.findWithQuery(Veiculo.class, "select * from veiculo v where v.status_transacao = 1");
//        lista = Veiculo.listAll(Veiculo.class);

        if (lista != null && (!lista.isEmpty())) {
            ListViewCarroAdapter adapter = new ListViewCarroAdapter(this, lista);
            listViewCarros.setAdapter(adapter);
            listViewCarros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int j = 0; j < parent.getChildCount(); j++) {
                        parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                    }
                    // change the background color of the selected element
                    view.setBackgroundColor(getResources().getColor(R.color.list_selected));
                    isSelected = true;
                    //Id do item selecionado na lista
                    itemSelectedId = (int) id;
                    //TODO ajustar frota
                    descricaoCarro = lista.get(position) + " / " + lista.get(itemSelectedId).getPlaca();
                }
            });

            msgListaVazia.setVisibility(View.GONE);
        } else {
            listViewCarros.setVisibility(View.GONE);
        }
    }

    public void proximo(View v){
        Intent intent;

        switch (v.getId()){
            case R.id.btn_proximo_carros_id:
                if(isSelected) {
                    intent = new Intent(this, EntregaChaveMotoristasActivity.class);
                    Veiculo carro = new Veiculo();
                    carro = lista.get(itemSelectedId);
                    intent.putExtra(Constants.CARRO_SELECIONADO, descricaoCarro);
                    intent.putExtra(Constants.PLACA_CARRO_SELECIONADO, carro.getPlaca());
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
       builder.setMessage("Selecione um carro.");
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
