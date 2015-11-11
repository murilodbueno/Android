package luxfacta.com.br.controlefrota_treinamento.rest.model;

import com.google.gson.annotations.Expose;

/**
 * Created by mbueno on 27/08/2015.
 */
public class AvariaResponse {

    @Expose
    private Long status;

    @Expose
    private String descricao;

    public AvariaResponse(Long status, String descricao){
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
