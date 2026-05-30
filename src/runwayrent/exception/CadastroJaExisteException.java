package runwayrent.exception;

/**
 * Lançada quando se tenta cadastrar um cliente ou peça
 * com um ID que já existe no sistema.
 */
public class CadastroJaExisteException extends Exception {

    public CadastroJaExisteException(String tipo, String id) {
        super(tipo + " com ID '" + id + "' já está cadastrado no sistema.");
    }
}
