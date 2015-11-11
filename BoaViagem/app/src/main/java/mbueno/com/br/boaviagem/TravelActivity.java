package mbueno.com.br.boaviagem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import mbueno.com.br.persistence.DataBaseHelper;
import mbueno.com.br.util.Constantes;
import mbueno.com.br.util.DateFormat;

/**
 * Created by mbueno on 11/08/2015.
 */
public class TravelActivity extends Activity {

    public static final String ACTION_SHOW_NEW_TRAVEL = "boaviagem.ACTION_SHOW_NEW_TRAVEL";
    public static final String CATEGORY_NEW_TRAVEL = "boaviagem.CATEGORY_NEW_TRAVEL";

    private int yearDateStart, monthDateStar, dayDateStart, yearDateEnd, monthDateEnd, dayDateEnd;
    private Button travelStart, travelEnd;

    //Persistence
    private DataBaseHelper helper;
    private EditText destiny, amountPeople, budget;
    private RadioGroup radioGroup;
    private String id;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.new_travel);

        Calendar calendarStart = Calendar.getInstance();
        yearDateStart = calendarStart.get(Calendar.YEAR);
        monthDateStar = calendarStart.get(Calendar.MONTH);
        dayDateStart = calendarStart.get(Calendar.DAY_OF_MONTH);

        travelStart = (Button) findViewById(R.id.dateArrivalId);
        travelStart.setText(dayDateStart + "/" + (monthDateStar + 1) + "/" + yearDateStart);

        Calendar calendarEnd = Calendar.getInstance();
        yearDateEnd = calendarEnd.get(Calendar.YEAR);
        monthDateEnd = calendarEnd.get(Calendar.MONTH);
        dayDateEnd = calendarEnd.get(Calendar.DAY_OF_MONTH);

        travelEnd = (Button) findViewById(R.id.dateExitId);
        travelEnd.setText(dayDateEnd + "/" + (monthDateEnd + 1) + "/" + yearDateEnd);

        //Persistence
        destiny = (EditText) findViewById(R.id.destinyId);
        amountPeople = (EditText) findViewById(R.id.amountPeopleId);
        budget = (EditText) findViewById(R.id.budgetId);
        radioGroup = (RadioGroup) findViewById(R.id.travelTypeId);


        //prepare access databse
        helper = new DataBaseHelper(this);
        id = getIntent().getStringExtra(Constantes.TRAVEL_ID);

        if(id != null){
            prepararEdicao();
        }
    }

    private void prepararEdicao(){
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT tipo_viagem, destino, data_chegada, data_saida, " +
        "quantidade_pessoas, orcamento FROM viagem WHERE _id = ?", new String[]{id});

        cursor.moveToFirst();

        if(cursor.getInt(0) == Constantes.TRAVEL_RECREATION){
            radioGroup.check(R.id.recreationId);
        } else {
            radioGroup.check(R.id.businessId);
        }

        destiny.setText(cursor.getString(1));
        travelStart.setText(cursor.getString(2));
        travelEnd.setText(cursor.getString(3));
        amountPeople.setText(cursor.getString(4));
        budget.setText(cursor.getString(5));
        cursor.close();
    }

    //method to save into database
    public void saveTravel(View view){

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("destino", destiny.getText().toString());
        values.put("data_chegada", travelStart.getText().toString());
        values.put("data_saida", travelEnd.getText().toString());
        values.put("orcamento", budget.getText().toString());
        values.put("quantidade_pessoas", amountPeople.getText().toString());

        int tipo = radioGroup.getCheckedRadioButtonId();

        if(tipo == R.id.recreationId){
            values.put("tipo_viagem", Constantes.TRAVEL_RECREATION);
        } else {
            values.put("tipo_viagem", Constantes.TRAVEL_BUSINESS);
        }

        long resultado;
        if(id == null){
            resultado = db.insert("viagem", null, values);
        } else {
            resultado = db.update("viagem", values, "_id = ?", new String[]{id});
        }

        if(resultado != -1){
            Toast.makeText(this, getString(R.string.lbl_save_success), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.lbl_save_failed), Toast.LENGTH_SHORT).show();
        }

    }

    public void selectDate(View view){
        //Utilizar DialogFragment
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(R.id.dateArrivalId == id){
            return new DatePickerDialog(this, listenerStart, yearDateStart, monthDateStar, dayDateStart);
        }
        if(R.id.dateExitId == id){
            return new DatePickerDialog(this, listenerEnd, yearDateEnd, monthDateEnd, dayDateEnd);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener listenerStart = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yearDateStart = year;
            monthDateStar = monthOfYear;
            dayDateStart = dayOfMonth;
            travelStart.setText(dayDateStart + "/" + (monthDateStar+1) + "/" + yearDateStart);
        }
    };

    private DatePickerDialog.OnDateSetListener listenerEnd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yearDateEnd = year;
            monthDateEnd = monthOfYear;
            dayDateEnd = dayOfMonth;
            travelEnd.setText(dayDateEnd + "/" + (monthDateEnd+1) + "/" + yearDateEnd);
        }
    };

    @Override
    protected void onDestroy(){
        helper.close();
        super.onDestroy();
    }
}
