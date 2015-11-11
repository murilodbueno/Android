package project.com.br.controlefrota_treinamento.enumeration;

/**
 * Created by mbueno on 02/09/2015.
 */
public enum StatusTransacaoMotoristaEnum {

    MOT_SEM_TRANSACAO(0),
    MOT_COM_TRANSACAO(1);

    private final int tipoTransacaoMotorista;

    StatusTransacaoMotoristaEnum(int tipoTransacaoMotorista) {
        this.tipoTransacaoMotorista = tipoTransacaoMotorista;
    }

    public int getTipoTransacaoMotorista() {
        return tipoTransacaoMotorista;
    }

    public static Integer findStatusMotoristaById(Integer idStatus){
        for(StatusTransacaoMotoristaEnum stv : values()){
            if( stv.tipoTransacaoMotorista == idStatus) {
                return stv.getTipoTransacaoMotorista();
            }
        }
        return null;
    }
}
