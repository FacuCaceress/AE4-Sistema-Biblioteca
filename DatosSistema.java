
package entidades.Caceres.Moreira;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase DatosSistema.
 * Agrupa los datos principales del sistema de biblioteca
 * para permitir su almacenamiento y recuperación.
 */
public class DatosSistema {

    // Referencia al sistema de inicio de sesión
    private SistemaLogin sistemaLogin;

    // Referencia a la biblioteca
    private Biblioteca biblioteca;

    // Constructor
    public DatosSistema(SistemaLogin sistemaLogin, Biblioteca biblioteca) {
        this.sistemaLogin = sistemaLogin;
        this.biblioteca = biblioteca;
    }

    // Obtiene el sistema de inicio de sesión
    public SistemaLogin getSistemaLogin() {
        return this.sistemaLogin;
    }

    // Obtiene la biblioteca
    public Biblioteca getBiblioteca() {
        return this.biblioteca;
    }
}
