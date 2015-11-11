package mbueno.com.br.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mbueno.com.br.persistence.DataBaseHelper;
import mbueno.com.br.util.Constantes;
import mbueno.com.br.util.DateFormat;

/**
 * Created by mbueno on 12/08/2015.
 */
public class CostListActivity extends ListActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener{

    public static final String ACTION_SHOW_COST_LIST = "boaviagem.ACTION_SHOW_COST_LIST";
    public static final String CATEGORY_COST_LIST = "boaviagem.CATEGORY_COST_LIST";

    private List<Map<String, Object>> gastos;
    private String dataAnterior = "";
    private String viagemId;
    private String id;
    private int selectCost;

    //alerts
    private AlertDialog alertDialog;
    private AlertDialog dialogConfirm;

    //Persistence
    private DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        viagemId = getIntent().getStringExtra(Constantes.TRAVEL_ID);

        String[] de = {"data", "descricao", "valor", "categoria"};
        int[] para = {R.id.listCostDate, R.id.listCostDescription, R.id.listCostValue, R.id.listCostCaterory};

        SimpleAdapter adapter = new SimpleAdapter(this, costList(), R.layout.cost_list, de, para);
        adapter.setViewBinder(new CostViewBinder());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

        //alerts
        this.alertDialog = createAlertDialog();
        this.dialogConfirm = createDialogConfirm();

        helper = new DataBaseHelper(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = gastos.get(position);
        String description = (String) map.get("descricao");
        String message = "Gasto selecionado: " + description;
//        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();

        this.selectCost = position;
        alertDialog.show();
    }

    private List<Map<String, Object>> costList(){

        DataBaseHelper h = new DataBaseHelper(this);
        SQLiteDatabase db = h.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT g._id, g.categoria, g.data, g.valor, g.local, g.descricao, " +
                "g.viagem_id, v.destino FROM gasto g JOIN viagem v ON g.viagem_id = v._id WHERE g.viagem_id = ?", new String[]{viagemId});

        cursor.moveToFirst();

        gastos = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < cursor.getCount(); i++){
            Map<String, Object> item = new HashMap<String, Object>();

            String id = cursor.getString(0);
            String categoria = cursor.getString(1);
            String dataGasto = cursor.getString(2);
            double valor = cursor.getDouble(3);
            String local = cursor.getString(4);
            String descricao = cursor.getString(5);

            String viagem_id = cursor.getString(6);
            String destino = cursor.getString(7);

            item.put("id", id);

            if(categoria == Constantes.CATEGORIA_ALIMENTACAO){
                item.put("categoria", R.color.categoria_alimentacao);
            }
            else if(categoria == Constantes.CATEGORIA_COMBUSTIVEL){
                item.put("categoria", R.color.categoria_combustivel);
            }
            else if(categoria == Constantes.CATEGORIA_HOSPEDAGEM){
                item.put("categoria", R.color.categoria_hospedagem);
            }
            else if(categoria == Constantes.CATEGORIA_TRANSPORTE) {
                item.put("categoria", R.color.categoria_transporte);
            }
            else {
                item.put("categoria", R.color.categoria_outros);
            }

            item.put("data", dataGasto);

            item.put("descricao", descricao);
            item.put("local", local);
            item.put("valor", valor);
            item.put("destino", destino);
            item.put("viagem_id", viagem_id);

            gastos.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        return gastos;
    }

    @Override
    public void onClick(DialogInterface dialog, int item) {
        Intent intent;
        String idCost = (String) gastos.get(selectCost).get("id");
        String destino = (String) gastos.get(selectCost).get("destino");
        String viagem_id = (String) gastos.get(selectCost).get("viagem_id");

        switch (item){
            case 0:
                intent = new Intent(this, CostActivity.class);
                intent.putExtra(Constantes.COST_ID, idCost);
                intent.putExtra(Constantes.TRAVEL_ID, viagem_id);
                intent.putExtra(Constantes.TRAVEL_DESTINY, destino);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, CostActivity.class);
                intent.putExtra(Constantes.TRAVEL_ID, viagem_id);
                startActivity(intent);
                break;
            case 2:
                dialogConfirm.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                gastos.remove(this.selectCost);
                removerGasto(idCost);
                getListView().invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialogConfirm.dismiss();
                break;
        }
    }

    private void removerGasto(String id){
        SQLiteDatabase db = helper.getWritableDatabase();
        String where[] = new String[]{id};
        db.delete("gasto", "_id = ?", where);
    }

    private class CostViewBinder implements SimpleAdapter.ViewBinder{

        public boolean setViewValue(View view, Object data, String textRepresentation){
            if(view.getId() == R.id.listCostDate){
                if(!dataAnterior.equals(data)){
                    TextView textView = (TextView) view;
                    textView.setText(textRepresentation);
                    view.setVisibility(View.VISIBLE);
                } else{
                    view.setVisibility(View.GONE);
                }
                return true;
            }
            if(view.getId() == R.id.listCostCaterory){
                Integer id = (Integer) data;
                view.setBackgroundColor(getResources().getColor(id));
                return true;
            }
            return false;
        }
    }

    private AlertDialog createAlertDialog(){
        final CharSequence[] items = {
                getString(R.string.lbl_edit),
                getString(R.string.lbl_new_cost),
                getString(R.string.lbl_remove)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.lbl_options);
        builder.setItems(items, this);

        return builder.create();
    }

    private AlertDialog createDialogConfirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.lbl_delete_confirm_travel);
        builder.setPositiveButton(getString(R.string.lbl_sim), this);
        builder.setNegativeButton(getString(R.string.lbl_nao), this);
        return builder.create();
    }

}
