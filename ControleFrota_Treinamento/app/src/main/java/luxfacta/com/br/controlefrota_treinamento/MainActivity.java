package luxfacta.com.br.controlefrota_treinamento;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import luxfacta.com.br.controlefrota_treinamento.model.Unidade;
import luxfacta.com.br.controlefrota_treinamento.util.MyPreferences;

public class MainActivity extends Activity {

    public Unidade unidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferencias =
                this.getSharedPreferences(MyPreferences.PREFERENCES_FILE, MODE_PRIVATE);
        boolean hasUnidade;

        hasUnidade = preferencias.getBoolean(MyPreferences.HAS_UNIDADE, false);
        if (!hasUnidade) {
            startActivity(new Intent(this, SincronizacaoInicialActivity.class));
            finish();
        } else {
            setContentView(R.layout.main);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_information) {
            intent = new Intent(this, InformationActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void selecionarOpcao(View view){

        Intent intent;

        switch (view.getId()){
            case R.id.btn_entrega_chave_id:
                intent = new Intent(this, EntregaChaveCarrosActivity.class);
                startActivity(intent);
                break;

			case R.id.btn_saida_veiculos_id:
                intent = new Intent(this, SaidaVeiculosCarrosActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_entrada_veiculos_id:
                intent = new Intent(this, EntradaVeiculosActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_devolucao_chaves_id:
                intent = new Intent(this, DevolucaoActivity.class);
                startActivity(intent);
                break;
			case R.id.btn_status_id:
                intent = new Intent(this, StatusActivity.class);
                startActivity(intent);
                break;
        }
    }
}
