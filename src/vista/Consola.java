package vista;

import controlador.ControladorIncidencias;
import modelo.Incidencia;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Consola {

    // Formatos para la presentación de fecha y hora
    private static final DateTimeFormatter FORMATO_FECHA_EXPORT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FORMATO_HORA_EXPORT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final ControladorIncidencias controlador;
    private final Escaner escaner;
    private String usuarioActual = "";

    public Consola() {
        this.controlador = new ControladorIncidencias();
        this.escaner = new Escaner();
    }


     //Metodo llamado desde Main).

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
        // Nota: La carga inicial del fichero se realiza automáticamente al instanciar ServicioFichero , depende de su formato si se carga o no
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
                    System.out.println("¡Adiós! Las incidencias se han mantenido en incidencias.txt");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }


     //Lanza la excepción personalizada basada en la entrada y la captura.

    private void provocarExcepcion() {
        System.out.println("\n--- PROVOCAR EXCEPCIÓN ---");
        System.out.println("Ingrese un carácter para provocar intencionalmente una excepción:");
        System.out.println(" - 'a' para generar NumberFormatException -- test");
        System.out.println(" - 'b' para generar IndexOutOfBoundsException -- test");
        System.out.println(" - Cualquier otra cosa genera una excepción genérica.");
        System.out.print("Entrada: ");
        String entrada = escaner.leerLinea();

        try {
            // Lógica para forzar distintas excepciones
            if (entrada.equalsIgnoreCase("a")) {
                // NumberFormatException
                Integer.parseInt(entrada);
            }
            else if (entrada.equalsIgnoreCase("b")) {
                // IndexOutOfBoundsException
                String s = "";
                char c = s.charAt(0);
            }
            else {
                // IllegalStateException (ExcepcionPersonalizada)
                String mensajeErrorGenerico = "Error: se esperaba un caracter  pero metiste: " + entrada;
                throw new IllegalStateException(mensajeErrorGenerico);
            }
        } catch (Exception e) {
            // Captura cualquier excepción y la registra.
            System.out.println("\n Excepción capturada: " + e.getClass().getSimpleName());

            // La Vista pasa la tarea de crear la incidencia al Controlador
            boolean registrado = controlador.crearIncidencia(usuarioActual, e);

            if (registrado) {
                System.out.println(" Incidencia registrada con éxito para el usuario " + usuarioActual + ".");
            } else {
                System.out.println("Fallo al guardar la incidencia. Verifique el log de errores.");
            }
        }
    }


     //Muestra las incidencias registradas para el usuario actual con el formato .

    private void mostrarIncidenciasUsuario() {
        System.out.println("\n--- INCIDENCIAS DE " + usuarioActual.toUpperCase() + " ---");

        List<Incidencia> misIncidencias = controlador.obtenerIncidenciasPorUsuario(usuarioActual);

        if (misIncidencias.isEmpty()) {
            System.out.println("No se encontraron incidencias registradas para este usuario o hubo un error de lectura.");
            return;
        }

        System.out.println("Total de incidencias encontradas: " + misIncidencias.size());

        //este metodo se llama funciones labda lo repase con la IA muy bueno para la programcion mas funcional , lo inclui , lo entiendo muy bien
        // Aplicamos el formato de salida solicitado en el bucle:
        misIncidencias.forEach(i -> {
            System.out.println("-- " + i.getFecha().format(FORMATO_FECHA_EXPORT) + ";" +
                    i.getFecha().format(FORMATO_HORA_EXPORT) + ";" +
                    i.getTipoExcepcion() + ": " +
                    i.getMensajeError() + ";" +
                    i.getUsuario());
        });
        System.out.println("------------------------------------");
    }
}