package runwayrent.service;

import runwayrent.exception.CadastroJaExisteException;
import runwayrent.exception.ClienteNaoEncontradoException;
import runwayrent.model.Cliente;

/**
 * Serviço responsável pelas operações de cadastro e consulta de clientes.
 * Utiliza array fixo para armazenamento (RNF05).
 */
public class ClienteService {

    private static final int CAPACIDADE_MAXIMA = 100;
    private Cliente[] clientes;
    private int totalClientes;

    public ClienteService() {
        this.clientes = new Cliente[CAPACIDADE_MAXIMA];
        this.totalClientes = 0;
    }

    /**
     * RF02 - Cadastra um novo cliente no sistema.
     *
     * @param id          Identificador único do cliente
     * @param nome        Nome completo
     * @param email       E-mail de contato
     * @param telefone    Telefone de contato
     * @param cotaMensal  Quantidade máxima de peças por mês
     * @throws CadastroJaExisteException se o ID já estiver cadastrado
     */
    public void cadastrarCliente(String id, String nome, String email, String telefone, int cotaMensal)
            throws CadastroJaExisteException {

        if (totalClientes >= CAPACIDADE_MAXIMA) {
            throw new IllegalStateException("Capacidade máxima de clientes atingida.");
        }

        for (int i = 0; i < totalClientes; i++) {
            if (clientes[i].getId().equalsIgnoreCase(id)) {
                throw new CadastroJaExisteException("Cliente", id);
            }
        }

        clientes[totalClientes] = new Cliente(id, nome, email, telefone, cotaMensal);
        totalClientes++;
        System.out.println("Cliente '" + nome + "' cadastrado com sucesso!");
    }

    /**
     * RF05 - Busca e retorna um cliente pelo seu ID.
     *
     * @param id Identificador do cliente
     * @return Cliente encontrado
     * @throws ClienteNaoEncontradoException se o cliente não existir
     */
    public Cliente buscarClientePorId(String id) throws ClienteNaoEncontradoException {
        for (int i = 0; i < totalClientes; i++) {
            if (clientes[i].getId().equalsIgnoreCase(id)) {
                return clientes[i];
            }
        }
        throw new ClienteNaoEncontradoException(id);
    }

    /**
     * RF05 - Lista todos os clientes cadastrados.
     */
    public void listarClientes() {
        if (totalClientes == 0) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println("\n===== CLIENTES CADASTRADOS =====");
        for (int i = 0; i < totalClientes; i++) {
            System.out.println(clientes[i]);
            System.out.println("--------------------------------");
        }
    }

    public int getTotalClientes() {
        return totalClientes;
    }
}
