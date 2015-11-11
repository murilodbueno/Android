package mbueno.com.br.boaviagem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import mbueno.com.br.persistence.DataBaseHelper;
import mbueno.com.br.util.Constantes;

/**
 * Created by mbueno on 11/08/2015.
 */
public class CostActivity extends Activity {

    public static final String ACTION_SHOW_NEW_COST = "boaviagem.ACTION_SHOW_NEW_COST";
    public static final String CATEGORY_NEW_COST = "boaviagem.CATEGORY_NEW_COST";

    private int yearDate, monthDate, dayDate;
    private Button costDate;
    private Spinner category;

    private DataBaseHelper helper;
    private TextView destino;
    private EditText valor;
    private EditText descricao;
    private EditText local;
    private String idCost;
    private String idTravel;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cost);
        Calendar calendar = Calendar.getInstance();
        yearDate = calendar.get(Calendar.YEAR);
        monthDate = calendar.get(Calendar.MONTH);
        dayDate = calendar.get(Calendar.DAY_OF_MONTH);

        costDate = (Button) findViewById(R.id.btnDataId);
        costDate.setText(dayDate + "/" + (monthDate + 1) + "/" + yearDate);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.costCategory, android.R.layout.simple_spinner_item);
        category = (Spinner) findViewById(R.id.categoryId);
        category.setAdapter(adapter);

        String viagemDestino = getIntent().getExtras().getString(Constantes.TRAVEL_DESTINY);
        destino = (TextView) findViewById(R.id.costDestinoId);
        destino.setText(viagemDestino);

        valor = (EditText) findViewById(R.id.valueId);
        descricao = (EditText) findViewById(R.id.descriptionId);
        local = (EditText) findViewById(R.id.placeId);

        helper = new DataBaseHelper(this);
        idCost = getIntent().getStringExtra(Constantes.COST_ID);
        idTravel = getIntent().getStringExtra(Constantes.TRAVEL_ID);

        if(idCost != null){
            prepararEdicao();
        }
    }

    public void selectDate(View view){
        //TODO: Utilizar DialogFragment
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(R.id.btnDataId == id){
            return new DatePickerDialog(this, listener, yearDate, monthDate, dayDate);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yearDate = year;
            monthDate = monthOfYear;
            dayDate = dayOfMonth;
            costDate.setText(dayDate + "/" + (monthDate+1) + "/" + yearDate);
        }
    };

    public void saveCost(View view){
        ContentValues values = new ContentValues();
        values.put("viagem_id", idTravel);
        values.put("data", costDate.getText().toString());
        values.put("valor", valor.getText().toString());
        values.put("descricao", descricao.getText().toString());
        values.put("local", local.getText().toString());
        values.put("categoria", category.getSelectedItem().toString());

        SQLiteDatabase db = helper.getWritableDatabase();

        long resultado;
        if(idCost == null){
           resultado = db.insert("gasto", null, values);
        } else {
           resultado = db.update("gasto", values, "_id = ?", new String[]{idCost});
        }

        if(resultado != -1){
            Toast.makeText(this, getString(R.string.lbl_save_success), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.lbl_save_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private void prepararEdicao(){
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT valor, data, local, categoria, descricao FROM gasto WHERE _id = ?", new String[]{idCost});

        cursor.moveToFirst();

        valor.setText(cursor.getString(0));
        costDate.setText(cursor.getString(1));
        local.setText(cursor.getString(2));


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.costCategory, android.R.layout.simple_spinner_item);
        category = (Spinner) findViewById(R.id.categoryId);
        int spinnerPosition;

        if(cursor.getString(3) != null){
            spinnerPosition = adapter.getPosition(cursor.getString(3));
            category.setSelection(spinnerPosition);
        }

        descricao.setText(cursor.getString(4));

        cursor.close();
    }

}
