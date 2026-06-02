# RunwayRent — Sistema de Gerenciamento de Aluguel de Peças de Alta Costura

> Projeto Integrador I — A2  
> Curso de Análise e Desenvolvimento de Sistemas

---

Integrantes

| Nome | Matrícula |
|------|-----------|
| Matheus de Oliveira dos Santos | 1260116266 |
| Ana Luiza de Castro | 1250116586 |
| Luiz Felipe Sousa e Sena | 1250109314 |
| Leonardo Assis Rocha dos Santos | 1250115659 |
| Sibelle Mendes Maciel | 1250111964 |

---

Contextualização do Mini-Mundo

A **RunwayRent** é uma empresa especializada no aluguel de peças de alta costura para eventos, operando por meio de assinatura mensal. Cada cliente possui uma **cota de peças por mês**, respeitando o limite contratado. O sistema controla a disponibilidade das peças por data, realiza o registro de devoluções, avalia possíveis danos nas peças devolvidas e aplica **multas proporcionais ao valor de mercado** quando necessário. Peças danificadas ficam indisponíveis durante o período de restauração.

---

Atores do Sistema

| Ator | Função |
|------|--------|
| **Cliente** | Solicita e devolve roupas; consulta peças disponíveis e emprestadas |
| **Loja** | Gerencia empréstimos, cadastros, devoluções, multas e avaliações |
| **Sistema** | Valida regras de negócio, cota mensal, multas e disponibilidade |

---

Diagrama de Casos de Uso

```
┌─────────────────────────────────────────────────────────────┐
│                     Sistema RunwayRent                      │
│                                                             │
│  ┌──────────────────────┐                                   │
│  │ Consultar peças      │◄──── Cliente                      │
│  │ disponíveis          │◄──── Loja                         │
│  └──────────────────────┘                                   │
│                                                             │
│  ┌──────────────────────┐                                   │
│  │ Consultar peças      │◄──── Loja                         │
│  │ emprestadas          │                                   │
│  └──────────────────────┘                                   │
│                                                             │
│  ┌──────────────────────┐                                   │
│  │ Cadastrar peças      │◄──── Loja                         │
│  └──────────────────────┘                                   │
│                                                             │
│  ┌──────────────────────┐                                   │
│  │ Realizar empréstimo  │◄──── Loja                         │
│  └──────────────────────┘                                   │
│                                                             │
│  ┌──────────────────────┐   <<include>>  ┌──────────────┐  │
│  │ Realizar devolução   │───────────────►│  Avaliação   │  │
│  │ da peça              │                │  de danos    │  │
│  └──────────────────────┘                └──────┬───────┘  │
│         ▲ Loja                    <<extend>>     │          │
│                                                  ▼          │
│                                        ┌──────────────────┐ │
│                                        │ Registrar multa  │ │
│                                        │ por danos        │ │
│                                        └──────────────────┘ │
│                                                             │
│  ┌──────────────────────┐                                   │
│  │ Cadastrar cliente    │◄──── Loja                         │
│  └──────────────────────┘                                   │
│                                                             │
│  ┌──────────────────────┐                                   │
│  │ Consultar cliente    │◄──── Loja                         │
│  └──────────────────────┘                                   │
└─────────────────────────────────────────────────────────────┘
```

---

Mapeamento de Casos de Uso

| UC | Caso de Uso | Ator | RF Associado |
|----|-------------|------|--------------|
| UC01 | Cadastrar cliente | Loja | RF02 |
| UC02 | Cadastrar peça | Loja | RF01 |
| UC03 | Consultar peças disponíveis | Loja / Cliente | RF03 |
| UC04 | Consultar peças emprestadas | Loja | RF04 |
| UC05 | Consultar cliente | Loja | RF05 |
| UC06 | Realizar empréstimo | Loja | RF06 |
| UC07 | Realizar avaliação de danos | Loja | RF07 |
| UC08 | Registrar devolução | Loja | RF08 |
| UC09 | Registrar multa por danos | Loja | RF09 |

---

Requisitos Funcionais

| Código | Descrição |
|--------|-----------|
| RF01 | Cadastrar peças |
| RF02 | Cadastrar cliente |
| RF03 | Listar peças disponíveis |
| RF04 | Listar peças emprestadas |
| RF05 | Consultar cliente |
| RF06 | Realizar empréstimo |
| RF07 | Realizar avaliação de danos |
| RF08 | Registrar devolução |
| RF09 | Registrar multa por danos |

---

Requisitos Não Funcionais

| Código | Descrição | Como foi atendido |
|--------|-----------|-------------------|
| RNF01 | Desenvolvido em Java | Código 100% em Java |
| RNF02 | Uso de POO | Classes, herança de exceções, encapsulamento, métodos |
| RNF03 | Execução via console | Menu interativo via `Scanner` |
| RNF04 | Validar exceções | 5 exceções customizadas implementadas |
| RNF05 | Uso de arrays | Arrays fixos nos três serviços |
| RNF06 | Documentação README.md | Este arquivo |

---

Diagrama de Classes (UML — textual)

```
┌────────────────────────────────────────────────────────────────────────────┐
│                              <<model>>                                     │
│                               Cliente                                      │
├────────────────────────────────────────────────────────────────────────────┤
│ - id: String                                                               │
│ - nome: String                                                             │
│ - email: String                                                            │
│ - telefone: String                                                         │
│ - cotaMensal: int                                                          │
│ - pecasEmprestadas: int                                                    │
│ - multaAcumulada: double                                                   │
├────────────────────────────────────────────────────────────────────────────┤
│ + getId(): String                                                          │
│ + getNome(): String                  ... getters ...                       │
│ + setNome(String): void              ... setters ...                       │
│ + possuiCotaDisponivel(): boolean                                          │
│ + incrementarEmprestimo(): void                                            │
│ + decrementarEmprestimo(): void                                            │
│ + adicionarMulta(double): void                                             │
└────────────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────────────┐
│                              <<model>>                                     │
│                                Peca                                        │
├────────────────────────────────────────────────────────────────────────────┤
│ - id: String                                                               │
│ - nome: String                                                             │
│ - marca: String                                                            │
│ - tamanho: String                                                          │
│ - valorMercado: double                                                     │
│ - status: Status  {DISPONIVEL | EMPRESTADA | EM_RESTAURACAO}               │
├────────────────────────────────────────────────────────────────────────────┤
│ + getId(): String                    ... getters ...                       │
│ + setStatus(Status): void            ... setters ...                       │
│ + isDisponivel(): boolean                                                  │
└────────────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────────────┐
│                              <<model>>                                     │
│                             Emprestimo                                     │
├────────────────────────────────────────────────────────────────────────────┤
│ - id: String                                                               │
│ - cliente: Cliente                                                         │
│ - peca: Peca                                                               │
│ - dataEmprestimo: LocalDate                                                │
│ - dataDevolucaoPrevista: LocalDate                                         │
│ - dataDevolucaoEfetiva: LocalDate                                          │
│ - status: StatusEmprestimo  {ATIVO | DEVOLVIDO | DEVOLVIDO_COM_DANO}       │
│ - observacaoDanos: String                                                  │
│ - multaAplicada: double                                                    │
├────────────────────────────────────────────────────────────────────────────┤
│ + getId(): String                    ... getters ...                       │
│ + setStatus(StatusEmprestimo): void  ... setters ...                       │
└────────────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────────────┐
│                             <<service>>                                    │
│                           ClienteService                                   │
├────────────────────────────────────────────────────────────────────────────┤
│ - clientes: Cliente[]                                                      │
│ - totalClientes: int                                                       │
├────────────────────────────────────────────────────────────────────────────┤
│ + cadastrarCliente(id, nome, email, telefone, cotaMensal): void            │
│ + buscarClientePorId(id): Cliente                                          │
│ + listarClientes(): void                                                   │
└────────────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────────────┐
│                             <<service>>                                    │
│                             PecaService                                    │
├────────────────────────────────────────────────────────────────────────────┤
│ - pecas: Peca[]                                                            │
│ - totalPecas: int                                                          │
├────────────────────────────────────────────────────────────────────────────┤
│ + cadastrarPeca(id, nome, marca, tamanho, valorMercado): void              │
│ + buscarPecaPorId(id): Peca                                                │
│ + listarPecasDisponiveis(): void                                           │
│ + listarPecasEmprestadas(): void                                           │
└────────────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────────────────┐
│                             <<service>>                                    │
│                          EmprestimoService                                 │
├────────────────────────────────────────────────────────────────────────────┤
│ - emprestimos: Emprestimo[]                                                │
│ - totalEmprestimos: int                                                    │
│ - clienteService: ClienteService                                           │
│ - pecaService: PecaService                                                 │
│ - PERCENTUAL_MULTA: double = 0.30                                          │
├────────────────────────────────────────────────────────────────────────────┤
│ + realizarEmprestimo(idCliente, idPeca, diasEmprestimo): void              │
│ + realizarDevolucao(idEmprestimo, possuiDano, descricaoDano): void         │
│ - registrarMultaPorDano(emprestimo): void                                  │
│ + concluirRestauracao(idPeca): void                                        │
│ + listarEmprestimosAtivos(): void                                          │
│ + listarHistoricoEmprestimos(): void                                       │
│ + buscarEmprestimoPorId(id): Emprestimo                                    │
└────────────────────────────────────────────────────────────────────────────┘

Exceções customizadas (todas herdam de Exception):
  ├── ClienteNaoEncontradoException
  ├── PecaNaoEncontradaException
  ├── PecaIndisponivelException
  ├── CotaExcedidaException
  ├── EmprestimoNaoEncontradoException
  └── CadastroJaExisteException
```

---

Estrutura do Projeto

```
RunwayRent/
├── README.md
└── src/
    └── runwayrent/
        ├── Main.java
        ├── model/
        │   ├── Cliente.java
        │   ├── Peca.java
        │   └── Emprestimo.java
        ├── service/
        │   ├── ClienteService.java
        │   ├── PecaService.java
        │   └── EmprestimoService.java
        ├── exception/
        │   ├── ClienteNaoEncontradoException.java
        │   ├── PecaNaoEncontradaException.java
        │   ├── PecaIndisponivelException.java
        │   ├── CotaExcedidaException.java
        │   ├── EmprestimoNaoEncontradoException.java
        │   └── CadastroJaExisteException.java
        └── util/
            └── EntradaUtil.java
```

---

Como compilar e executar

### Pré-requisito
- Java 11 ou superior instalado

### Compilar (a partir da pasta `src/`)
```bash
javac -d . runwayrent/exception/*.java runwayrent/model/*.java runwayrent/util/*.java runwayrent/service/*.java runwayrent/Main.java
```

### Executar
```bash
java runwayrent.Main
```

---

Fluxo principal de uso

1. O sistema carrega dados iniciais (2 clientes e 4 peças) para facilitar testes.
2. Acesse **Peças → Listar peças disponíveis** para ver as peças cadastradas.
3. Acesse **Clientes → Consultar cliente** para verificar cotas.
4. Acesse **Empréstimos → Realizar empréstimo**, informe o ID do cliente, o ID da peça e o prazo em dias.
5. Para devolver, acesse **Empréstimos → Realizar devolução**, informe o ID do empréstimo (ex: `EMP001`) e indique se há dano.
6. Se houver dano, a multa é calculada automaticamente (30% do valor de mercado) e a peça entra em restauração.
7. Após restauração, acesse **Peças → Concluir restauração** para tornar a peça disponível novamente.
