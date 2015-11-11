package luxfacta.com.br.controlefrota_treinamento.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Luxfacta on 20/08/2015.
 */
public class Motorista extends SugarRecord<Motorista> {

    private Long codigo;
    private String nome;
    private String cpf;
    private Date dataCadastro;
    private Date dataAtualizacao;
    private String unidade;
    private Integer statusTransacao;

    public Motorista(){}

    public Motorista(Long codigo, String nome, String cpf, Date dataCadastro, Date dataAtualizacao, String unidade){
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
        this.unidade = unidade;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Integer getStatusTransacao() {
        return statusTransacao;
    }

    public void setStatusTransacao(Integer statusTransacao) {
        this.statusTransacao = statusTransacao;
    }

    @Override
    public String toString() {
        return nome + " - " + cpf;
    }
}
