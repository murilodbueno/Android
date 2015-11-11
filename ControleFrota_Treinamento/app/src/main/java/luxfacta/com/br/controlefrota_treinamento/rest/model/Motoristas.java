package luxfacta.com.br.controlefrota_treinamento.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mbueno on 26/08/2015.
 */
public class Motoristas {

    @Expose
    @SerializedName("idMotorista")
    private Long idMotorista;

    @Expose
    @SerializedName("nome")
    private String nome;

    @Expose
    @SerializedName("cpf")
    private String cpf;

    public Long getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(Long idMotorista) {
        this.idMotorista = idMotorista;
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
}
