package luxfacta.com.br.controlefrota_treinamento;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import luxfacta.com.br.controlefrota_treinamento.adapter.StatusListAdapter;
import luxfacta.com.br.controlefrota_treinamento.model.Veiculo;

/**
 * Created by llima on 18/08/2015.
 */
public class StatusActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);

        //TODO Busca dos valores de status no service
        List<Integer> values = new ArrayList<>();

        int veiculoChaveEntregue = (int) Veiculo.count(Veiculo.class, "status_transacao = ? or status_transacao = ?", new String[]{"2", "3"});
        int veiculoNaRua = (int) Veiculo.count(Veiculo.class, "status_transacao = ?", new String[]{"3"});
        int veiculoChavePainel = (int) Veiculo.count(Veiculo.class, "status_transacao = ?", new String[]{"1"});
        int veiculoPatio = (int) Veiculo.count(Veiculo.class, "status_transacao = ? or status_transacao = ?", new String[]{"2", "1"});

        values.add(veiculoChaveEntregue);
        values.add(veiculoNaRua);
        values.add(veiculoChavePainel);
        values.add(veiculoPatio);

        ArrayAdapter adapter = new StatusListAdapter(this, R.layout.status_list_view, values);
        getListView().setAdapter(adapter);
    }
}
