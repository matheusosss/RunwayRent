package runwayrent.model;

/**
 * Representa um cliente da RunwayRent.
 * Cada cliente possui uma cota mensal de peças que pode emprestar.
 */
public class Cliente {

    private String id;
    private String nome;
    private String email;
    private String telefone;
    private int cotaMensal;
    private int pecasEmprestadas;
    private double multaAcumulada;

    public Cliente(String id, String nome, String email, String telefone, int cotaMensal) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cotaMensal = cotaMensal;
        this.pecasEmprestadas = 0;
        this.multaAcumulada = 0.0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public int getCotaMensal() {
        return cotaMensal;
    }

    public int getPecasEmprestadas() {
        return pecasEmprestadas;
    }

    public double getMultaAcumulada() {
        return multaAcumulada;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCotaMensal(int cotaMensal) {
        this.cotaMensal = cotaMensal;
    }

    public void setPecasEmprestadas(int pecasEmprestadas) {
        this.pecasEmprestadas = pecasEmprestadas;
    }

    public void setMultaAcumulada(double multaAcumulada) {
        this.multaAcumulada = multaAcumulada;
    }

    /**
     * Verifica se o cliente ainda possui cota disponível para empréstimo.
     */
    public boolean possuiCotaDisponivel() {
        return pecasEmprestadas < cotaMensal;
    }

    /**
     * Incrementa o contador de peças emprestadas.
     */
    public void incrementarEmprestimo() {
        this.pecasEmprestadas++;
    }

    /**
     * Decrementa o contador de peças emprestadas ao realizar devolução.
     */
    public void decrementarEmprestimo() {
        if (this.pecasEmprestadas > 0) {
            this.pecasEmprestadas--;
        }
    }

    /**
     * Adiciona um valor de multa ao saldo do cliente.
     */
    public void adicionarMulta(double valor) {
        this.multaAcumulada += valor;
    }

    @Override
    public String toString() {
        return "=== Cliente ===\n" +
               "ID       : " + id + "\n" +
               "Nome     : " + nome + "\n" +
               "Email    : " + email + "\n" +
               "Telefone : " + telefone + "\n" +
               "Cota     : " + pecasEmprestadas + "/" + cotaMensal + " peças\n" +
               "Multa    : R$ " + String.format("%.2f", multaAcumulada);
    }
}
