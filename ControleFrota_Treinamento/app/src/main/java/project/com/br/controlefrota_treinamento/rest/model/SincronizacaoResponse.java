package project.com.br.controlefrota_treinamento.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mbueno on 26/08/2015.
 */
public class SincronizacaoResponse {

    @Expose
    @SerializedName("status")
    private int status;

    @Expose
    @SerializedName("descricao")
    private String descricao;

    @Expose
    @SerializedName("dtUltAtualizacao")
    private String dtUltAtualizacao;

    @Expose
    @SerializedName("veiculos")
    private List<Veiculos> veiculos;

    @Expose
    @SerializedName("motoristas")
    private List<Motoristas> motoristas;

    public SincronizacaoResponse(int status, String descricao, String dtUltAtualizacao,
                                 List<Veiculos> veiculos, List<Motoristas> motoristas){
        this.status = status;
        this.descricao = descricao;
        this.dtUltAtualizacao = dtUltAtualizacao;
        this.veiculos = veiculos;
        this.motoristas = motoristas;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDtUltAtualizacao() {
        return dtUltAtualizacao;
    }

    public void setDtUltAtualizacao(String dtUltAtualizacao) {
        this.dtUltAtualizacao = dtUltAtualizacao;
    }

    public List<Veiculos> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculos> veiculos) {
        this.veiculos = veiculos;
    }

    public List<Motoristas> getMotoristas() {
        return motoristas;
    }

    public void setMotoristas(List<Motoristas> motoristas) {
        this.motoristas = motoristas;
    }
}
