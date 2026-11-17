
package entidades.Caceres.Moreira;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase abstracta Libro.
 * Representa las características generales de los libros
 * registrados en el sistema de biblioteca.
 * Implementa la interfaz Mostrable.
 */
public abstract class Libro implements Mostrable {

    // Atributos privados
    private String titulo;
    private String editorial;
    private int edicion;
    private String fechaPublicacion;
    private Autor autor;
    private float precioVenta;
    private float precioAlquiler;
    private int stock;
    private FormatoLibro formato;

    // Constructor de la clase abstracta Libro
    public Libro(String titulo, String editorial, int edicion,
            String fechaPublicacion, Autor autor, float precioVenta,
            float precioAlquiler, int stock, FormatoLibro formato) {

        this.titulo = titulo;
        this.editorial = editorial;
        this.edicion = edicion;
        this.fechaPublicacion = fechaPublicacion;
        this.autor = autor;
        this.precioVenta = precioVenta;
        this.precioAlquiler = precioAlquiler;
        this.stock = stock;
        this.formato = formato;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditorial() {
        return this.editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getEdicion() {
        return this.edicion;
    }

    public void setEdicion(int edicion) {
        this.edicion = edicion;
    }

    public String getFechaPublicacion() {
        return this.fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Autor getAutor() {
        return this.autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public float getPrecioVenta() {
        return this.precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getPrecioAlquiler() {
        return this.precioAlquiler;
    }

    public void setPrecioAlquiler(float precioAlquiler) {
        this.precioAlquiler = precioAlquiler;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public FormatoLibro getFormato() {
        return this.formato;
    }

    public void setFormato(FormatoLibro formato) {
        this.formato = formato;
    }

    // Devuelve el tipo concreto de libro
    public String getTipoLibro() {
        return getClass().getSimpleName();
    }

    // Sobrescribe toString para mostrar un resumen del libro
    @Override
    public String toString() {

        String autorNombre = (autor == null)
                ? "Sin autor"
                : autor.getNombre() + " " + autor.getApellido();

        return titulo
                + " | Autor: " + autorNombre
                + " | Formato: " + formato
                + " | Stock: " + stock;
    }

    // Implementa el método mostrar de la interfaz Mostrable
    @Override
    public String mostrar() {

        String autorNombre = (autor == null)
                ? "Sin autor"
                : autor.getNombre() + " " + autor.getApellido();

        StringBuilder sb = new StringBuilder();

        sb.append("Título: ").append(titulo).append("\n")
          .append("Editorial: ").append(editorial).append("\n")
          .append("Edición: ").append(edicion).append("\n")
          .append("Fecha de publicación: ").append(fechaPublicacion).append("\n")
          .append("Autor: ").append(autorNombre).append("\n")
          .append("Precio de venta: ").append(precioVenta).append("\n")
          .append("Precio de alquiler: ").append(precioAlquiler).append("\n")
          .append("Stock: ").append(stock).append("\n")
          .append("Formato: ").append(formato).append("\n")
          .append("Tipo: ").append(getTipoLibro());

        return sb.toString();
    }
}
