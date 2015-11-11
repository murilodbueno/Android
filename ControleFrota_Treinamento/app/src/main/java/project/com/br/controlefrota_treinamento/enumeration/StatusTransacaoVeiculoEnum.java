package project.com.br.controlefrota_treinamento.enumeration;

/**
 * Created by llima on 01/09/2015.
 */
public enum StatusTransacaoVeiculoEnum {

    CHAVE_PAINEL(1),
    CHAVE_MOTORISTA_PATIO(2),
    VEICULO_RUA(3);

    private final int tipoTransacaoVeiculo;

    StatusTransacaoVeiculoEnum(int tipoTransacaoVeiculo) {
        this.tipoTransacaoVeiculo = tipoTransacaoVeiculo;
    }

    public int getTipoTransacaoVeiculo() {
        return tipoTransacaoVeiculo;
    }

    public static Integer findStatusVeiculoById(Integer idStatus){
        for(StatusTransacaoVeiculoEnum stv : values()){
            if( stv.tipoTransacaoVeiculo == idStatus) {
                return stv.getTipoTransacaoVeiculo();
            }
        }
        return null;
    }
}
