
package entidades.Caceres.Moreira;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase MenuBibliotecario.
 * Gestiona las opciones disponibles para el bibliotecario
 * mediante la consola.
 */
public class MenuBibliotecario {

    // Método estático para mostrar el menú del bibliotecario
    public static void mostrar(Scanner sc, SistemaLogin sistemaLogin,
            Biblioteca biblioteca, Bibliotecario bibliotecario) {

        int opcion;

        do {
            System.out.println("\n==================================");
            System.out.println("      MENU BIBLIOTECARIO");
            System.out.println("==================================");
            System.out.println("Bienvenido/a: " + bibliotecario.getUsuario());
            System.out.println("1. Listar libros");
            System.out.println("2. Agregar libro");
            System.out.println("3. Eliminar libro");
            System.out.println("4. Modificar libro");
            System.out.println("5. Actualizar stock");
            System.out.println("6. Buscar libro");
            System.out.println("7. Registrar autor");
            System.out.println("8. Buscar autor");
            System.out.println("9. Listar autores");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero(sc);

            switch (opcion) {
                case 1:
                    biblioteca.listar();
                    break;

                case 2:
                    agregarLibro(sc, biblioteca);
                    break;

                case 3:
                    eliminarLibro(sc, biblioteca);
                    break;

                case 4:
                    modificarLibro(sc, biblioteca);
                    break;

                case 5:
                    actualizarStock(sc, biblioteca);
                    break;

                case 6:
                    buscarLibro(sc, biblioteca);
                    break;

                case 7:
                    registrarAutor(sc, biblioteca);
                    break;

                case 8:
                    buscarAutor(sc, biblioteca);
                    break;

                case 9:
                    listarAutores(biblioteca);
                    break;

                case 0:
                    System.out.println("Cerrando sesión...");
                    sistemaLogin.cerrarSesion();
                    break;

                default:
                    System.out.println("Opción incorrecta.");
            }

        } while (opcion != 0);
    }

    // Registra un nuevo libro
    private static void agregarLibro(Scanner sc, Biblioteca biblioteca) {

        System.out.println("\n========== AGREGAR LIBRO ==========");

        Libro libro = crearLibro(sc, biblioteca);

        if (libro != null) {
            if (biblioteca.agregarLibro(libro)) {
                System.out.println("Libro agregado correctamente.");
            } else {
                System.out.println("El libro ya se encuentra registrado.");
            }
        }
    }

    // Elimina un libro de la biblioteca
    private static void eliminarLibro(Scanner sc, Biblioteca biblioteca) {

        System.out.println("\n========== ELIMINAR LIBRO ==========");

        List<Libro> libros = biblioteca.getLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        mostrarLibros(libros);

        System.out.print("Ingrese el número del libro a eliminar: ");
        int indice = leerEntero(sc) - 1;

        if (indice < 0 || indice >= libros.size()) {
            System.out.println("Número incorrecto.");
            return;
        }

        Libro seleccionado = libros.get(indice);

        if (biblioteca.eliminarLibro(seleccionado)) {
            System.out.println("Libro eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el libro.");
        }
    }

    // Modifica los datos de un libro
    private static void modificarLibro(Scanner sc, Biblioteca biblioteca) {

        System.out.println("\n========== MODIFICAR LIBRO ==========");

        List<Libro> libros = biblioteca.getLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        mostrarLibros(libros);

        System.out.print("Ingrese el número del libro a modificar: ");
        int indice = leerEntero(sc) - 1;

        if (indice < 0 || indice >= libros.size()) {
            System.out.println("Número incorrecto.");
            return;
        }

        System.out.println("\nIngrese los nuevos datos:");

        Libro modificado = crearLibro(sc, biblioteca);

        if (modificado != null) {
            if (biblioteca.modificarLibro(indice, modificado)) {
                System.out.println("Libro modificado correctamente.");
            } else {
                System.out.println("No se pudo modificar el libro.");
            }
        }
    }

    // Actualiza el stock de un libro
    private static void actualizarStock(Scanner sc, Biblioteca biblioteca) {

        System.out.println("\n========== ACTUALIZAR STOCK ==========");

        List<Libro> libros = biblioteca.getLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        mostrarLibros(libros);

        System.out.print("Ingrese el número del libro: ");
        int indice = leerEntero(sc) - 1;

        if (indice < 0 || indice >= libros.size()) {
            System.out.println("Número incorrecto.");
            return;
        }

        Libro seleccionado = libros.get(indice);

        System.out.println("Stock actual: " + seleccionado.getStock());
        System.out.print("Ingrese el nuevo stock: ");

        int nuevoStock = leerEntero(sc);

        if (biblioteca.actualizarStock(seleccionado, nuevoStock)) {
            System.out.println("Stock actualizado correctamente.");
        } else {
            System.out.println("No se pudo actualizar el stock.");
        }
    }

    // Busca libros por sus datos
    private static void buscarLibro(Scanner sc, Biblioteca biblioteca) {

        System.out.println("\n========== BUSCAR LIBRO ==========");

        System.out.print("Ingrese título, editorial o autor: ");
        String texto = sc.nextLine();

        List<Libro> resultados = biblioteca.buscarLibro(texto);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron libros.");
        } else {
            mostrarLibros(resultados);
        }
    }

    // Registra un nuevo autor
    private static void registrarAutor(Scanner sc, Biblioteca biblioteca) {

        System.out.println("\n========== REGISTRAR AUTOR ==========");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Apellido: ");
        String apellido = sc.nextLine();

        System.out.print("Fecha de nacimiento: ");
        String fecha = sc.nextLine();

        System.out.print("Bibliografía: ");
        String bibliografia = sc.nextLine();

        Autor autor = new Autor(nombre, apellido, fecha, bibliografia);

        if (biblioteca.agregarAutor(autor)) {
            System.out.println("Autor registrado correctamente.");
        } else {
            System.out.println("El autor ya está registrado.");
        }
    }

    // Busca autores por nombre o apellido
    private static void buscarAutor(Scanner sc, Biblioteca biblioteca) {

        System.out.println("\n========== BUSCAR AUTOR ==========");

        System.out.print("Ingrese el nombre o apellido: ");
        String nombre = sc.nextLine();

        List<Autor> resultados = biblioteca.buscarAutorPorNombre(nombre);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron autores.");
        } else {
            for (Autor autor : resultados) {
                System.out.println(autor);
            }
        }
    }

    // Muestra todos los autores registrados
    private static void listarAutores(Biblioteca biblioteca) {

        System.out.println("\n========== AUTORES REGISTRADOS ==========");

        if (biblioteca.getAutores().isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        for (Autor autor : biblioteca.getAutores()) {
            System.out.println(autor);
        }
    }

    // Construye un libro según la categoría seleccionada
    private static Libro crearLibro(Scanner sc, Biblioteca biblioteca) {

        System.out.println("\nSeleccione el tipo de libro:");
        System.out.println("1. Literatura");
        System.out.println("2. Científico");
        System.out.println("3. Tecnología");
        System.out.println("4. Arte");
        System.out.print("Opción: ");

        int tipo = leerEntero(sc);

        if (tipo < 1 || tipo > 4) {
            System.out.println("Tipo de libro incorrecto.");
            return null;
        }

        System.out.print("Título: ");
        String titulo = sc.nextLine();

        System.out.print("Editorial: ");
        String editorial = sc.nextLine();

        System.out.print("Edición: ");
        int edicion = leerEntero(sc);

        System.out.print("Fecha de publicación: ");
        String fecha = sc.nextLine();

        Autor autor = seleccionarAutor(sc, biblioteca);

        if (autor == null) {
            System.out.println("Debe seleccionar un autor.");
            return null;
        }

        System.out.print("Precio de venta: ");
        float precioVenta = leerFloat(sc);

        System.out.print("Precio de alquiler: ");
        float precioAlquiler = leerFloat(sc);

        System.out.print("Stock: ");
        int stock = leerEntero(sc);

        if (edicion <= 0 || precioVenta < 0
                || precioAlquiler < 0 || stock < 0) {
            System.out.println("Los valores ingresados no son válidos.");
            return null;
        }

        System.out.println("Formato:");
        System.out.println("1. Físico");
        System.out.println("2. Digital");
        System.out.print("Opción: ");

        int opcionFormato = leerEntero(sc);

        FormatoLibro formato;

        if (opcionFormato == 1) {
            formato = FormatoLibro.FISICO;
        } else if (opcionFormato == 2) {
            formato = FormatoLibro.DIGITAL;
        } else {
            System.out.println("Formato incorrecto.");
            return null;
        }

        switch (tipo) {

            case 1:
                System.out.println("Géneros literarios:");

                GeneroLiterario[] generos = GeneroLiterario.values();

                for (int i = 0; i < generos.length; i++) {
                    System.out.println((i + 1) + ". " + generos[i]);
                }

                System.out.print("Seleccione el género: ");
                int genero = leerEntero(sc);

                if (genero < 1 || genero > generos.length) {
                    return null;
                }

                return new Literatura(titulo, editorial, edicion,
                        fecha, autor, precioVenta, precioAlquiler,
                        stock, formato, generos[genero - 1]);

            case 2:
                System.out.println("Áreas científicas:");

                AreaCientifica[] areas = AreaCientifica.values();

                for (int i = 0; i < areas.length; i++) {
                    System.out.println((i + 1) + ". " + areas[i]);
                }

                System.out.print("Seleccione el área: ");
                int area = leerEntero(sc);

                if (area < 1 || area > areas.length) {
                    return null;
                }

                return new Cientifico(titulo, editorial, edicion,
                        fecha, autor, precioVenta, precioAlquiler,
                        stock, formato, areas[area - 1]);

            case 3:
                System.out.println("Áreas tecnológicas:");

                AreaTecnologia[] tecnologias = AreaTecnologia.values();

                for (int i = 0; i < tecnologias.length; i++) {
                    System.out.println((i + 1) + ". " + tecnologias[i]);
                }

                System.out.print("Seleccione el área: ");
                int tecnologia = leerEntero(sc);

                if (tecnologia < 1 || tecnologia > tecnologias.length) {
                    return null;
                }

                return new Tecnologia(titulo, editorial, edicion,
                        fecha, autor, precioVenta, precioAlquiler,
                        stock, formato, tecnologias[tecnologia - 1]);

            case 4:
                System.out.println("Tipos de arte:");

                TipoArte[] tiposArte = TipoArte.values();

                for (int i = 0; i < tiposArte.length; i++) {
                    System.out.println((i + 1) + ". " + tiposArte[i]);
                }

                System.out.print("Seleccione el tipo de arte: ");
                int arte = leerEntero(sc);

                if (arte < 1 || arte > tiposArte.length) {
                    return null;
                }

                return new Arte(titulo, editorial, edicion,
                        fecha, autor, precioVenta, precioAlquiler,
                        stock, formato, tiposArte[arte - 1]);
        }

        return null;
    }

    // Permite seleccionar un autor registrado
    private static Autor seleccionarAutor(Scanner sc, Biblioteca biblioteca) {

        List<Autor> autores = biblioteca.getAutores();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            System.out.println("Debe registrar un autor antes de crear un libro.");
            return null;
        }

        System.out.println("\nAutores disponibles:");

        for (int i = 0; i < autores.size(); i++) {
            System.out.println((i + 1) + ". " + autores.get(i).getNombre()
                    + " " + autores.get(i).getApellido());
        }

        System.out.print("Seleccione un autor: ");
        int opcion = leerEntero(sc);

        if (opcion < 1 || opcion > autores.size()) {
            System.out.println("Selección incorrecta.");
            return null;
        }

        return autores.get(opcion - 1);
    }

    // Muestra una lista de libros numerados
    private static void mostrarLibros(List<Libro> libros) {

        for (int i = 0; i < libros.size(); i++) {
            System.out.println("\nLibro número: " + (i + 1));
            System.out.println(libros.get(i).mostrar());
            System.out.println("--------------------------------");
        }
    }

    // Lee un número entero desde el teclado
    private static int leerEntero(Scanner sc) {

        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número entero válido: ");
            }
        }
    }

    // Lee un número decimal desde el teclado
    private static float leerFloat(Scanner sc) {

        while (true) {
            try {
                return Float.parseFloat(
                        sc.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
}
