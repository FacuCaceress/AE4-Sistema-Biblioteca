
package entidades.Caceres.Moreira;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase abstracta Persona.
 * Representa los datos personales comunes de los usuarios y autores
 * del sistema de biblioteca.
 */
public abstract class Persona {

    // Atributos privados
    private String nombre;
    private String apellido;
    private String fechaNacimiento;

    // Constructor de la clase Persona
    public Persona(String nombre, String apellido, String fechaNacimiento) {

        // Inicializa los atributos personales
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getter para obtener el nombre
    public String getNombre() {
        return this.nombre;
    }

    // Setter para modificar el nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter para obtener el apellido
    public String getApellido() {
        return this.apellido;
    }

    // Setter para modificar el apellido
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    // Getter para obtener la fecha de nacimiento
    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    // Setter para modificar la fecha de nacimiento
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
