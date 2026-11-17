
package entidades.Caceres.Moreira;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase SistemaLogin.
 * Administra los usuarios registrados en el sistema.
 * Permite registrar clientes y bibliotecarios, iniciar sesión,
 * cerrar sesión y recuperar credenciales.
 */
public class SistemaLogin {

    // Lista de usuarios registrados
    private List<Usuario> usuarios;

    // Usuario que inició sesión
    private transient Usuario usuarioActual;

    // Constructor
    public SistemaLogin() {
        this.usuarios = new ArrayList<>();
        this.usuarioActual = null;
    }

    // Registra un nuevo cliente
    public boolean registrarCliente(Cliente cliente) {

        if (cliente == null) {
            return false;
        }

        if (existeUsuario(cliente.getUsuario(), cliente.getMail())) {
            return false;
        }

        return usuarios.add(cliente);
    }

    // Registra un nuevo bibliotecario
    public boolean registrarBibliotecario(Bibliotecario bibliotecario) {

        if (bibliotecario == null) {
            return false;
        }

        if (existeUsuario(bibliotecario.getUsuario(),
                bibliotecario.getMail())) {
            return false;
        }

        return usuarios.add(bibliotecario);
    }

    // Comprueba si ya existe un usuario o correo registrado
    private boolean existeUsuario(String usuario, String mail) {

        for (Usuario registrado : usuarios) {

            if (registrado.getUsuario() != null
                    && usuario != null
                    && registrado.getUsuario().equalsIgnoreCase(usuario)) {
                return true;
            }

            if (registrado.getMail() != null
                    && mail != null
                    && registrado.getMail().equalsIgnoreCase(mail)) {
                return true;
            }
        }

        return false;
    }

    // Permite iniciar sesión
    public boolean iniciarSesion(String usuario, String contrasena) {

        if (usuario == null || contrasena == null) {
            return false;
        }

        for (Usuario registrado : usuarios) {

            if (registrado.getUsuario() != null
                    && registrado.getContrasena() != null
                    && registrado.getUsuario().equalsIgnoreCase(usuario)
                    && registrado.getContrasena().equals(contrasena)) {

                this.usuarioActual = registrado;
                return true;
            }
        }

        this.usuarioActual = null;
        return false;
    }

    // Cierra la sesión actual
    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    // Obtiene el usuario que inició sesión
    public Usuario getUsuarioActual() {
        return this.usuarioActual;
    }

    // Recupera el nombre de usuario mediante el correo
    public String recuperarUsuario(String mail) {

        if (mail == null) {
            return null;
        }

        for (Usuario registrado : usuarios) {

            if (registrado.getMail() != null
                    && registrado.getMail().equalsIgnoreCase(mail)) {

                return registrado.getUsuario();
            }
        }

        return null;
    }

    // Recupera la contraseña mediante el correo
    public String recuperarContrasena(String mail) {

        if (mail == null) {
            return null;
        }

        for (Usuario registrado : usuarios) {

            if (registrado.getMail() != null
                    && registrado.getMail().equalsIgnoreCase(mail)) {

                return registrado.getContrasena();
            }
        }

        return null;
    }

    // Obtiene la lista de usuarios registrados
    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }

    // Reemplaza la lista de usuarios al recuperar los datos del TXT
    public void setUsuarios(List<Usuario> usuarios) {

        if (usuarios != null) {
            this.usuarios = usuarios;
        }
    }
}
