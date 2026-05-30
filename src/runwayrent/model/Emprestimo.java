package runwayrent.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representa um empréstimo de uma peça para um cliente.
 */
public class Emprestimo {

    public enum StatusEmprestimo {
        ATIVO,
        DEVOLVIDO,
        DEVOLVIDO_COM_DANO
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String id;
    private Cliente cliente;
    private Peca peca;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoEfetiva;
    private StatusEmprestimo status;
    private String observacaoDanos;
    private double multaAplicada;

    public Emprestimo(String id, Cliente cliente, Peca peca, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
        this.id = id;
        this.cliente = cliente;
        this.peca = peca;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.status = StatusEmprestimo.ATIVO;
        this.multaAplicada = 0.0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Peca getPeca() {
        return peca;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoEfetiva() {
        return dataDevolucaoEfetiva;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public String getObservacaoDanos() {
        return observacaoDanos;
    }

    public double getMultaAplicada() {
        return multaAplicada;
    }

    // Setters
    public void setDataDevolucaoEfetiva(LocalDate dataDevolucaoEfetiva) {
        this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public void setObservacaoDanos(String observacaoDanos) {
        this.observacaoDanos = observacaoDanos;
    }

    public void setMultaAplicada(double multaAplicada) {
        this.multaAplicada = multaAplicada;
    }

    @Override
    public String toString() {
        String devolucaoEfetiva = (dataDevolucaoEfetiva != null)
                ? dataDevolucaoEfetiva.format(FORMATTER)
                : "Não devolvida";

        String danos = (observacaoDanos != null && !observacaoDanos.isEmpty())
                ? observacaoDanos
                : "Nenhum";

        return "=== Empréstimo ===\n" +
               "ID              : " + id + "\n" +
               "Cliente         : " + cliente.getNome() + "\n" +
               "Peça            : " + peca.getNome() + "\n" +
               "Data empréstimo : " + dataEmprestimo.format(FORMATTER) + "\n" +
               "Devolução prev. : " + dataDevolucaoPrevista.format(FORMATTER) + "\n" +
               "Devolução efet. : " + devolucaoEfetiva + "\n" +
               "Status          : " + status + "\n" +
               "Danos           : " + danos + "\n" +
               "Multa           : R$ " + String.format("%.2f", multaAplicada);
    }
}
