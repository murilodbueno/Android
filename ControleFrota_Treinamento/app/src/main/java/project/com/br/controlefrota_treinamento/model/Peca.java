package project.com.br.controlefrota_treinamento.model;

import com.orm.SugarRecord;

/**
 * Created by mbueno on 20/08/2015.
 */
public class Peca extends SugarRecord<Peca> {

    private Long codigo;
    private String descricao;
    private TipoPeca tipoPeca = new TipoPeca();

    public Peca(){}

    public Peca(Long codigo, String descricao, TipoPeca tipoPeca){
        this.codigo = codigo;
        this.descricao = descricao;
        this.tipoPeca = tipoPeca;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoPeca getTipoPeca() {
        return tipoPeca;
    }

    public void setTipoPeca(TipoPeca tipoPeca) {
        this.tipoPeca = tipoPeca;
    }
}
