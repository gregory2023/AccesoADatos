package vista;

import java.util.Scanner;

public class Escaner {
    private final Scanner scanner;

    public Escaner() {
        this.scanner = new Scanner(System.in);
    }

    public String leerLinea() {
        return scanner.nextLine();
    }


    public int leerEntero() {
        while (true) {
            String input = leerLinea();
            try {
                // Si la entrada es válida, la devuelve.
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                // Si no es un número, pide de nuevo.
                System.out.print("❗ Entrada no válida. Por favor, ingrese un número: ");
            }
        }
    }
}