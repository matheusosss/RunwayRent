package runwayrent.exception;

/**
 * Lançada quando um empréstimo não é encontrado no sistema.
 */
public class EmprestimoNaoEncontradoException extends Exception {

    public EmprestimoNaoEncontradoException(String id) {
        super("Empréstimo com ID '" + id + "' não encontrado no sistema.");
    }
}
