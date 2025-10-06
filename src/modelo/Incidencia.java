package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Incidencia {
    private String usuario;
    private LocalDateTime fecha;
    private String tipoExcepcion;
    private String mensajeError;

    // Formato para serializar la fecha para que sea fácil de leer y escribir
    //me ayude con este de la IA
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Constructor simple
    public Incidencia(String usuario, String tipoExcepcion, String mensajeError) {
        this.usuario = usuario;
        this.fecha = LocalDateTime.now();
        this.tipoExcepcion = tipoExcepcion;
        // Limpiamos el mensaje de errores que puedan interferir con nuestro separador
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

    @Override
    public String toString() {
        // Formato: USUARIO;FECHA;TIPO_EXCEPCION;MENSAJE_ERROR
        return usuario + ";" + fecha.format(FORMATO_FECHA) + ";" + tipoExcepcion + ";" + mensajeError;
    }

    // Metodo  para recrear la Incidencia desde una línea del fichero dividido por ;
    public static Incidencia fromString(String linea) {
        String[] partes = linea.split(";");

        // Verificación básica del formato
        if (partes.length < 4) {
            throw new IllegalArgumentException("Línea de incidencia con formato incorrecto: " + linea);
        }

        // Crear una instancia temporal solo para rellenar los datos
        Incidencia i = new Incidencia(partes[0], partes[2], partes[3]);

        // Reestablecer la fecha desde el string
        i.fecha = LocalDateTime.parse(partes[1], FORMATO_FECHA);
        return i;
    }
}