package project.com.br.controlefrota_treinamento.model;

import com.orm.SugarRecord;

/**
 * Created by mbueno on 20/08/2015.
 */
public class TipoPeca extends SugarRecord<TipoPeca>{

    private Long codigo;
    private String descricao;

    public TipoPeca(){}

    public TipoPeca(Long codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
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
}
