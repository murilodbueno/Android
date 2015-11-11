package luxfacta.com.br.controlefrota_treinamento.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mbueno on 26/08/2015.
 */
public class Avarias {

    @Expose
    @SerializedName("idVeiculo")
    private Long idVeiculo;

    @Expose
    @SerializedName("idPeca")
    private Long idPeca;

    @Expose
    @SerializedName("imagem")
    private String imagem;

    @Expose
    @SerializedName("descricao")
    private String descricao;

    @Expose
    @SerializedName("status")
    private int status;

    @Expose
    @SerializedName("dtOcorrencia")
    private String dtOcorrencia;

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public Long getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(Long idPeca) {
        this.idPeca = idPeca;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDtOcorrencia() {
        return dtOcorrencia;
    }

    public void setDtOcorrencia(String dtOcorrencia) {
        this.dtOcorrencia = dtOcorrencia;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
