package luxfacta.com.br.controlefrota_treinamento.enumeration;

import luxfacta.com.br.controlefrota_treinamento.R;
import luxfacta.com.br.controlefrota_treinamento.util.Constants;

/**
 * Created by llima on 26/08/2015.
 */
public enum AvariasEnum {

    EXTINTOR(1, Constants.TIPO_PECA_INTERNA, R.id.sw_extintor),
    DOCUMENTO(2, Constants.TIPO_PECA_INTERNA, R.id.sw_documento),
    CARTAO_COMBUSTIVEL(3, Constants.TIPO_PECA_INTERNA, R.id.sw_cartao_combustivel),
    GRADE(4, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_02),
    FAROL_ESQUERDO(5, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_01),
    FAROL_DIREITO(6, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_03),
    PARABRISA(7, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_10),
    CAPO(8, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_04),
    LANTERNA_ESQUERDA(9, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_27),
    LANTERNA_DIREITA(10, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_28),
    PARA_CHOQUE(11, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_29),
    PNEU_DIANT_ESQUERDO(12, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_07),
    PNEU_DIANT_DIREITO(13, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_08),
    PNEU_TRAS_ESQUERDO(14, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_21),
    PNEU_TRAS_DIREITO(15, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_24),
    PARALAMA_DIANT_ESQUERDO(16, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_05),
    PARALAMA_DIANT_DIREITO(17, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_06),
    PARALAMA_TRAS_ESQUERDO(18, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_22),
    PARALAMA_TRAS_DIREITO(19, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_23),
    RETROVISOR_ESQUERDO(20, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_09),
    RETROVISOR_DIREITO(21, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_11),
    PORTA_MOTORISTA(22, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_12),
    PORTA_PASSAGEIRO(23, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_15),
    PORTA_MOTORISTA_TRAS(24, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_17),
    PORTA_PASSAGEIRO_TRAS(25, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_20),
    VIDRO_LATERAL_MOTORISTA(26, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_13),
    VIDRO_LATERAL_PASSAGEIRO(27, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_14),
    VITRO_LATERAL_TRAS_MOTORISTA(28, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_18),
    VITRO_LATERAL_TRAS_PASSAGEIRO(29, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_19),
    TETO(30, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_16),
    VIDRO_TRASEIRO(31, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_25),
    PORTA_MALAS(32, Constants.TIPO_PECA_EXTERNA, R.id.avaria_bt_26),
    PNEU_RESERVA(33, Constants.TIPO_PECA_INTERNA, R.id.sw_pneu_reserva),
    MACACO(34, Constants.TIPO_PECA_INTERNA, R.id.sw_macaco),
    TRIANGULO(35, Constants.TIPO_PECA_INTERNA, R.id.sw_triangulo),
    NIVEL_COMBUSTIVEL(36, Constants.TIPO_PECA_INTERNA, R.id.radio_group_combustivel);

    private final int idPeca, tipoAvaria, btAvaria;

    AvariasEnum(int idPeca, int tipoAvaria, int btAvaria) {
        this.idPeca = idPeca;
        this.tipoAvaria = tipoAvaria;
        this.btAvaria = btAvaria;
    }

    public int getIdPeca() {
        return idPeca;
    }

    public int getTipoAvaria() {
        return tipoAvaria;
    }

    public int getBtAvaria() {
        return btAvaria;
    }

    public static Integer getButtonId(Integer idPeca, Integer tpPeca){
        for(AvariasEnum ae : values()){
            if( ae.getIdPeca() == idPeca && ae.getTipoAvaria() == tpPeca) {
                return ae.getBtAvaria();
            }
        }
        return -1;
    }

    public static Integer findPecaExterna(Integer idPeca){
        for(AvariasEnum stv : values()){
            if( stv.getIdPeca() == idPeca && stv.getTipoAvaria() == Constants.TIPO_PECA_EXTERNA) {
                return stv.getIdPeca();
            }
        }
        return -1;
    }

    public static Integer findIdPecaByView(Integer viewId) {
        for(AvariasEnum stv : values()){
            if (stv.getBtAvaria() == viewId) {
                return stv.getIdPeca();
            }
        }
        return -1;
    }

    public static Integer findPecaInterna(Integer idPeca){
        for(AvariasEnum stv : values()){
            if( stv.getIdPeca() == idPeca && stv.getTipoAvaria() == Constants.TIPO_PECA_INTERNA) {
                return stv.getIdPeca();
            }
        }
        return -1;
    }
}
