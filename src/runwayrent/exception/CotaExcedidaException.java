package runwayrent.exception;

/**
 * Lançada quando o cliente tenta realizar um empréstimo
 * além da sua cota mensal permitida.
 */
public class CotaExcedidaException extends Exception {

    public CotaExcedidaException(String nomeCliente, int cota) {
        super("O cliente '" + nomeCliente + "' já atingiu sua cota mensal de " + cota + " peça(s).");
    }
}
