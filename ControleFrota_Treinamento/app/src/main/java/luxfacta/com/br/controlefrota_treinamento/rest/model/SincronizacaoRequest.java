package luxfacta.com.br.controlefrota_treinamento.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mbueno on 26/08/2015.
 */
public class SincronizacaoRequest {

    @Expose
    @SerializedName("versao")
    private String versao;

    @Expose
    @SerializedName("metodo")
    private String metodo;

    @Expose
    @SerializedName("unidade")
    private String unidade;

    @Expose
    @SerializedName("dtUltAtualizacao")
    private String dtUltAtualizacao;

    @Expose
    @SerializedName("transacoes")
    private List<Transacoes> transacoes;

    @Expose
    @SerializedName("avarias")
    private List<Avarias> avarias;

    public SincronizacaoRequest(String versao, String metodo, String unidade,
                                String dtUltAtualizacao, List<Transacoes> transacoes,
                                List<Avarias> avarias){
        this.versao = versao;
        this.metodo = metodo;
        this.unidade = unidade;
        this.dtUltAtualizacao = dtUltAtualizacao;
        this.transacoes = transacoes;
        this.avarias = avarias;
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

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getDtUltAtualizacao() {
        return dtUltAtualizacao;
    }

    public void setDtUltAtualizacao(String dtUltAtualizacao) {
        this.dtUltAtualizacao = dtUltAtualizacao;
    }

    public List<Transacoes> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacoes> transacoes) {
        this.transacoes = transacoes;
    }

    public List<Avarias> getAvarias() {
        return avarias;
    }

    public void setAvarias(List<Avarias> avarias) {
        this.avarias = avarias;
    }
}
