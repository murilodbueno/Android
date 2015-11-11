package luxfacta.com.br.controlefrota_treinamento.enumeration;

/**
 * Created by llima on 31/08/2015.
 */
public enum TipoTransacaoEnum {

    ENTREGA_CHAVES(1, "Entrega de chaves"),
    SAIDA_VEICULO(2, "Saída de veículo"),
    ENTRADA_VEICULO(3, "Entrada de veículo"),
    DEVOLUCAO_CHAVES(4, "Devolução de chaves");

    private final long idTipoTransacao;
    private final String descricao;

    TipoTransacaoEnum(long idTipoTransacao, String descricao) {
        this.idTipoTransacao = idTipoTransacao;
        this.descricao = descricao;
    }

    public long getIdTipoTransacao() {
        return idTipoTransacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Long findTipoTransacaoById(Long idTipo){
        for(TipoTransacaoEnum tte : values()){
            if( tte.idTipoTransacao == idTipo) {
                return tte.getIdTipoTransacao();
            }
        }
        return null;
    }
}
