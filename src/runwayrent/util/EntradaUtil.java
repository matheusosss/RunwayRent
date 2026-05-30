package runwayrent.util;

import java.util.Scanner;

/**
 * Utilitário para leitura de dados do console.
 * Centraliza o Scanner para evitar múltiplas instâncias.
 */
public class EntradaUtil {

    private static final Scanner scanner = new Scanner(System.in);

    private EntradaUtil() {
        // Classe utilitária - não instanciável
    }

    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    public static double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                double valor = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um valor numérico.");
            }
        }
    }

    public static boolean lerBooleano(String mensagem) {
        while (true) {
            System.out.print(mensagem + " (s/n): ");
            String entrada = scanner.nextLine().trim().toLowerCase();
            if (entrada.equals("s") || entrada.equals("sim")) return true;
            if (entrada.equals("n") || entrada.equals("nao") || entrada.equals("não")) return false;
            System.out.println("Digite 's' para sim ou 'n' para não.");
        }
    }
}
