
package entidades.Caceres.Moreira;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase Autor.
 * Representa a los autores de los libros registrados
 * en el sistema de biblioteca.
 * Hereda de la clase abstracta Persona.
 */
public class Autor extends Persona {

    // Atributo privado para almacenar la bibliografía del autor
    private String bibliografia;

    // Constructor de la clase Autor
    public Autor(String nombre, String apellido, String fechaNacimiento,
            String bibliografia) {

        // Inicializa los atributos heredados de Persona
        super(nombre, apellido, fechaNacimiento);

        // Inicializa el atributo propio de Autor
        this.bibliografia = bibliografia;
    }

    // Getter para obtener la bibliografía
    public String getBibliografia() {
        return this.bibliografia;
    }

    // Setter para modificar la bibliografía
    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    // Sobrescribe toString para mostrar los datos del autor
    @Override
    public String toString() {
        return "Autor: " + getNombre() + " " + getApellido()
                + " | Fecha de nacimiento: " + getFechaNacimiento()
                + " | Bibliografía: " + bibliografia;
    }
}
