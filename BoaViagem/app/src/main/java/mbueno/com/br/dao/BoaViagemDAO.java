package mbueno.com.br.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mbueno.com.br.bean.Viagem;
import mbueno.com.br.persistence.DataBaseHelper;

/**
 * Created by mbueno on 13/08/2015.
 */
public class BoaViagemDAO {

    private DataBaseHelper helper;
    private SQLiteDatabase db;

    public BoaViagemDAO(Context context){
        helper = new DataBaseHelper(context);
    }

    private SQLiteDatabase getDB(){
        if(db == null){
            db = helper.getWritableDatabase();
        }
        return db;
    }

    public void close(){
        helper.close();
    }

    public List<Viagem> listarViagens(){
        Cursor cursor = getDB().query(DataBaseHelper.Viagem.TABELA,
                DataBaseHelper.Viagem.COLUNAS,
                null, null, null, null, null);

        List<Viagem> viagens = new ArrayList<Viagem>();

        while(cursor.moveToNext()){
            Viagem viagem = criarViagem(cursor);
            viagens.add(viagem);
        }
        cursor.close();
        return viagens;
    }

    public Viagem criarViagem(Cursor c){
        Viagem v = new Viagem();

        return v;
    }
}
