package project.com.br.controlefrota_treinamento.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mbueno on 26/08/2015.
 */
public class Veiculos {

    @Expose
    @SerializedName("idVeiculo")
    private Long idVeiculo;

    @Expose
    @SerializedName("placa")
    private String placa;

    @Expose
    @SerializedName("marca")
    private String marca;

    @Expose
    @SerializedName("modelo")
    private String modelo;

    @Expose
    @SerializedName("codChave")
    private Integer codChave;

    @Expose
    @SerializedName("transacao")
    private Transacoes transacao;

    @Expose
    @SerializedName("avarias")
    private List<Avarias> avarias;

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
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

    public Integer getCodChave() {
        return codChave;
    }

    public void setCodChave(Integer codChave) {
        this.codChave = codChave;
    }

    public Transacoes getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacoes transacao) {
        this.transacao = transacao;
    }

    public List<Avarias> getAvarias() {
        return avarias;
    }

    public void setAvarias(List<Avarias> avarias) {
        this.avarias = avarias;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
