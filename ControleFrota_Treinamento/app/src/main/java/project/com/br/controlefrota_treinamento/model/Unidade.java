package project.com.br.controlefrota_treinamento.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by mbueno on 20/08/2015.
 */
public class Unidade extends SugarRecord<Unidade> implements Parcelable {

    private String codigo;
    private String nome;

    public Unidade(){}

    public Unidade(String codigo, String nome){
        this.codigo = codigo;
        this.nome = nome;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(codigo);
        out.writeString(nome);
    }

    public static final Parcelable.Creator<Unidade> CREATOR
            = new Parcelable.Creator<Unidade>() {
        public Unidade createFromParcel(Parcel in) {
            return new Unidade(in);
        }

        public Unidade[] newArray(int size) {
            return new Unidade[size];
        }
    };

    private Unidade(Parcel in) {
        codigo = in.readString();
        nome = in.readString();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
