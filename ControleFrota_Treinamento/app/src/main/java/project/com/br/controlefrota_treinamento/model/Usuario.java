package project.com.br.controlefrota_treinamento.model;


import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by mbueno on 20/08/2015.
 */
public class Usuario extends SugarRecord<Usuario>{

    private Perfil perfil;
    private Long codigo;
    private String senha;
    private Date dataCadastro;
    private String nome;

    public Usuario(){}

    public Usuario(Long codigo, String senha, Date dataCadastro, String nome, Perfil perfil){
        this.codigo = codigo;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
        this.nome = nome;
        this.perfil = perfil;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
