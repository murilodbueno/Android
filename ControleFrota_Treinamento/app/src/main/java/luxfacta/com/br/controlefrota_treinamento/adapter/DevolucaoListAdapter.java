package luxfacta.com.br.controlefrota_treinamento.adapter;

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
 * Created by llima on 19/08/2015.
 */
public class DevolucaoListAdapter extends ArrayAdapter<SaidaEntradaVeiculosBean> {

    private List<SaidaEntradaVeiculosBean> data;

    public DevolucaoListAdapter(Context context, int resource, List<SaidaEntradaVeiculosBean> data) {
        super(context, resource, data);
        this.data = data;
    }

    static class ViewHolder {
        TextView nomeCarro, funcionario;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.devolucao_list_view, null);

            holder = new ViewHolder();
            holder.nomeCarro = (TextView) convertView.findViewById(R.id.nome_carro);
            holder.funcionario = (TextView) convertView.findViewById(R.id.funcionario);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SaidaEntradaVeiculosBean item = data.get(position);

        holder.nomeCarro.setText(item.getCarros().toString());
        holder.funcionario.setText(item.getMotoristas().getNome());

        return convertView;
    }
}


