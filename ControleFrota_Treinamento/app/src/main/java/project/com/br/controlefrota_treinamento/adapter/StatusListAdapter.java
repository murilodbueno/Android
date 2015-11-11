package project.com.br.controlefrota_treinamento.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import project.com.br.controlefrota_treinamento.R;

/**
 * Created by llima on 18/08/2015.
 */
public class StatusListAdapter extends ArrayAdapter<Integer> {

    private List<Integer> data;
//    private String itemTitle, itemDesc;
//    private Integer itemValue;

    public StatusListAdapter(Context context, int resource, List<Integer> data) {
        super(context, resource, data);
        this.data = data;

    }

    static class ViewHolder {
        ImageView icon;
        TextView itemTitle, itemDesc, itemValue;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.status_list_view, null);

            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.itemTitle = (TextView) convertView.findViewById(R.id.title);
            holder.itemDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.itemValue = (TextView) convertView.findViewById(R.id.value);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (position) {
            case 0:
                holder.icon.setImageResource(R.drawable.key_exchange);
                holder.itemTitle.setText(R.string.title_chaves_entregues);
                holder.itemDesc.setText(R.string.desc_chaves_entregues);
                break;
            case 1:
                holder.icon.setImageResource(R.drawable.car_rental);
                holder.itemTitle.setText(R.string.title_carros_rua);
                holder.itemDesc.setText(R.string.desc_carros_rua);
                break;
            case 2:
                holder.icon.setImageResource(R.drawable.key);
                holder.itemTitle.setText(R.string.title_chaves_quadro);
                holder.itemDesc.setText(R.string.desc_chaves_quadro);
                break;
            case 3:
                holder.icon.setImageResource(R.drawable.garage);
                holder.itemTitle.setText(R.string.title_carros_patio);
                holder.itemDesc.setText(R.string.desc_carros_patio);
                break;
        }

        holder.itemValue.setText(data.get(position).toString());

        return convertView;
    }
}
