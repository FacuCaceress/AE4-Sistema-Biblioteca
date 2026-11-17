
package entidades.Caceres.Moreira;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase Bibliotecario.
 * Representa al usuario encargado de administrar
 * los libros y autores del sistema de biblioteca.
 * Hereda de la clase Usuario.
 */
public class Bibliotecario extends Usuario {

    // Atributo privado para almacenar el número de legajo
    private int legajo;

    // Constructor de la clase Bibliotecario
    public Bibliotecario(String nombre, String apellido, String fechaNacimiento,
            String usuario, String mail, String contrasena, int legajo) {

        // Inicializa los atributos heredados de Usuario
        super(nombre, apellido, fechaNacimiento, usuario, mail, contrasena);

        // Inicializa el atributo propio de Bibliotecario
        this.legajo = legajo;
    }

    // Getter para obtener el número de legajo
    public int getLegajo() {
        return this.legajo;
    }

    // Setter para modificar el número de legajo
    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    // Sobrescribe toString para mostrar los datos del bibliotecario
    @Override
    public String toString() {
        return super.toString()
                + " | Legajo: " + legajo;
    }
}
