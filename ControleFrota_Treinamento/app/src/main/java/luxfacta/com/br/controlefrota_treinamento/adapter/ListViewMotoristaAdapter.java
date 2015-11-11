package luxfacta.com.br.controlefrota_treinamento.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luxfacta.com.br.controlefrota_treinamento.R;
import luxfacta.com.br.controlefrota_treinamento.model.Motorista;

/**
 * Created by mbueno on 18/08/2015.
 */
public class ListViewMotoristaAdapter extends ArrayAdapter<Motorista> {

    private Context context;
    private List<Motorista> lista;
    private int selectedItem;

    public ListViewMotoristaAdapter(Context context, List<Motorista> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    private class ViewHolder {
        TextView txtTitle;
        TextView txtDesc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.entrega_chave_motoristas_list_view, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.nome_id);
            holder.txtDesc = (TextView) convertView.findViewById(R.id.cpf_id);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Motorista rowItem = getItem(position);

        holder.txtTitle.setText(rowItem.getNome());
        holder.txtDesc.setText(rowItem.getCpf());

        return convertView;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public long getItemId(int position) {
        return lista.indexOf(getItem(position));
    }
}
