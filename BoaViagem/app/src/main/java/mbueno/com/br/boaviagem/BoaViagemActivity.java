package mbueno.com.br.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mbueno on 11/08/2015.
 */
public class BoaViagemActivity extends Activity {
    private EditText login;
    private EditText password;
    private CheckBox stayConnected;
    private static final String STAY_CONNECTED = "stay_connected";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //recupera referencia do login e senha da tela e será utilizado para autenticação no
        // método enterOnClick
        login = (EditText) findViewById(R.id.loginUserId);
        password = (EditText) findViewById(R.id.passwordId);

        stayConnected = (CheckBox) findViewById(R.id.loginCheckBoxId);

        SharedPreferences preference = getPreferences(MODE_PRIVATE);
        boolean connected = preference.getBoolean(STAY_CONNECTED, false);

        if(connected){
            startActivity(new Intent(this, DashBoardActivity.class));
        }
    }

    public void enterOnClick(View v){
        String loginInput = login.getText().toString();
        String senhaInput = password.getText().toString();

        if("buba".equals(loginInput) && "".equals(senhaInput)){
            //start dashboard

            SharedPreferences preferences = getPreferences(MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(STAY_CONNECTED, stayConnected.isChecked());
            editor.commit();

            Intent intent = new Intent(DashBoardActivity.ACTION_SHOW_DASHBOARD);
            intent.addCategory(DashBoardActivity.CATEGORY_DASHBOARD);

            startActivity(intent);
        } else {
            //mensagem de erro
            String errorMessage = getString(R.string.lbl_invalid_autenthication);
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
