package luxfacta.com.br.controlefrota_treinamento.model;

import com.orm.SugarRecord;

/**
 * Created by Luxfacta on 20/08/2015.
 */
public class Perfil extends SugarRecord<Perfil>{

    private Long codigo;
    private String descricao;

    public Perfil(){}

    public Perfil(Long codigo, String descricao){
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
