package project.com.br.controlefrota_treinamento.rest.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by mbueno on 27/08/2015.
 */
public class AvariaRequest {

    @Expose
    private String versao;

    @Expose
    private String metodo;

    @Expose
    private Long unidade;

    @Expose
    private List<Avarias> avarias;

    public AvariaRequest(String versao, String metodo, Long unidade, List<Avarias> avarias){
        this.versao = versao;
        this.metodo = metodo;
        this.unidade = unidade;
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

    public Long getUnidade() {
        return unidade;
    }

    public void setUnidade(Long unidade) {
        this.unidade = unidade;
    }

    public List<Avarias> getAvarias() {
        return avarias;
    }

    public void setAvarias(List<Avarias> avarias) {
        this.avarias = avarias;
    }
}
