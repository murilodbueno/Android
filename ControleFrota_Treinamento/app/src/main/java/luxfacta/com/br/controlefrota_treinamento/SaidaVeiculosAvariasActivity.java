package luxfacta.com.br.controlefrota_treinamento;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import luxfacta.com.br.controlefrota_treinamento.enumeration.AvariasEnum;
import luxfacta.com.br.controlefrota_treinamento.model.Avaria;
import luxfacta.com.br.controlefrota_treinamento.model.Veiculo;
import luxfacta.com.br.controlefrota_treinamento.rest.model.Avarias;
import luxfacta.com.br.controlefrota_treinamento.util.Constants;

/**
 * Created by Luxfacta on 18/08/2015.
 */
public class SaidaVeiculosAvariasActivity extends Activity {

    private static final int REQUEST_CODE = 100;

    private static final int[] idArray = {R.id.avaria_bt_01, R.id.avaria_bt_02, R.id.avaria_bt_03,
            R.id.avaria_bt_04, R.id.avaria_bt_05, R.id.avaria_bt_06, R.id.avaria_bt_07,
            R.id.avaria_bt_08, R.id.avaria_bt_09, R.id.avaria_bt_10, R.id.avaria_bt_11,
            R.id.avaria_bt_12, R.id.avaria_bt_13, R.id.avaria_bt_14, R.id.avaria_bt_15,
            R.id.avaria_bt_16, R.id.avaria_bt_17, R.id.avaria_bt_18, R.id.avaria_bt_19,
            R.id.avaria_bt_20, R.id.avaria_bt_21, R.id.avaria_bt_22, R.id.avaria_bt_23,
            R.id.avaria_bt_24, R.id.avaria_bt_25, R.id.avaria_bt_26, R.id.avaria_bt_27,
            R.id.avaria_bt_28, R.id.avaria_bt_29
    };

    private ImageButton[] bt = new ImageButton[idArray.length];
    private ImageButton clickedButton;

    private AlertDialog alerta;

    private Map<Integer, Avaria> mapAvarias;
    private Long carroSelecionado, idMotorista;

    private Avaria selectedAvaria;
    private Veiculo veiculoAvaria;
    private boolean alteredState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        List<Avaria> listaAvarias;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.saida_veiculos_avarias);

        if (savedInstanceState != null) {
            carroSelecionado = (Long) savedInstanceState.getSerializable(Constants.CARRO_SELECIONADO);
            idMotorista = (Long) savedInstanceState.getSerializable(Constants.MOTORISTA_SELECIONADO);
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                // Algum erro ocorreu, a activity não recebeu nenhum carro
                Toast.makeText(getBaseContext(), getResources().getString(R.string.generic_error), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                carroSelecionado = (Long) extras.getSerializable(Constants.CARRO_SELECIONADO);
                idMotorista = (Long) extras.getSerializable(Constants.MOTORISTA_SELECIONADO);
            }
        }

        listaAvarias = Select.from(Avaria.class).where(Condition.prop("veiculo").eq(carroSelecionado)).list();
        veiculoAvaria = Select.from(Veiculo.class).where(Condition.prop("codigo").eq(carroSelecionado)).first();
        mapAvarias = new HashMap<>();
        for(Avaria avaria : listaAvarias) {
            int idImgButtonPeca = AvariasEnum.getButtonId(avaria.getPeca(), Constants.TIPO_PECA_EXTERNA);
            if (idImgButtonPeca != -1) {
                mapAvarias.put(idImgButtonPeca, avaria);

                ImageButton ib = (ImageButton) findViewById(idImgButtonPeca);
                ib.setImageResource(R.drawable.circle_shape_button_red);
            }
        }

        for (int i=0; i<idArray.length; i++) {
            bt[i] = (ImageButton) findViewById(idArray[i]); // Fetch the view id from array

            registerForContextMenu(bt[i]);

            bt[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedButton = (ImageButton) v;

                    selectedAvaria = mapAvarias.get(v.getId());
                    //avaria = new Avaria(); //mock
                    if (selectedAvaria == null || selectedAvaria.getStatus() == 0) {
                        invokeCamera();
                    } else {
                        updateDialog(v, selectedAvaria);
                    }
                }
            });
        }

        if (getActionBar() != null) {
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void updateDialog(View itemButton, Avaria avaria) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.avaria_ext_dialog, null);
        final Dialog builder = new Dialog(this);

        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(dialogView);

        byte[] decodedString = Base64.decode(avaria.getFoto(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        ImageView imageAvaria = (ImageView) builder.findViewById(R.id.image_avaria);
        imageAvaria.setImageBitmap(decodedByte);
        imageAvaria.setScaleType(ImageView.ScaleType.FIT_XY);

        TextView title = (TextView) builder.findViewById(R.id.title);
        title.setText(R.string.lbl_confirmar);

        TextView text = (TextView) builder.findViewById(R.id.text);
        String formattedText = getResources().getString(R.string.avarias_ext_alteracao_dialog);
        text.setText(formattedText);

        Button cancelButton = (Button) builder.findViewById(R.id.cancel_button);
        Button confirmButton = (Button) builder.findViewById(R.id.confirm_button);

        cancelButton.setText(R.string.lbl_nao);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedButton = null;
                selectedAvaria = null;
                builder.dismiss();
            }
        });

        confirmButton.setText(R.string.lbl_sim);
        confirmButton.setOnClickListener(new ConfirmationOnClickListener(itemButton) {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                openContextMenu(itemButton);
            }
        });

        builder.show();
    }

    public void proximo(View v){
        Intent intent;
        if (v.getId() == R.id.btn_proximo_saida_avarias_id) {
            ArrayList<Avaria> listaAvarias = new ArrayList<Avaria>(mapAvarias.values());

            intent = new Intent(this, SaidaVeiculosCheckListActivity.class);
            intent.putExtra(Constants.CARRO_SELECIONADO, carroSelecionado);
            intent.putExtra(Constants.MOTORISTA_SELECIONADO, idMotorista);
            intent.putParcelableArrayListExtra(Constants.LISTA_AVARIAS, listaAvarias);
            startActivity(intent);
        }
    }

    public void anterior(View v) {
        if (v.getId() == R.id.btn_anterior_saida_carros_avarias_id) {
            if (alteredState) {
                returnConfirmation();
            } else {
                onBackPressed();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.avaria_menu, menu);
        menu.setHeaderTitle(R.string.lbl_opcoes);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_remove_image :
                selectedAvaria.setStatus(0);
                mapAvarias.put(clickedButton.getId(), selectedAvaria);

                clickedButton.setImageResource(R.drawable.circle_shape_button_green);
                alteredState = true;
                break;
            case R.id.action_change_image :
                invokeCamera();
                break;
        }

//        selectedAvaria = null;
//        clickedButton = null;

        return super.onContextItemSelected(item);
    }

    public boolean invokeCamera() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
    }

    private void returnConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensagem");
        builder.setMessage(getResources().getText(R.string.avarias_ext_return_dialog));
        builder.setPositiveButton(getResources().getText(R.string.lbl_confirmar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        builder.setNegativeButton(getResources().getText(R.string.lbl_cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alerta.dismiss();
            }
        });
        alerta = builder.create();
        alerta.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            String encodedTempImg;

            if (resultCode == RESULT_OK) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();

                encodedTempImg = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Toast.makeText(getBaseContext(), "Avaria cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                encodedTempImg = null;
            }

            if (encodedTempImg != null) {
                if (selectedAvaria == null) {
                    selectedAvaria = new Avaria();
                    selectedAvaria.setDataCadastro(new Date());
                } else if (selectedAvaria.getStatus() == 0){
                    selectedAvaria.setDataAtualizacao(new Date());
                }

                selectedAvaria.setFoto(encodedTempImg);
                selectedAvaria.setPeca(AvariasEnum.findIdPecaByView(clickedButton.getId()));
                selectedAvaria.setVeiculo(veiculoAvaria);
                selectedAvaria.setStatus(1);

                mapAvarias.put(clickedButton.getId(), selectedAvaria);

                clickedButton.setImageResource(R.drawable.circle_shape_button_red);
                alteredState = true;
            }
        }
    }

    public class ConfirmationOnClickListener implements View.OnClickListener {

        View itemButton;

        public ConfirmationOnClickListener(View itemButton) {
            this.itemButton = itemButton;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
