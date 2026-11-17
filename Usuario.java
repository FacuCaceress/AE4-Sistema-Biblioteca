
package entidades.Caceres.Moreira;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase Usuario.
 * Representa a los usuarios que pueden acceder al sistema
 * de biblioteca mediante sus credenciales.
 * Hereda de la clase Persona.
 */
public class Usuario extends Persona {

    // Atributos privados
    private String id;
    private String usuario;
    private String mail;
    private String contrasena;

    // Constructor de la clase Usuario
    public Usuario(String nombre, String apellido, String fechaNacimiento,
            String usuario, String mail, String contrasena) {

        super(nombre, apellido, fechaNacimiento);

        this.usuario = usuario;
        this.mail = mail;
        this.contrasena = contrasena;
        this.id = generarId();
    }

    // Genera un identificador para el usuario
    private String generarId() {
        return java.util.UUID.randomUUID().toString();
    }

    // Getter para obtener el identificador
    public String getId() {
        return this.id;
    }

    // Permite recuperar el identificador original desde el TXT
    public void setId(String id) {
        if (id != null && !id.trim().isEmpty()) {
            this.id = id;
        }
    }

    // Getter para obtener el nombre de usuario
    public String getUsuario() {
        return this.usuario;
    }

    // Setter para modificar el nombre de usuario
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // Getter para obtener el correo electrónico
    public String getMail() {
        return this.mail;
    }

    // Setter para modificar el correo electrónico
    public void setMail(String mail) {
        this.mail = mail;
    }

    // Getter para obtener la contraseña
    public String getContrasena() {
        return this.contrasena;
    }

    // Setter para modificar la contraseña
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    // Sobrescribe toString para mostrar los datos del usuario
    @Override
    public String toString() {
        return "ID: " + id
                + " | Nombre: " + getNombre() + " " + getApellido()
                + " | Usuario: " + usuario
                + " | Mail: " + mail;
    }
}
