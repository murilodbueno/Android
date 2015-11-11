package luxfacta.com.br.controlefrota_treinamento.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mbueno on 26/08/2015.
 */
public class Transacoes {

    @Expose
    @SerializedName("idMotorista")
    private Long idMotorista;

    @Expose
    @SerializedName("idVeiculo")
    private Long idVeiculo;

    @Expose
    @SerializedName("tipo")
    private Long tipo;

    @Expose
    @SerializedName("dtOcorrencia")
    private String dtOcorrencia;

    public Long getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(Long idMotorista) {
        this.idMotorista = idMotorista;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public Long getTipo() {
        return tipo;
    }

    public void setTipo(Long tipo) {
        this.tipo = tipo;
    }

    public String getDtOcorrencia() {
        return dtOcorrencia;
    }

    public void setDtOcorrencia(String dtOcorrencia) {
        this.dtOcorrencia = dtOcorrencia;
    }
}
