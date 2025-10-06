package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


 //Clase que gestiona la colección de todas las Incidencias en memoria.

public class ListaIncidencia {

    // Lista interna para almacenar todos los objetos Incidencia
    private List<Incidencia> incidencias;

    public ListaIncidencia() {
        this.incidencias = new ArrayList<>();
    }

     //Agrega una Incidencia a la colección.

    public void agregarIncidencia(Incidencia incidencia) {
        if (incidencia != null) {
            this.incidencias.add(incidencia);
        }
    }


     //agrega una lista completa de Incidencias (útil para la carga inicial desde el fichero).

    public void agregarTodas(List<Incidencia> lista) {
        if (lista != null) {
            this.incidencias.addAll(lista);
        }
    }


    // Obtiene una sublista de incidencias filtrada por el nombre de usuario.

    public List<Incidencia> buscarPorUsuario(String usuario) {
        if (usuario == null || usuario.isEmpty()) {
            return List.of();
        }

        // Aplica el filtro (ignorando mayúsculas/minúsculas)
        return this.incidencias.stream()
                .filter(i -> i.getUsuario().equalsIgnoreCase(usuario))
                .collect(Collectors.toList());
    }

    //traigo todas las incidencias
    public List<Incidencia> getTodasIncidencias() {
        return incidencias;
    }
}