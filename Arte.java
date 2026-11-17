
package entidades.Caceres.Moreira;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase Arte.
 * Representa un libro relacionado con el arte.
 * Hereda de la clase abstracta Libro.
 */
public class Arte extends Libro {

    // Atributo privado para almacenar el tipo de arte
    private TipoArte tipoArte;

    // Constructor de la clase Arte
    public Arte(String titulo, String editorial, int edicion,
            String fechaPublicacion, Autor autor, float precioVenta,
            float precioAlquiler, int stock, FormatoLibro formato,
            TipoArte tipoArte) {

        // Inicializa los atributos heredados de Libro
        super(titulo, editorial, edicion, fechaPublicacion,
                autor, precioVenta, precioAlquiler, stock, formato);

        // Inicializa el atributo propio de Arte
        this.tipoArte = tipoArte;
    }

    // Getter para obtener el tipo de arte
    public TipoArte getTipoArte() {
        return this.tipoArte;
    }

    // Setter para modificar el tipo de arte
    public void setTipoArte(TipoArte tipoArte) {
        this.tipoArte = tipoArte;
    }

    // Sobrescribe toString para mostrar los datos del libro de arte
    @Override
    public String toString() {
        return super.toString()
                + " | Tipo de arte: " + tipoArte;
    }
}
