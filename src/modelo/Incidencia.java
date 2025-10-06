package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Incidencia {
    private String usuario;
    private LocalDateTime fecha;
    private String tipoExcepcion;
    private String mensajeError;

    // Formatos para la persistencia y presentación de fecha y hora
    private static final DateTimeFormatter FORMATO_FECHA_EXPORT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FORMATO_HORA_EXPORT = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Formato interno para reconstruir el LocalDateTime (usado en fromString)
    private static final DateTimeFormatter FORMATO_INTERNO = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // Constructor
    public Incidencia(String usuario, String tipoExcepcion, String mensajeError) {
        this.usuario = usuario;
        this.fecha = LocalDateTime.now();
        this.tipoExcepcion = tipoExcepcion;
        // Limpiamos el mensaje de errores que puedan interferir con nuestro separador (;)
        this.mensajeError = mensajeError.replace(";", ",");
    }

    // Getters
    public String getUsuario() {
        return usuario;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public String getTipoExcepcion() {
        return tipoExcepcion;
    }
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * Convierte el objeto en una línea de texto para guardarlo en el fichero.
     * Formato de persistencia: YYYY-MM-DD;HH:MM:SS;TIPO_EXCEPCION;MENSAJE_ERROR;USUARIO
     */
    @Override
    public String toString() {
        String fechaParte = fecha.format(FORMATO_FECHA_EXPORT);
        String horaParte = fecha.format(FORMATO_HORA_EXPORT);

        // Esta es la cadena que se guarda en incidencias.txt
        return fechaParte + ";" + horaParte + ";" + tipoExcepcion + ";" + mensajeError + ";" + usuario;
    }

    /**
     * Recrea el objeto Incidencia desde una línea del fichero.
     */
    public static Incidencia fromString(String linea) {
        String[] partes = linea.split(";");

        // El formato de persistencia tiene 5 partes
        if (partes.length < 5) {
            throw new IllegalArgumentException("Línea de incidencia con formato incorrecto: " + linea);
        }

        //  parsear
        String fechaHoraStr = partes[0] + "T" + partes[1];

        // 2. Crear una instancia temporal (usuario: partes[4], tipo: partes[2], mensaje: partes[3])
        Incidencia i = new Incidencia(partes[4], partes[2], partes[3]);

        // parseamos la  fecha/hora
        i.fecha = LocalDateTime.parse(fechaHoraStr, FORMATO_INTERNO);
        return i;
    }
}