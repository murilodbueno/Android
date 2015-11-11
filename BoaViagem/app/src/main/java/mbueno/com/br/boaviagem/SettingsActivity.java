package mbueno.com.br.boaviagem;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by mbueno on 12/08/2015.
 */
public class SettingsActivity extends PreferenceActivity {

    public static final String ACTION_SHOW_SETTINGS = "boaviagem.ACTION_SHOW_SETTINGS";
    public static final String CATEGORY_SETTINGS = "boaviagem.CATEGORY_SETTINGS";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //TODO utilizar novo padrao
        addPreferencesFromResource(R.xml.preference);
    }

}
