
package entidades.Caceres.Moreira;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase Utilidades.
 * Contiene métodos estáticos que facilitan operaciones
 * comunes dentro del sistema de biblioteca.
 */
public class Utilidades {

    // Constructor privado para evitar instanciar esta clase auxiliar
    private Utilidades() {
    }

    // Método estático genérico para buscar elementos dentro de una lista
    public static <T> List<T> buscar(List<T> lista, String texto) {

        // Crea una lista para almacenar los resultados encontrados
        List<T> resultados = new ArrayList<>();

        // Verifica que la lista y el texto no sean nulos
        if (lista == null || texto == null) {
            return resultados;
        }

        // Convierte el texto de búsqueda a minúsculas
        String busqueda = texto.trim().toLowerCase();

        // Recorre todos los elementos de la lista
        for (T elemento : lista) {

            // Comprueba que el elemento no sea nulo
            if (elemento != null) {

                // Obtiene la representación textual del elemento
                String contenido = elemento.toString().toLowerCase();

                // Comprueba si contiene el texto buscado
                if (contenido.contains(busqueda)) {
                    resultados.add(elemento);
                }
            }
        }

        // Retorna la lista con los resultados encontrados
        return resultados;
    }
}
