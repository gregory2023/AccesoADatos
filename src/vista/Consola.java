package vista;

import controlador.ControladorIncidencias;
import modelo.Incidencia;
import java.util.List;

public class Consola {
    private final ControladorIncidencias controlador;
    private final Escaner escaner;
    private String usuarioActual = "";

    public Consola() {
        this.controlador = new ControladorIncidencias();
        this.escaner = new Escaner();
    }


    //Metodo público para iniciar la aplicación (llamado desde Main).

    public void iniciar() {
        solicitarUsuario();
        mostrarMenuPrincipal();
    }

    private void solicitarUsuario() {
        System.out.println("--- GESTIÓN DE INCIDENCIAS ---");
        System.out.print("Por favor, ingrese su nombre de usuario: ");
        usuarioActual = escaner.leerLinea().trim();
        if (usuarioActual.isEmpty()) {
            System.out.println("El usuario no puede estar vacío. Usando 'Sistema'.");
            usuarioActual = "Sistema";
        }
        System.out.println("Bienvenido/a, " + usuarioActual + ".");
    }

    private void mostrarMenuPrincipal() {
        int opcion = -1;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1.  Provocar una Excepción (Crear Incidencia)");
            System.out.println("2.  Mostrar mis Incidencias (" + usuarioActual + ")");
            System.out.println("0.  Salir");

            // La lectura de la opción delega en el Escaner
            opcion = escaner.leerEntero();

            switch (opcion) {
                case 1:
                    provocarExcepcion();
                    break;
                case 2:
                    mostrarIncidenciasUsuario();
                    break;
                case 0:
                    System.out.println("¡Adiós! Las incidencias se han guardado en incidencias.txt");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void provocarExcepcion() {
        System.out.println("\n--- PROVOCAR EXCEPCIÓN ---");
        System.out.println("Ingrese un carácter para provocar intencionalmente una excepción:");
        System.out.println(" - 'a' genera NumberFormatException");
        System.out.println(" - 'b' genera IndexOutOfBoundsException");
        System.out.println(" - Cualquier otra cosa genera una excepción genérica.");
        System.out.print("Entrada: ");
        String entrada = escaner.leerLinea();

        try {
            // Lógica para forzar distintas excepciones
            if (entrada.equalsIgnoreCase("a")) {
                int num = Integer.parseInt(entrada);
            }
            else if (entrada.equalsIgnoreCase("b")) {
                String s = "";
                char c = s.charAt(0);
            }
            else {
                throw new IllegalStateException("Excepción personalizada de usuario. Entrada: " + entrada);
            }
        } catch (Exception e) {
            System.out.println("\n Excepción capturada: " + e.getClass().getSimpleName());

            // La Vista DELEGA la tarea de crear la incidencia al Controlador
            boolean registrado = controlador.crearIncidencia(usuarioActual, e);

            if (registrado) {
                System.out.println("incidencia registrada con éxito para el usuario " + usuarioActual + ".");
            } else {
                System.out.println(" Fallo al guardar la incidencia. Verifique el log de errores.");
            }
        }
    }

    private void mostrarIncidenciasUsuario() {
        System.out.println("\n--- INCIDENCIAS DE " + usuarioActual.toUpperCase() + " ---");

        List<Incidencia> misIncidencias = controlador.obtenerIncidenciasPorUsuario(usuarioActual);

        if (misIncidencias.isEmpty()) {
            System.out.println("No se encontraron incidencias registradas para este usuario o hubo un error de lectura.");
            return;
        }

        System.out.println("Total de incidencias encontradas: " + misIncidencias.size());
        misIncidencias.forEach(i -> {
            System.out.println("------------------------------------");
            System.out.println("Fecha: " + i.getFecha().toLocalDate() + " | Hora: " + i.getFecha().toLocalTime().withNano(0));
            System.out.println("Tipo: " + i.getTipoExcepcion());
            System.out.println("Mensaje: " + i.getMensajeError());
        });
        System.out.println("------------------------------------");
    }
}