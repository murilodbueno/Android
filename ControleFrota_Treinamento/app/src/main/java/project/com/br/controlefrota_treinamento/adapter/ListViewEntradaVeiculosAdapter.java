package project.com.br.controlefrota_treinamento.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import project.com.br.controlefrota_treinamento.R;
import project.com.br.controlefrota_treinamento.bean.SaidaEntradaVeiculosBean;

/**
 * Created by mbueno on 19/08/2015.
 */
public class ListViewEntradaVeiculosAdapter extends ArrayAdapter<SaidaEntradaVeiculosBean> {

    private Context context;
    public List<SaidaEntradaVeiculosBean> listaEntrada;

    public ListViewEntradaVeiculosAdapter(Context context, List<SaidaEntradaVeiculosBean> items) {
        super(context, 0, items);
        this.context = context;
        this.listaEntrada = items;
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
            convertView = mInflater.inflate(R.layout.entrada_veiculos_list_view, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.entrada_veiculos_placa_id);
            holder.txtDesc = (TextView) convertView.findViewById(R.id.entrada_veiculos_funcionario_id);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        SaidaEntradaVeiculosBean rowItem = getItem(position);

        holder.txtTitle.setText(rowItem.getCarros().toString());
        holder.txtDesc.setText(rowItem.getMotoristas().toString());

        return convertView;
    }

    @Override
    public int getCount() {
        return listaEntrada.size();
    }

    @Override
    public long getItemId(int position) {
        return listaEntrada.indexOf(getItem(position));
    }

}
