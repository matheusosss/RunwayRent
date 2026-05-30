package runwayrent.exception;

/**
 * Lançada quando uma peça não está disponível para empréstimo
 * (já emprestada ou em restauração).
 */
public class PecaIndisponivelException extends Exception {

    public PecaIndisponivelException(String nomePeca, String status) {
        super("A peça '" + nomePeca + "' não está disponível. Status atual: " + status);
    }
}
