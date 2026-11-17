
package entidades.Caceres.Moreira;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase Cientifico.
 * Representa un libro de contenido científico.
 * Hereda de la clase abstracta Libro.
 */
public class Cientifico extends Libro {

    // Atributo privado para almacenar el área científica
    private AreaCientifica areaCientifica;

    // Constructor de la clase Cientifico
    public Cientifico(String titulo, String editorial, int edicion,
            String fechaPublicacion, Autor autor, float precioVenta,
            float precioAlquiler, int stock, FormatoLibro formato,
            AreaCientifica areaCientifica) {

        // Inicializa los atributos heredados de Libro
        super(titulo, editorial, edicion, fechaPublicacion,
                autor, precioVenta, precioAlquiler, stock, formato);

        // Inicializa el atributo propio de Cientifico
        this.areaCientifica = areaCientifica;
    }

    // Getter para obtener el área científica
    public AreaCientifica getAreaCientifica() {
        return this.areaCientifica;
    }

    // Setter para modificar el área científica
    public void setAreaCientifica(AreaCientifica areaCientifica) {
        this.areaCientifica = areaCientifica;
    }

    // Sobrescribe toString para mostrar los datos del libro científico
    @Override
    public String toString() {
        return super.toString()
                + " | Área científica: " + areaCientifica;
    }
}
