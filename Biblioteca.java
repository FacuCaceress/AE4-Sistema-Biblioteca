
package entidades.Caceres.Moreira;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase Biblioteca.
 * Administra los libros y autores registrados en el sistema.
 * Permite realizar operaciones de alta, baja, modificación,
 * búsqueda, alquiler y venta de libros.
 */
public class Biblioteca implements Listable {

    // Listas para almacenar los libros y autores
    private List<Libro> libros;
    private List<Autor> autores;

    // Constructor
    public Biblioteca() {
        this.libros = new ArrayList<>();
        this.autores = new ArrayList<>();
    }

    // Agrega un libro a la biblioteca
    public boolean agregarLibro(Libro libro) {

        if (libro == null) {
            return false;
        }

        // Comprueba si ya existe un libro con el mismo título,
        // edición y autor
        for (Libro existente : libros) {

            if (existente.getTitulo() != null
                    && libro.getTitulo() != null
                    && existente.getTitulo().equalsIgnoreCase(libro.getTitulo())
                    && existente.getEdicion() == libro.getEdicion()
                    && mismoAutor(existente.getAutor(), libro.getAutor())) {

                return false;
            }
        }

        return libros.add(libro);
    }

    // Comprueba si dos libros tienen el mismo autor
    private boolean mismoAutor(Autor autor1, Autor autor2) {

        if (autor1 == null || autor2 == null) {
            return autor1 == autor2;
        }

        return autor1.getNombre() != null
                && autor2.getNombre() != null
                && autor1.getApellido() != null
                && autor2.getApellido() != null
                && autor1.getNombre().equalsIgnoreCase(autor2.getNombre())
                && autor1.getApellido().equalsIgnoreCase(autor2.getApellido());
    }

    // Elimina un libro de la biblioteca
    public boolean eliminarLibro(Libro libro) {

        if (libro == null) {
            return false;
        }

        return libros.remove(libro);
    }

    // Modifica un libro según su posición en la lista
    public boolean modificarLibro(int indice, Libro libroModificado) {

        if (libroModificado == null
                || indice < 0
                || indice >= libros.size()) {
            return false;
        }

        // Evita reemplazar un libro por otro que ya esté registrado
        for (int i = 0; i < libros.size(); i++) {

            if (i != indice) {
                Libro existente = libros.get(i);

                if (existente.getTitulo() != null
                        && libroModificado.getTitulo() != null
                        && existente.getTitulo().equalsIgnoreCase(
                                libroModificado.getTitulo())
                        && existente.getEdicion() == libroModificado.getEdicion()
                        && mismoAutor(existente.getAutor(),
                                libroModificado.getAutor())) {

                    return false;
                }
            }
        }

        libros.set(indice, libroModificado);
        return true;
    }

    // Actualiza el stock de un libro
    public boolean actualizarStock(Libro libro, int nuevoStock) {

        if (libro == null || nuevoStock < 0 || !libros.contains(libro)) {
            return false;
        }

        libro.setStock(nuevoStock);
        return true;
    }

    // Busca libros utilizando la clase Utilidades
    public List<Libro> buscarLibro(String texto) {
        return Utilidades.buscar(libros, texto);
    }

    // Agrega un autor a la biblioteca
    public boolean agregarAutor(Autor autor) {

        if (autor == null) {
            return false;
        }

        for (Autor existente : autores) {

            if (existente.getNombre() != null
                    && autor.getNombre() != null
                    && existente.getApellido() != null
                    && autor.getApellido() != null
                    && existente.getNombre().equalsIgnoreCase(autor.getNombre())
                    && existente.getApellido().equalsIgnoreCase(autor.getApellido())) {

                return false;
            }
        }

        return autores.add(autor);
    }

    // Busca autores por nombre o apellido
    public List<Autor> buscarAutorPorNombre(String nombre) {

        List<Autor> resultados = new ArrayList<>();

        if (nombre == null || nombre.trim().isEmpty()) {
            return resultados;
        }

        String busqueda = nombre.trim().toLowerCase();

        for (Autor autor : autores) {

            if (autor != null) {

                String nombreCompleto = autor.getNombre() + " "
                        + autor.getApellido();

                if (nombreCompleto.toLowerCase().contains(busqueda)) {
                    resultados.add(autor);
                }
            }
        }

        return resultados;
    }

    // Alquila un libro a un cliente
    public boolean alquilarLibro(Cliente cliente, Libro libro) {

        if (cliente == null || libro == null || !libros.contains(libro)) {
            return false;
        }

        if (libro.getStock() <= 0) {
            return false;
        }

        libro.setStock(libro.getStock() - 1);
        cliente.agregarLibroAlquilado(libro);

        return true;
    }

    // Vende un libro a un cliente
    public boolean venderLibro(Cliente cliente, Libro libro) {

        if (cliente == null || libro == null || !libros.contains(libro)) {
            return false;
        }

        if (libro.getStock() <= 0) {
            return false;
        }

        libro.setStock(libro.getStock() - 1);
        cliente.agregarLibroComprado(libro);

        return true;
    }

    // Implementa el método listar de la interfaz Listable
    @Override
    public void listar() {

        System.out.println("\n========== CATÁLOGO DE LIBROS ==========");

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        for (int i = 0; i < libros.size(); i++) {

            Libro libro = libros.get(i);

            System.out.println("\nLibro número: " + (i + 1));
            System.out.println(libro.mostrar());
            System.out.println("----------------------------------------");
        }
    }

    // Obtiene la lista de libros
    public List<Libro> getLibros() {
        return this.libros;
    }

    // Obtiene la lista de autores
    public List<Autor> getAutores() {
        return this.autores;
    }

    // Reemplaza la lista de libros al recuperar los datos del TXT
    public void setLibros(List<Libro> libros) {

        if (libros != null) {
            this.libros = libros;
        }
    }

    // Reemplaza la lista de autores al recuperar los datos del TXT
    public void setAutores(List<Autor> autores) {

        if (autores != null) {
            this.autores = autores;
        }
    }
}
