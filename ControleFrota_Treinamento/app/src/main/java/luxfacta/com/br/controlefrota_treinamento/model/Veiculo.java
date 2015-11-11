package luxfacta.com.br.controlefrota_treinamento.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Luxfacta on 20/08/2015.
 */
public class Veiculo extends SugarRecord<Veiculo> implements Parcelable {

    private Long codigo;
    private String placa;
    private String modelo;
    private String marca;
    private Integer chaveCodigo;
    private Date dataCadastro;
    private Date dataAtualizacao;
    private Integer statusTransacao;
    private Unidade unidade = new Unidade();

    public Veiculo(){}

    public Veiculo(Long codigo, String placa, String modelo, String marca, Integer chaveCodigo,
                   Date dataCadastro, Date dataAtualizacao, Unidade unidade){
        this.codigo = codigo;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.chaveCodigo = chaveCodigo;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
        this.unidade = unidade;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(codigo);
        out.writeString(placa);
        out.writeString(modelo);
        out.writeString(marca);
        out.writeInt(chaveCodigo);
        if (dataCadastro != null)
            out.writeLong(dataCadastro.getTime());
        else
            out.writeLong(0);

        if (dataAtualizacao != null)
            out.writeLong(dataAtualizacao.getTime());
        else
            out.writeLong(0);
        out.writeInt(statusTransacao);
        out.writeParcelable(unidade, flags);
    }

    public static final Parcelable.Creator<Veiculo> CREATOR
            = new Parcelable.Creator<Veiculo>() {
        public Veiculo createFromParcel(Parcel in) {
            return new Veiculo(in);
        }

        public Veiculo[] newArray(int size) {
            return new Veiculo[size];
        }
    };

    private Veiculo(Parcel in) {
        codigo = in.readLong();
        placa  = in.readString();
        modelo = in.readString();
        marca = in.readString();
        chaveCodigo = in.readInt();
        dataCadastro = new Date(in.readLong());
        dataAtualizacao = new Date(in.readLong());
        statusTransacao = in.readInt();
        unidade = in.readParcelable(Unidade.class.getClassLoader());
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getChaveCodigo() {
        return chaveCodigo;
    }

    public void setChaveCodigo(Integer chaveCodigo) {
        this.chaveCodigo = chaveCodigo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Integer getStatusTransacao() {
        return statusTransacao;
    }

    public void setStatusTransacao(Integer statusTransacao) {
        this.statusTransacao = statusTransacao;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    @Override
    public String toString() {
        return "Carro " + chaveCodigo + " / " + placa + " / " + modelo + " - " + marca;
    }
}
