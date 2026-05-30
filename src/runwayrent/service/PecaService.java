package runwayrent.service;

import runwayrent.exception.CadastroJaExisteException;
import runwayrent.exception.PecaNaoEncontradaException;
import runwayrent.model.Peca;

/**
 * Serviço responsável pelas operações de cadastro e consulta de peças.
 * Utiliza array fixo para armazenamento (RNF05).
 */
public class PecaService {

    private static final int CAPACIDADE_MAXIMA = 200;
    private Peca[] pecas;
    private int totalPecas;

    public PecaService() {
        this.pecas = new Peca[CAPACIDADE_MAXIMA];
        this.totalPecas = 0;
    }

    /**
     * RF01 - Cadastra uma nova peça no sistema.
     *
     * @param id           Identificador único da peça
     * @param nome         Nome da peça
     * @param marca        Marca/grife
     * @param tamanho      Tamanho (PP, P, M, G, GG)
     * @param valorMercado Valor de mercado da peça em reais
     * @throws CadastroJaExisteException se o ID já estiver cadastrado
     */
    public void cadastrarPeca(String id, String nome, String marca, String tamanho, double valorMercado)
            throws CadastroJaExisteException {

        if (totalPecas >= CAPACIDADE_MAXIMA) {
            throw new IllegalStateException("Capacidade máxima de peças atingida.");
        }

        for (int i = 0; i < totalPecas; i++) {
            if (pecas[i].getId().equalsIgnoreCase(id)) {
                throw new CadastroJaExisteException("Peça", id);
            }
        }

        pecas[totalPecas] = new Peca(id, nome, marca, tamanho, valorMercado);
        totalPecas++;
        System.out.println("Peça '" + nome + "' cadastrada com sucesso!");
    }

    /**
     * Busca uma peça pelo seu ID.
     *
     * @param id Identificador da peça
     * @return Peça encontrada
     * @throws PecaNaoEncontradaException se a peça não existir
     */
    public Peca buscarPecaPorId(String id) throws PecaNaoEncontradaException {
        for (int i = 0; i < totalPecas; i++) {
            if (pecas[i].getId().equalsIgnoreCase(id)) {
                return pecas[i];
            }
        }
        throw new PecaNaoEncontradaException(id);
    }

    /**
     * RF03 - Lista todas as peças com status DISPONIVEL.
     */
    public void listarPecasDisponiveis() {
        boolean encontrou = false;
        System.out.println("\n===== PEÇAS DISPONÍVEIS =====");
        for (int i = 0; i < totalPecas; i++) {
            if (pecas[i].isDisponivel()) {
                System.out.println(pecas[i]);
                System.out.println("-----------------------------");
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhuma peça disponível no momento.");
        }
    }

    /**
     * RF04 - Lista todas as peças com status EMPRESTADA.
     */
    public void listarPecasEmprestadas() {
        boolean encontrou = false;
        System.out.println("\n===== PEÇAS EMPRESTADAS =====");
        for (int i = 0; i < totalPecas; i++) {
            if (pecas[i].getStatus() == Peca.Status.EMPRESTADA) {
                System.out.println(pecas[i]);
                System.out.println("-----------------------------");
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhuma peça emprestada no momento.");
        }
    }

    public int getTotalPecas() {
        return totalPecas;
    }
}
