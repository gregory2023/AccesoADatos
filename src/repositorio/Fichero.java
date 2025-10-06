package repositorio;

import modelo.Incidencia;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class Fichero {
    private static final String NOMBRE_FICHERO = "datos/incidencias.txt";
    private static final Path RUTA = Path.of(NOMBRE_FICHERO);

    public boolean guardarIncidencia(Incidencia incidencia) {

        try (PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        Files.newBufferedWriter(
                                RUTA,
                                StandardOpenOption.CREATE,
                                StandardOpenOption.APPEND
                        )
                )
        )
        ) {
            // Se usa println para escribir la línea y añadir el salto de línea automáticamente
            pw.println(incidencia.toString());
            pw.flush();
            return true;
        } catch (IOException e) {
            // Captura de error de I/O
            System.err.println(" ERROR del Repositorio: No se pudo escribir la incidencia.");
            System.err.println("Detalles: " + e.getMessage());
            return false;
        }
    }


    public List<Incidencia> cargarTodas() {
        if (!Files.exists(RUTA)) {
            return List.of();
        }

        try {
            // Lee todas las líneas y las mapea a objetos Incidencia
            return Files.readAllLines(RUTA)
                    .stream()
                    .map(Incidencia::fromString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            // Captura de error de I/O
            System.err.println(" ERROR del Repositorio: No se pudo leer el archivo de incidencias.");
            System.err.println("Detalles: " + e.getMessage());
            return List.of(); // Devuelve lista vacía en caso de error
        } catch (Exception e) {
            // Captura si el formato de alguna línea es incorrecto (IllegalArgumentException, etc.)
            System.err.println(" ERROR: Una línea del archivo tiene formato inválido y fue omitida.");
            return List.of();
        }
    }
}