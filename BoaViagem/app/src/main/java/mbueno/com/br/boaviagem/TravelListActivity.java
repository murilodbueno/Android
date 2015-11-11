package mbueno.com.br.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mbueno.com.br.persistence.DataBaseHelper;
import mbueno.com.br.util.Constantes;
import mbueno.com.br.util.DateFormat;

/**
 * Created by mbueno on 12/08/2015.
 */
public class TravelListActivity extends ListActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener{

    public static final String ACTION_SHOW_TRAVEL_LIST = "boaviagem.ACTION_SHOW_TRAVEL_LIST";
    public static final String CATEGORY_TRAVEL_LIST = "boaviagem.CATEGORY_TRAVEL_LIST";

    private List<Map<String, Object>> travels;
    private int selectedTravel;

    private AlertDialog alertDialog;
    private AlertDialog dialogConfirm;

    private Integer progressBarInit = 0;

    //Persistence
    private DataBaseHelper helper;
    private Double limitValue;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String value = preferences.getString("valor_limite", "-1");
        limitValue = Double.valueOf(value);

        String[] de = {"imagem", "destino", "data", "total", "barraProgresso"};
        int[] para = {R.id.travelTypeId, R.id.listDestinyId, R.id.listDateId, R.id.listValueId, R.id.progressBarId};

        SimpleAdapter adapter = new SimpleAdapter(this, travelsList(), R.layout.travel_list, de, para);
        adapter.setViewBinder(new TravelsViewBinder());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

        //alerts
        this.alertDialog = createAlertDialog();
        this.dialogConfirm = createDialogConfirm();

        //Persistence
        helper = new DataBaseHelper(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = travels.get(position);
        String destiny = (String) map.get("destino");
        String message = "Viagem selecionada " + destiny;

        this.selectedTravel = position;
        alertDialog.show();

//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, CostListActivity.class));
    }


    private List<Map<String, Object>> travelsList() {
        DataBaseHelper h = new DataBaseHelper(this);
        SQLiteDatabase db = h.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, tipo_viagem, destino, " +
        "data_chegada, data_saida, orcamento FROM viagem", null);

        cursor.moveToFirst();

        travels = new ArrayList<Map<String,Object>>();

        for (int i = 0; i<cursor.getCount(); i++){
            Map<String, Object> item = new HashMap<String, Object>();

            String id = cursor.getString(0);
            int tipoViagem = cursor.getInt(1);
            String destino = cursor.getString(2);
            String dataChegada = cursor.getString(3);
            String dataSaida = cursor.getString(4);
            double orcamento = cursor.getDouble(5);

            item.put("id", id);

            if(tipoViagem == Constantes.TRAVEL_RECREATION){
                item.put("imagem", R.drawable.recreation);
            } else {
                item.put("imagem", R.drawable.business);
            }

            item.put("destino", destino);

            String periodo = dataChegada + " a " + dataSaida;
            item.put("data", periodo);

            double totalGasto = calcularTotalGasto(db, id);

            item.put("total", "Gasto total R$ " + totalGasto);

            double alerta = orcamento * limitValue/100;
            Double[] valores = new Double[]{orcamento, alerta, totalGasto};
            item.put("barraProgresso", valores);

            travels.add(item);
            cursor.moveToNext();
        }
        cursor.close();

        return travels;
    }

    private double calcularTotalGasto(SQLiteDatabase db, String id){
        Cursor cursor = db.rawQuery("SELECT SUM(valor) FROM gasto WHERE viagem_id = ?", new String[]{id});
        cursor.moveToFirst();
        double total = cursor.getDouble(0);
        cursor.close();
        return total;
    }

    private class TravelsViewBinder implements SimpleAdapter.ViewBinder {

        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if (view.getId() == R.id.progressBarId) {
                Double values[] = (Double[]) data;
                ProgressBar progressBar = (ProgressBar) view;
                progressBar.setMax(values[0].intValue());
                progressBar.setSecondaryProgress(values[1].intValue());
                progressBar.setProgress(values[2].intValue());
                return true;
            }
            return false;
        }
    }

    private AlertDialog createAlertDialog(){
        final CharSequence[] items = {
                getString(R.string.lbl_edit),
                getString(R.string.lbl_new_cost),
                getString(R.string.lbl_performed_costs),
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

    @Override
    public void onClick(DialogInterface dialog, int item) {
        Intent intent;
        String id = (String) travels.get(selectedTravel).get("id");
        String name = (String) travels.get(selectedTravel).get("destino");

        switch (item){
            case 0:
                intent = new Intent(this, TravelActivity.class);
                intent.putExtra(Constantes.TRAVEL_ID, id);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, CostActivity.class);
                intent.putExtra(Constantes.TRAVEL_ID, id);
                intent.putExtra(Constantes.TRAVEL_DESTINY, name);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, CostListActivity.class);
                intent.putExtra(Constantes.TRAVEL_ID, id);
                startActivity(intent);
                break;
            case 3:
                dialogConfirm.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                travels.remove(this.selectedTravel);
                removerViagem(id);
                getListView().invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialogConfirm.dismiss();
                break;
        }
    }

    private void removerViagem(String id){
        SQLiteDatabase db = helper.getWritableDatabase();
        String where[] = new String[]{id};
        db.delete("gasto", "viagem_id = ?", where);
        db.delete("viagem", "_id", where);
    }
}
