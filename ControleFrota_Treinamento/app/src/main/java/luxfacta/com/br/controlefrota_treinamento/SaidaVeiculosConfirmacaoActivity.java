package luxfacta.com.br.controlefrota_treinamento;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import luxfacta.com.br.controlefrota_treinamento.model.Avaria;
import luxfacta.com.br.controlefrota_treinamento.model.Motorista;

import luxfacta.com.br.controlefrota_treinamento.model.Transacao;
import luxfacta.com.br.controlefrota_treinamento.model.Veiculo;
import luxfacta.com.br.controlefrota_treinamento.util.Constants;

/**
 * Created by Luxfacta on 18/08/2015.
 */
public class SaidaVeiculosConfirmacaoActivity extends Activity{

    private boolean isFilled = false;
    private EditText senha;
    private Motorista cpfMotorisra;
    private TextView funcionario;

    private List<Avaria> listaAvarias;
    private Long idCarro, idMotorista;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saida_veiculos_confirmacao);

        if (savedInstanceState != null) {
            idCarro = (Long) savedInstanceState.getSerializable(Constants.CARRO_SELECIONADO);
            idMotorista = (Long) savedInstanceState.getSerializable(Constants.MOTORISTA_SELECIONADO);
            listaAvarias = savedInstanceState.getParcelableArrayList(Constants.LISTA_AVARIAS);
            cpfMotorisra = Select.from(Motorista.class).where(Condition.prop(Constants.CODIGO).eq(idMotorista)).first();
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                // Algum erro ocorreu, a activity não recebeu nenhum carro
                Toast.makeText(getBaseContext(), getResources().getString(R.string.generic_error), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                idCarro = (Long) extras.getSerializable(Constants.CARRO_SELECIONADO);
                idMotorista = (Long) extras.getSerializable(Constants.MOTORISTA_SELECIONADO);
                listaAvarias = extras.getParcelableArrayList(Constants.LISTA_AVARIAS);
                cpfMotorisra = Select.from(Motorista.class).where(Condition.prop(Constants.CODIGO).eq(idMotorista)).first();
            }
        }

        funcionario = (TextView) findViewById(R.id.confirmacao_funcionario_id);
        funcionario.setText(cpfMotorisra.toString() + ", ");

        if (getActionBar() != null) {
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        senha = (EditText) findViewById(R.id.pw_senha_id);
    }

    public void concluir(View v){
        switch (v.getId()){
            case R.id.btn_concluir_saida_confirmacao_id:
                String check = senha.getText().toString();
                if(!check.matches("")){
                    isFilled = true;
                }
                if(isFilled) {
                    String digitos = cpfMotorisra.getCpf().substring(0,4);
                    if(digitos.equals(check)) {

                        Veiculo vei = Select.from(Veiculo.class).where(Condition.prop(Constants.CODIGO).eq(idCarro)).first();
                        vei.setStatusTransacao(3);
                        vei.save();

                        cpfMotorisra.setStatusTransacao(1);
                        cpfMotorisra.save();

                        Transacao transacao = new Transacao();
                        transacao.setTipoTransacao(2l);
                        transacao.setDataTransacaoAdd(new Date());
                        transacao.setVeiculo(vei);
                        transacao.setSentToServer(0);
                        transacao.setMotorista(cpfMotorisra);
                        transacao.save();

                        for (int i = 0; i < listaAvarias.size(); i++) {
                            listaAvarias.get(i).setVeiculo(vei);
                            listaAvarias.get(i).setDataAtualizacao(new Date());
                            listaAvarias.get(i).setDataCadastro(new Date());
                            listaAvarias.get(i).setSentToServer(0);
                            listaAvarias.get(i).save();
                        }

                        Toast.makeText(getBaseContext(), R.string.saida_veiculos_msg_success, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SaidaVeiculosConfirmacaoActivity.this, MainActivity.class));
                    }else{
                        senha.setError("Senha incorreta.");
                    }
                } else {
                    senha.setError("Preenchimento obrigatório.");
                }
                break;
        }
    }

    public void anterior(View v) {
        switch (v.getId()){
            case R.id.btn_anterior_saida_carros_confirmacao_id:
                onBackPressed();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
