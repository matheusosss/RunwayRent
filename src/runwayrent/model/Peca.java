package runwayrent.model;

/**
 * Representa uma peça de alta costura disponível para aluguel.
 */
public class Peca {

    public enum Status {
        DISPONIVEL,
        EMPRESTADA,
        EM_RESTAURACAO
    }

    private String id;
    private String nome;
    private String marca;
    private String tamanho;
    private double valorMercado;
    private Status status;

    public Peca(String id, String nome, String marca, String tamanho, double valorMercado) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.tamanho = tamanho;
        this.valorMercado = valorMercado;
        this.status = Status.DISPONIVEL;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public String getTamanho() {
        return tamanho;
    }

    public double getValorMercado() {
        return valorMercado;
    }

    public Status getStatus() {
        return status;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public void setValorMercado(double valorMercado) {
        this.valorMercado = valorMercado;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Verifica se a peça está disponível para empréstimo.
     */
    public boolean isDisponivel() {
        return this.status == Status.DISPONIVEL;
    }

    @Override
    public String toString() {
        return "=== Peça ===\n" +
               "ID     : " + id + "\n" +
               "Nome   : " + nome + "\n" +
               "Marca  : " + marca + "\n" +
               "Tam.   : " + tamanho + "\n" +
               "Valor  : R$ " + String.format("%.2f", valorMercado) + "\n" +
               "Status : " + status;
    }
}
