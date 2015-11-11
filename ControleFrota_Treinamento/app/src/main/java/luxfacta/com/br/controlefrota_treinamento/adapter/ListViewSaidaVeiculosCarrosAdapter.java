package luxfacta.com.br.controlefrota_treinamento.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import luxfacta.com.br.controlefrota_treinamento.R;
import luxfacta.com.br.controlefrota_treinamento.bean.SaidaEntradaVeiculosBean;

/**
 * Created by Luxfacta on 19/08/2015.
 */
public class ListViewSaidaVeiculosCarrosAdapter extends ArrayAdapter<SaidaEntradaVeiculosBean>{
    private Context context;
    public List<SaidaEntradaVeiculosBean> listaSaida;

    public ListViewSaidaVeiculosCarrosAdapter(Context context, List<SaidaEntradaVeiculosBean> items) {
        super(context, 0, items);
        this.context = context;
        this.listaSaida = items;
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
            convertView = mInflater.inflate(R.layout.saida_veiculos_carros_list_view, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.funcionario_id);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.frota_carro_id);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        SaidaEntradaVeiculosBean rowItem = getItem(position);

        //TODO ajustar frota
        holder.txtTitle.setText(rowItem.getCarros().toString());
        holder.txtDesc.setText(rowItem.getMotoristas().toString());

        return convertView;
    }

    @Override
    public int getCount() {
        return listaSaida.size();
    }

    @Override
    public long getItemId(int position) {
        return listaSaida.indexOf(getItem(position));
    }

}
