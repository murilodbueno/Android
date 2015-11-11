package mbueno.com.br.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbueno on 13/08/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "BoaViagem.db";
    private static int VERSION = 1;

    public static class Viagem{
        public static final String TABELA = "viagem";
        public static final String _ID = "_id";
        public static final String DESTINO = "destino";
        public static final String DATA_CHEGADA = "data_chegada";
        public static final String DATA_SAIDA = "data_saida";
        public static final String ORCAMENTO = "orcamento";
        public static final String QUANTIDADE_PESSOAS = "quantidade_pessoas";
        public static final String TIPO_VIAGEM = "tipo_viagem";

        public static final String[] COLUNAS = new String[]{
                _ID, DESTINO, DATA_CHEGADA, DATA_SAIDA,
                TIPO_VIAGEM, ORCAMENTO, QUANTIDADE_PESSOAS
        };
    }

    public static class Gasto{
        public static final String TABELA = "gasto";
        public static final String _ID = "_id";
        public static final String VIAGEM_ID = "viagem_id";
        public static final String CATEGORIA = "categoria";
        public static final String DATA = "data";
        public static final String DESCRICAO = "descricao";
        public static final String VALOR = "valor";
        public static final String LOCAL = "local";

        public static final String[] COLUNAS = new String[]{
                _ID, VIAGEM_ID, CATEGORIA, DATA, DESCRICAO,
                VALOR, LOCAL
        };
    }


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Table travel
        db.execSQL("CREATE TABLE viagem (_id INTEGER PRIMARY KEY," +
        " destino TEXT, tipo_viagem INTEGER," +
        " data_chegada DATE, data_saida DATE," +
        " orcamento DOUBLE, quantidade_pessoas INTEGER);");

        //Table cost
        db.execSQL("CREATE TABLE gasto (_id INTEGER PRIMARY KEY," +
                "categoria TEXT, data DATE, valor DOUBLE," +
                "descricao TEXT, local TEXT, viagem_id INTEGER," +
                "FOREIGN KEY(viagem_id) REFERENCES viagem(_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
