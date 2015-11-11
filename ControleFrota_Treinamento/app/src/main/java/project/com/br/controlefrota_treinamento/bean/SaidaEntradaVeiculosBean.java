package project.com.br.controlefrota_treinamento.bean;

import project.com.br.controlefrota_treinamento.model.Motorista;
import project.com.br.controlefrota_treinamento.model.Veiculo;

/**
 * Created by mbueno on 19/08/2015.
 */
public class SaidaEntradaVeiculosBean {

    private Veiculo carros;
    private Motorista motoristas;

    public SaidaEntradaVeiculosBean(Veiculo carros, Motorista motoristas){
        this.carros = carros;
        this.motoristas = motoristas;
    }

    public Veiculo getCarros() {
        return carros;
    }

    public void setCarros(Veiculo carros) {
        this.carros = carros;
    }

    public Motorista getMotoristas() {
        return motoristas;
    }

    public void setMotoristas(Motorista motoristas) {
        this.motoristas = motoristas;
    }

}
