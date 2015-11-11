package project.com.br.controlefrota_treinamento.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by mbueno on 20/08/2015.
 */
public class Avaria extends SugarRecord<Avaria> implements Parcelable {

    private Date dataCadastro;
    private Date dataAtualizacao;
    private String foto;
    private Integer peca;
    private Integer status;
    private Veiculo veiculo = new Veiculo();
    private String descricao;
    private int sentToServer;

    public Avaria(){}

    public Avaria(Date dataCadastro, Date dataAtualizacao, String foto, Integer peca,
                  int status, Veiculo veiculo, String descricao, int sentToServer){
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
        this.foto = foto;
        this.peca = peca;
        this.status = status;
        this.veiculo = veiculo;
        this.descricao = descricao;
        this.sentToServer = sentToServer;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {

        if (dataCadastro != null)
            out.writeLong(dataCadastro.getTime());
        else
            out.writeLong(0);

        if (dataAtualizacao != null)
            out.writeLong(dataAtualizacao.getTime());
        else
            out.writeLong(0);

        out.writeString(foto);
        out.writeInt(peca);
        out.writeInt(status);
        out.writeParcelable(veiculo, flags);
        out.writeString(descricao);

    }

    public static final Parcelable.Creator<Avaria> CREATOR
            = new Parcelable.Creator<Avaria>() {
        public Avaria createFromParcel(Parcel in) {
            return new Avaria(in);
        }

        public Avaria[] newArray(int size) {
            return new Avaria[size];
        }
    };

    private Avaria(Parcel in) {
        dataCadastro  = new Date(in.readLong());
        dataAtualizacao = new Date(in.readLong());
        foto = in.readString();
        peca = in.readInt();
        status = in.readInt();
        veiculo = in.readParcelable(Veiculo.class.getClassLoader());
        descricao = in.readString();
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPeca() {
        return peca;
    }

    public void setPeca(Integer peca) {
        this.peca = peca;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSentToServer() {
        return sentToServer;
    }

    public void setSentToServer(int sentToServer) {
        this.sentToServer = sentToServer;
    }
}
