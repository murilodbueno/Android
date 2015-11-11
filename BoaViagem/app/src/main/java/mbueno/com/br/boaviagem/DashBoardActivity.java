package mbueno.com.br.boaviagem;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import mbueno.com.br.util.Constantes;

/**
 * Created by mbueno on 11/08/2015.
 */
public class DashBoardActivity extends Activity {

    public static final String ACTION_SHOW_DASHBOARD = "boaviagem.ACTION_SHOW_DASHBOARD";
    public static final String CATEGORY_DASHBOARD = "boaviagem.CATEGORY_DASHBOARD";


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.dashboard);
    }

    /**
     * Select the option: New Travel, New Cost, Travels and Settings
     *
     * Call the Intent specific for each activity
     *
     * @param view
     */
    public void selectOption(View view){
        TextView textView = (TextView) view;
        String option = "Opção: " + textView.getText().toString();
        Toast.makeText(this, option, Toast.LENGTH_LONG).show();

        switch (view.getId()){
            case R.id.newTravelId:
                //TODO buscar destino
                String destino = "teste Cidade";
                Intent intentTravel = new Intent(TravelActivity.ACTION_SHOW_NEW_TRAVEL);
                intentTravel.addCategory(TravelActivity.CATEGORY_NEW_TRAVEL);
                startActivity(new Intent(this, TravelActivity.class));
                break;
            case R.id.newCostId:
                //TODO buscar id e nome destino
                int viagemAtual = 1;
                String destino2 = "São Paulo";

                Intent intentCost = new Intent(CostActivity.ACTION_SHOW_NEW_COST);
                intentCost.addCategory(CostActivity.CATEGORY_NEW_COST);
                intentCost.putExtra(Constantes.TRAVEL_ID, viagemAtual);
                intentCost.putExtra(Constantes.TRAVEL_DESTINY, destino2);
                startActivity(intentCost);
                break;
            case R.id.myTravelsId:
                Intent intentTravelList = new Intent(TravelListActivity.ACTION_SHOW_TRAVEL_LIST);
                intentTravelList.addCategory(TravelListActivity.CATEGORY_TRAVEL_LIST);
                startActivity(new Intent(this, TravelListActivity.class));
                break;
            case R.id.settingsId:
                Intent intentSettings = new Intent(SettingsActivity.ACTION_SHOW_SETTINGS);
                intentSettings.addCategory(SettingsActivity.CATEGORY_SETTINGS);
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

}
