package runwayrent.exception;

/**
 * Lançada quando um cliente não é encontrado no sistema.
 */
public class ClienteNaoEncontradoException extends Exception {

    public ClienteNaoEncontradoException(String id) {
        super("Cliente com ID '" + id + "' não encontrado no sistema.");
    }
}
