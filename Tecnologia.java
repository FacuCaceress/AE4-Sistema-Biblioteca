
package entidades.Caceres.Moreira;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase Tecnologia.
 * Representa un libro de contenido tecnológico.
 * Hereda de la clase abstracta Libro.
 */
public class Tecnologia extends Libro {

    // Atributo privado para almacenar el área tecnológica
    private AreaTecnologia areaTecnologia;

    // Constructor de la clase Tecnologia
    public Tecnologia(String titulo, String editorial, int edicion,
            String fechaPublicacion, Autor autor, float precioVenta,
            float precioAlquiler, int stock, FormatoLibro formato,
            AreaTecnologia areaTecnologia) {

        // Inicializa los atributos heredados de Libro
        super(titulo, editorial, edicion, fechaPublicacion,
                autor, precioVenta, precioAlquiler, stock, formato);

        // Inicializa el atributo propio de Tecnologia
        this.areaTecnologia = areaTecnologia;
    }

    // Getter para obtener el área tecnológica
    public AreaTecnologia getAreaTecnologia() {
        return this.areaTecnologia;
    }

    // Setter para modificar el área tecnológica
    public void setAreaTecnologia(AreaTecnologia areaTecnologia) {
        this.areaTecnologia = areaTecnologia;
    }

    // Sobrescribe toString para mostrar los datos del libro tecnológico
    @Override
    public String toString() {
        return super.toString()
                + " | Área tecnológica: " + areaTecnologia;
    }
}
