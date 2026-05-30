package runwayrent.service;

import runwayrent.exception.ClienteNaoEncontradoException;
import runwayrent.exception.CotaExcedidaException;
import runwayrent.exception.EmprestimoNaoEncontradoException;
import runwayrent.exception.PecaIndisponivelException;
import runwayrent.exception.PecaNaoEncontradaException;
import runwayrent.model.Cliente;
import runwayrent.model.Emprestimo;
import runwayrent.model.Peca;

import java.time.LocalDate;

/**
 * Serviço responsável pelas operações de empréstimo, devolução,
 * avaliação de danos e aplicação de multas.
 */
public class EmprestimoService {

    private static final int CAPACIDADE_MAXIMA = 500;

    /**
     * Percentual do valor de mercado cobrado como multa em caso de dano.
     */
    private static final double PERCENTUAL_MULTA = 0.30;

    private Emprestimo[] emprestimos;
    private int totalEmprestimos;

    private ClienteService clienteService;
    private PecaService pecaService;

    public EmprestimoService(ClienteService clienteService, PecaService pecaService) {
        this.emprestimos = new Emprestimo[CAPACIDADE_MAXIMA];
        this.totalEmprestimos = 0;
        this.clienteService = clienteService;
        this.pecaService = pecaService;
    }

    /**
     * RF06 - Realiza o empréstimo de uma peça para um cliente.
     *
     * @param idCliente              ID do cliente
     * @param idPeca                 ID da peça
     * @param diasEmprestimo         Prazo em dias para devolução
     * @throws ClienteNaoEncontradoException se o cliente não existir
     * @throws PecaNaoEncontradaException    se a peça não existir
     * @throws PecaIndisponivelException     se a peça não estiver disponível
     * @throws CotaExcedidaException         se o cliente atingiu sua cota mensal
     */
    public void realizarEmprestimo(String idCliente, String idPeca, int diasEmprestimo)
            throws ClienteNaoEncontradoException,
                   PecaNaoEncontradaException,
                   PecaIndisponivelException,
                   CotaExcedidaException {

        Cliente cliente = clienteService.buscarClientePorId(idCliente);
        Peca peca = pecaService.buscarPecaPorId(idPeca);

        if (!cliente.possuiCotaDisponivel()) {
            throw new CotaExcedidaException(cliente.getNome(), cliente.getCotaMensal());
        }

        if (!peca.isDisponivel()) {
            throw new PecaIndisponivelException(peca.getNome(), peca.getStatus().toString());
        }

        String idEmprestimo = "EMP" + String.format("%03d", totalEmprestimos + 1);
        LocalDate dataEmprestimo = LocalDate.now();
        LocalDate dataDevolucaoPrevista = dataEmprestimo.plusDays(diasEmprestimo);

        Emprestimo emprestimo = new Emprestimo(idEmprestimo, cliente, peca, dataEmprestimo, dataDevolucaoPrevista);

        peca.setStatus(Peca.Status.EMPRESTADA);
        cliente.incrementarEmprestimo();

        emprestimos[totalEmprestimos] = emprestimo;
        totalEmprestimos++;

        System.out.println("Empréstimo realizado com sucesso!");
        System.out.println(emprestimo);
    }

    /**
     * RF08 - Registra a devolução de uma peça.
     * Inclui obrigatoriamente RF07 (avaliação de danos).
     *
     * @param idEmprestimo   ID do empréstimo
     * @param possuiDano     Indica se a peça foi devolvida com dano
     * @param descricaoDano  Descrição do dano (pode ser null/vazio se não houver dano)
     * @throws EmprestimoNaoEncontradoException se o empréstimo não existir
     */
    public void realizarDevolucao(String idEmprestimo, boolean possuiDano, String descricaoDano)
            throws EmprestimoNaoEncontradoException {

        Emprestimo emprestimo = buscarEmprestimoPorId(idEmprestimo);

        if (emprestimo.getStatus() != Emprestimo.StatusEmprestimo.ATIVO) {
            System.out.println("Este empréstimo já foi encerrado.");
            return;
        }

        emprestimo.setDataDevolucaoEfetiva(LocalDate.now());

        // RF07 - Avaliação de danos
        if (possuiDano && descricaoDano != null && !descricaoDano.trim().isEmpty()) {
            emprestimo.setObservacaoDanos(descricaoDano);
            emprestimo.setStatus(Emprestimo.StatusEmprestimo.DEVOLVIDO_COM_DANO);

            // Peça vai para restauração
            emprestimo.getPeca().setStatus(Peca.Status.EM_RESTAURACAO);

            // RF09 - Aplicação da multa
            registrarMultaPorDano(emprestimo);

        } else {
            emprestimo.setStatus(Emprestimo.StatusEmprestimo.DEVOLVIDO);
            emprestimo.getPeca().setStatus(Peca.Status.DISPONIVEL);
        }

        emprestimo.getCliente().decrementarEmprestimo();

        System.out.println("Devolução registrada com sucesso!");
        System.out.println(emprestimo);
    }

    /**
     * RF09 - Registra multa proporcional ao valor de mercado da peça danificada.
     *
     * @param emprestimo Empréstimo com dano identificado
     */
    private void registrarMultaPorDano(Emprestimo emprestimo) {
        double valorMulta = emprestimo.getPeca().getValorMercado() * PERCENTUAL_MULTA;
        emprestimo.setMultaAplicada(valorMulta);
        emprestimo.getCliente().adicionarMulta(valorMulta);

        System.out.println("Multa aplicada: R$ " + String.format("%.2f", valorMulta)
                + " (30% de R$ " + String.format("%.2f", emprestimo.getPeca().getValorMercado()) + ")");
    }

    /**
     * Marca uma peça como disponível após conclusão de sua restauração.
     *
     * @param idPeca ID da peça restaurada
     * @throws PecaNaoEncontradaException se a peça não existir
     */
    public void concluirRestauracao(String idPeca) throws PecaNaoEncontradaException {
        Peca peca = pecaService.buscarPecaPorId(idPeca);

        if (peca.getStatus() != Peca.Status.EM_RESTAURACAO) {
            System.out.println("A peça '" + peca.getNome() + "' não está em restauração.");
            return;
        }

        peca.setStatus(Peca.Status.DISPONIVEL);
        System.out.println("Peça '" + peca.getNome() + "' restaurada e disponível novamente.");
    }

    /**
     * Lista todos os empréstimos ativos no sistema.
     */
    public void listarEmprestimosAtivos() {
        boolean encontrou = false;
        System.out.println("\n===== EMPRÉSTIMOS ATIVOS =====");
        for (int i = 0; i < totalEmprestimos; i++) {
            if (emprestimos[i].getStatus() == Emprestimo.StatusEmprestimo.ATIVO) {
                System.out.println(emprestimos[i]);
                System.out.println("------------------------------");
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum empréstimo ativo no momento.");
        }
    }

    /**
     * Lista todo o histórico de empréstimos registrados.
     */
    public void listarHistoricoEmprestimos() {
        if (totalEmprestimos == 0) {
            System.out.println("Nenhum empréstimo registrado.");
            return;
        }
        System.out.println("\n===== HISTÓRICO DE EMPRÉSTIMOS =====");
        for (int i = 0; i < totalEmprestimos; i++) {
            System.out.println(emprestimos[i]);
            System.out.println("------------------------------------");
        }
    }

    /**
     * Busca um empréstimo pelo seu ID.
     *
     * @param id Identificador do empréstimo
     * @return Empréstimo encontrado
     * @throws EmprestimoNaoEncontradoException se não existir
     */
    public Emprestimo buscarEmprestimoPorId(String id) throws EmprestimoNaoEncontradoException {
        for (int i = 0; i < totalEmprestimos; i++) {
            if (emprestimos[i].getId().equalsIgnoreCase(id)) {
                return emprestimos[i];
            }
        }
        throw new EmprestimoNaoEncontradoException(id);
    }

    public int getTotalEmprestimos() {
        return totalEmprestimos;
    }
}
