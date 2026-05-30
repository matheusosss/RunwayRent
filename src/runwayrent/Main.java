package runwayrent;

import runwayrent.exception.*;
import runwayrent.service.ClienteService;
import runwayrent.service.EmprestimoService;
import runwayrent.service.PecaService;
import runwayrent.util.EntradaUtil;

/**
 * Classe principal do sistema RunwayRent.
 * Gerencia o menu de interação via console.
 *
 * <p>Projeto Integrador I - A2</p>
 *
 * <p>Integrantes:</p>
 * <ul>
 *   <li>Matheus de Oliveira dos Santos   - Matrícula: 1260116266</li>
 *   <li>Ana Luiza de Castro              - Matrícula: 1250116586</li>
 *   <li>Luiz Felipe Sousa e Sena         - Matrícula: 1250109314</li>
 *   <li>Leonardo Assis Rocha dos Santos  - Matrícula: 1250115659</li>
 *   <li>Sibelle Mendes Maciel             - Matrícula: 1250111964</li>
 * </ul>
 */
public class Main {

    private static ClienteService clienteService = new ClienteService();
    private static PecaService pecaService = new PecaService();
    private static EmprestimoService emprestimoService = new EmprestimoService(clienteService, pecaService);

    public static void main(String[] args) {
        exibirBanner();
        carregarDadosIniciais();

        boolean executando = true;
        while (executando) {
            exibirMenuPrincipal();
            int opcao = EntradaUtil.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1  -> menuClientes();
                case 2  -> menuPecas();
                case 3  -> menuEmprestimos();
                case 0  -> executando = false;
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }

        System.out.println("\nEncerrando o sistema RunwayRent. Até logo!");
    }

    // =========================================================
    //  BANNER
    // =========================================================

    private static void exibirBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║         RUNWAYRENT - Alta Costura            ║");
        System.out.println("║     Sistema de Gerenciamento de Aluguéis     ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    // =========================================================
    //  MENU PRINCIPAL
    // =========================================================

    private static void exibirMenuPrincipal() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println(" 1. Clientes");
        System.out.println(" 2. Peças");
        System.out.println(" 3. Empréstimos");
        System.out.println(" 0. Sair");
        System.out.println("=====================================");
    }

    // =========================================================
    //  MENU CLIENTES
    // =========================================================

    private static void menuClientes() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Consultar cliente por ID");
            System.out.println("3. Listar todos os clientes");
            System.out.println("0. Voltar");

            int opcao = EntradaUtil.lerInteiro("Opção: ");
            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> consultarCliente();
                case 3 -> clienteService.listarClientes();
                case 0 -> voltar = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n-- Cadastrar Cliente --");
        String id        = EntradaUtil.lerString("ID do cliente   : ");
        String nome      = EntradaUtil.lerString("Nome completo   : ");
        String email     = EntradaUtil.lerString("E-mail          : ");
        String telefone  = EntradaUtil.lerString("Telefone        : ");
        int cota         = EntradaUtil.lerInteiro("Cota mensal (qtd peças): ");

        try {
            clienteService.cadastrarCliente(id, nome, email, telefone, cota);
        } catch (CadastroJaExisteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void consultarCliente() {
        String id = EntradaUtil.lerString("ID do cliente: ");
        try {
            System.out.println(clienteService.buscarClientePorId(id));
        } catch (ClienteNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // =========================================================
    //  MENU PEÇAS
    // =========================================================

    private static void menuPecas() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- PEÇAS ---");
            System.out.println("1. Cadastrar peça");
            System.out.println("2. Listar peças disponíveis");
            System.out.println("3. Listar peças emprestadas");
            System.out.println("4. Concluir restauração de peça");
            System.out.println("0. Voltar");

            int opcao = EntradaUtil.lerInteiro("Opção: ");
            switch (opcao) {
                case 1 -> cadastrarPeca();
                case 2 -> pecaService.listarPecasDisponiveis();
                case 3 -> pecaService.listarPecasEmprestadas();
                case 4 -> concluirRestauracao();
                case 0 -> voltar = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void cadastrarPeca() {
        System.out.println("\n-- Cadastrar Peça --");
        String id      = EntradaUtil.lerString("ID da peça     : ");
        String nome    = EntradaUtil.lerString("Nome           : ");
        String marca   = EntradaUtil.lerString("Marca/Grife    : ");
        String tamanho = EntradaUtil.lerString("Tamanho        : ");
        double valor   = EntradaUtil.lerDouble("Valor de mercado (R$): ");

        try {
            pecaService.cadastrarPeca(id, nome, marca, tamanho, valor);
        } catch (CadastroJaExisteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void concluirRestauracao() {
        String idPeca = EntradaUtil.lerString("ID da peça restaurada: ");
        try {
            emprestimoService.concluirRestauracao(idPeca);
        } catch (PecaNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // =========================================================
    //  MENU EMPRÉSTIMOS
    // =========================================================

    private static void menuEmprestimos() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- EMPRÉSTIMOS ---");
            System.out.println("1. Realizar empréstimo");
            System.out.println("2. Realizar devolução");
            System.out.println("3. Listar empréstimos ativos");
            System.out.println("4. Histórico de empréstimos");
            System.out.println("0. Voltar");

            int opcao = EntradaUtil.lerInteiro("Opção: ");
            switch (opcao) {
                case 1 -> realizarEmprestimo();
                case 2 -> realizarDevolucao();
                case 3 -> emprestimoService.listarEmprestimosAtivos();
                case 4 -> emprestimoService.listarHistoricoEmprestimos();
                case 0 -> voltar = true;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void realizarEmprestimo() {
        System.out.println("\n-- Realizar Empréstimo --");
        String idCliente = EntradaUtil.lerString("ID do cliente : ");
        String idPeca    = EntradaUtil.lerString("ID da peça    : ");
        int dias         = EntradaUtil.lerInteiro("Prazo (dias)  : ");

        try {
            emprestimoService.realizarEmprestimo(idCliente, idPeca, dias);
        } catch (ClienteNaoEncontradoException | PecaNaoEncontradaException
                 | PecaIndisponivelException | CotaExcedidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void realizarDevolucao() {
        System.out.println("\n-- Registrar Devolução --");
        String idEmprestimo = EntradaUtil.lerString("ID do empréstimo: ");
        boolean possuiDano  = EntradaUtil.lerBooleano("A peça possui dano?");

        String descricaoDano = "";
        if (possuiDano) {
            descricaoDano = EntradaUtil.lerString("Descreva o dano: ");
        }

        try {
            emprestimoService.realizarDevolucao(idEmprestimo, possuiDano, descricaoDano);
        } catch (EmprestimoNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // =========================================================
    //  DADOS INICIAIS (para facilitar testes na apresentação)
    // =========================================================

    private static void carregarDadosIniciais() {
        try {
            clienteService.cadastrarCliente("CLI001", "Fernanda Lima", "fernanda@email.com", "21999990001", 3);
            clienteService.cadastrarCliente("CLI002", "Rafael Souza", "rafael@email.com", "21999990002", 2);

            pecaService.cadastrarPeca("PEC001", "Vestido Chanel Primavera", "Chanel", "P", 12000.00);
            pecaService.cadastrarPeca("PEC002", "Blazer Saint Laurent", "YSL", "M", 8500.00);
            pecaService.cadastrarPeca("PEC003", "Vestido Dior Noite", "Dior", "G", 15000.00);
            pecaService.cadastrarPeca("PEC004", "Saia Valentino Floral", "Valentino", "PP", 6000.00);

            System.out.println("\nDados iniciais carregados com sucesso.");
        } catch (CadastroJaExisteException e) {
            System.out.println("Aviso ao carregar dados: " + e.getMessage());
        }
    }
}
