package servicio;

import modelo.Incidencia;
// Importo la nueva clase a la lista
import modelo.ListaIncidencia;
import repositorio.Fichero;

import java.util.List;

public class ServicioFichero {
    private final Fichero repositorio;
    // La lista ahora es una instancia de ListaIncidencia
    private final ListaIncidencia listaIncidencias;

    public ServicioFichero() {
        this.repositorio = new Fichero();
        this.listaIncidencias = new ListaIncidencia();
        //  Al iniciar el servicio, cargamos todos los datos del fichero a la ListaIncidencia
        cargarDatosIniciales();
    }


     // Carga todos los datos del fichero al inicio del programa.

    private void cargarDatosIniciales() {
        // Obtenemos la lista cargada por el Repositorio
        List<Incidencia> datosCargados = repositorio.cargarTodas();
        // La ListaIncidencia se encarga de agregarlos a su colección
        listaIncidencias.agregarTodas(datosCargados);
        System.out.println("Se cargan " + datosCargados.size() + " incidencias existentes.");
    }


     //Crea un objeto Incidencia, lo guarda en el fichero y lo añade a la lista en memoria.

    public boolean registrarIncidencia(String usuario, String tipoExcepcion, String mensaje) {
        Incidencia nuevaIncidencia = new Incidencia(usuario, tipoExcepcion, mensaje);

        // 1. Persistencia: Guarda en el fichero
        if (repositorio.guardarIncidencia(nuevaIncidencia)) {
            // 2. Memoria: Si la persistencia es exitosa, se añade a la lista en memoria.
            listaIncidencias.agregarIncidencia(nuevaIncidencia);
            return true;
        }
        return false;
    }

     //btiene todas las incidencias de un usuario específico, pidiéndoselas a ListaIncidencia.

    public List<Incidencia> buscarIncidenciasPorUsuario(String usuario) {
        //  Delega la búsqueda y el filtro a la clase ListaIncidencia
        return listaIncidencias.buscarPorUsuario(usuario);
    }
}