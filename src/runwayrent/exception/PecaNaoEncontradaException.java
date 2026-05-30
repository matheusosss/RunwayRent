package runwayrent.exception;

/**
 * Lançada quando uma peça não é encontrada no sistema.
 */
public class PecaNaoEncontradaException extends Exception {

    public PecaNaoEncontradaException(String id) {
        super("Peça com ID '" + id + "' não encontrada no sistema.");
    }
}
