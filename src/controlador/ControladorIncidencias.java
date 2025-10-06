package controlador;

import modelo.Incidencia;
import servicio.ServicioFichero;

import java.util.List;

public class ControladorIncidencias {
    private final ServicioFichero servicio;

    public ControladorIncidencias() {
        this.servicio = new ServicioFichero();
    }


     //Recibe la excepción de la Vista, la prepara y la envía al Servicio.

    public boolean crearIncidencia(String usuario, Exception e) {
        String tipo = e.getClass().getSimpleName(); // Nombre de la clase de excepción
        String mensaje = e.getMessage() != null ? e.getMessage() : "Sin mensaje de error.";

        return servicio.registrarIncidencia(usuario, tipo, mensaje);
    }

    //elega la petición de obtener incidencias por usuario al Servicio.

    public List<Incidencia> obtenerIncidenciasPorUsuario(String usuario) {
        return servicio.buscarIncidenciasPorUsuario(usuario);
    }
}