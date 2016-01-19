package br.com.mbueno.filereader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by mbueno on 18/01/2016.
 */
public class ImageActivity extends Activity {

    ImageView imageView;
    EditText zoom;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent i = getIntent();
        String picturePath = i.getStringExtra("image");

        imageView = (ImageView) findViewById(R.id.imgView);
        zoom = (EditText) findViewById(R.id.editText);
        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        mAttacher = new PhotoViewAttacher(imageView);
        mAttacher.update();

        zoom.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                while(event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.ACTION_DOWN){
                    float valor = Float.valueOf(zoom.getText().toString());
                    mAttacher.setScale((float) (valor*0.01 + mAttacher.getScale()), true);
                    mAttacher.update();
                    Log.i("zoom-true", String.valueOf(mAttacher.getScale()));
                    return true;
                }
                Log.i("zoom-false", String.valueOf(mAttacher.getScale()));
                return false;
            }
        });
    }
}
