package luxfacta.com.br.controlefrota_treinamento.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mbueno on 27/08/2015.
 */
public class TransacaoRequest {

    @Expose
    @SerializedName("versao")
    private String versao;

    @Expose
    @SerializedName("metodo")
    private String metodo;

    @Expose
    @SerializedName("unidade")
    private Long unidade;

    @Expose
    @SerializedName("transacoes")
    private List<Transacoes> transacoes;

    public TransacaoRequest(String versao, String metodo, Long unidade, List<Transacoes> transacoes){
        this.versao = versao;
        this.metodo = metodo;
        this.unidade = unidade;
        this.transacoes = transacoes;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Long getUnidade() {
        return unidade;
    }

    public void setUnidade(Long unidade) {
        this.unidade = unidade;
    }

    public List<Transacoes> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacoes> transacoes) {
        this.transacoes = transacoes;
    }
}
