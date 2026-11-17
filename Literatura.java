
package entidades.Caceres.Moreira;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase Literatura.
 * Representa un libro de contenido literario.
 * Hereda de la clase abstracta Libro.
 */
public class Literatura extends Libro {

    // Atributo privado para almacenar el género literario
    private GeneroLiterario genero;

    // Constructor de la clase Literatura
    public Literatura(String titulo, String editorial, int edicion,
            String fechaPublicacion, Autor autor, float precioVenta,
            float precioAlquiler, int stock, FormatoLibro formato,
            GeneroLiterario genero) {

        // Inicializa los atributos heredados de Libro
        super(titulo, editorial, edicion, fechaPublicacion,
                autor, precioVenta, precioAlquiler, stock, formato);

        // Inicializa el atributo propio de Literatura
        this.genero = genero;
    }

    // Getter para obtener el género literario
    public GeneroLiterario getGenero() {
        return this.genero;
    }

    // Setter para modificar el género literario
    public void setGenero(GeneroLiterario genero) {
        this.genero = genero;
    }

    // Sobrescribe toString para mostrar los datos del libro literario
    @Override
    public String toString() {
        return super.toString()
                + " | Género literario: " + genero;
    }
}
