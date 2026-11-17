
package entidades.Caceres.Moreira;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase Cliente.
 * Representa a un usuario que puede alquilar y comprar libros
 * en el sistema de biblioteca.
 * Hereda de la clase Usuario.
 */
public class Cliente extends Usuario {

    // Atributo privado para almacenar el número de socio
    private int numeroSocio;

    // Lista de libros alquilados por el cliente
    private List<Libro> librosAlquilados;

    // Lista de libros comprados por el cliente
    private List<Libro> librosComprados;

    // Constructor de la clase Cliente
    public Cliente(String nombre, String apellido, String fechaNacimiento,
            String usuario, String mail, String contrasena, int numeroSocio) {

        super(nombre, apellido, fechaNacimiento, usuario, mail, contrasena);

        this.numeroSocio = numeroSocio;
        this.librosAlquilados = new ArrayList<>();
        this.librosComprados = new ArrayList<>();
    }

    // Getter para obtener el número de socio
    public int getNumeroSocio() {
        return this.numeroSocio;
    }

    // Setter para modificar el número de socio
    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    // Getter para obtener los libros alquilados
    public List<Libro> getLibrosAlquilados() {
        return this.librosAlquilados;
    }

    // Getter para obtener los libros comprados
    public List<Libro> getLibrosComprados() {
        return this.librosComprados;
    }

    // Agrega un libro a la lista de libros alquilados
    public void agregarLibroAlquilado(Libro libro) {
        if (libro != null) {
            this.librosAlquilados.add(libro);
        }
    }

    // Agrega un libro a la lista de libros comprados
    public void agregarLibroComprado(Libro libro) {
        if (libro != null) {
            this.librosComprados.add(libro);
        }
    }

    // Remueve un libro de la lista de alquilados cuando se devuelve
    public void devolverLibroAlquilado(Libro libro) {
        if (libro != null) {
            this.librosAlquilados.remove(libro);
        }
    }

    // Reemplaza los libros alquilados al recuperar los datos del TXT
    public void setLibrosAlquilados(List<Libro> librosAlquilados) {
        if (librosAlquilados != null) {
            this.librosAlquilados = librosAlquilados;
        }
    }

    // Reemplaza los libros comprados al recuperar los datos del TXT
    public void setLibrosComprados(List<Libro> librosComprados) {
        if (librosComprados != null) {
            this.librosComprados = librosComprados;
        }
    }

    // Sobrescribe toString para mostrar los datos del cliente
    @Override
    public String toString() {
        return super.toString()
                + " | Número de socio: " + numeroSocio
                + " | Libros alquilados: " + librosAlquilados.size()
                + " | Libros comprados: " + librosComprados.size();
    }
}
