package project.com.br.controlefrota_treinamento.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mbueno on 27/08/2015.
 */
public class TransacaoResponse {

    @Expose
    @SerializedName("status")
    private Long status;

    @Expose
    @SerializedName("descricao")
    private String descricao;

    public TransacaoResponse(Long status, String descricao){
        this.status = status;
        this.descricao = descricao;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
