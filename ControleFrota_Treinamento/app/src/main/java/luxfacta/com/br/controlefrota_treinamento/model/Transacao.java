package luxfacta.com.br.controlefrota_treinamento.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Luxfacta on 20/08/2015.
 */
public class Transacao extends SugarRecord<Transacao>{

    private Long codigo;
    private Date dataTransacaoAdd;
    private Veiculo veiculo = new Veiculo();
    private Motorista motorista = new Motorista();
    private Long tipoTransacao;
    private int sentToServer;

    public Transacao(){}

    public Transacao(Long codigo, Date dataTransacaoAdd, Veiculo veiculo, Motorista motorista, Long tipoTransacao){
        this.codigo = codigo;
        this.dataTransacaoAdd = dataTransacaoAdd;
        this.veiculo = veiculo;
        this.motorista = motorista;
        this.tipoTransacao = tipoTransacao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getDataTransacaoAdd() {
        return dataTransacaoAdd;
    }

    public void setDataTransacaoAdd(Date dataTransacaoAdd) {
        this.dataTransacaoAdd = dataTransacaoAdd;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Long getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(Long tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public int getSentToServer() {
        return sentToServer;
    }

    public void setSentToServer(int sentToServer) {
        this.sentToServer = sentToServer;
    }
}
