
package entidades.Caceres.Moreira;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase MenuCliente.
 * Gestiona las opciones disponibles para el cliente
 * mediante la consola.
 */
public class MenuCliente {

    // Muestra el menú principal del cliente
    public static void mostrar(Scanner sc, SistemaLogin sistemaLogin,
            Biblioteca biblioteca, Cliente cliente) {

        int opcion;

        do {
            System.out.println("\n==================================");
            System.out.println("          MENU CLIENTE");
            System.out.println("==================================");
            System.out.println("Bienvenido/a: " + cliente.getUsuario());
            System.out.println("1. Listar libros");
            System.out.println("2. Buscar libro por título");
            System.out.println("3. Alquilar libro");
            System.out.println("4. Comprar libro");
            System.out.println("5. Ver libros alquilados");
            System.out.println("6. Ver libros comprados");
            System.out.println("7. Devolver libro alquilado");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero(sc);

            switch (opcion) {

                case 1:
                    biblioteca.listar();
                    break;

                case 2:
                    buscarLibroGeneral(sc, biblioteca);
                    break;

                case 3:
                    alquilarLibro(sc, biblioteca, cliente);
                    break;

                case 4:
                    comprarLibro(sc, biblioteca, cliente);
                    break;

                case 5:
                    verLibrosAlquilados(cliente);
                    break;

                case 6:
                    verLibrosComprados(cliente);
                    break;

                case 7:
                    devolverLibro(sc, cliente);
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

    // Busca libros por título, autor, editorial u otros datos
    private static void buscarLibroGeneral(Scanner sc,
            Biblioteca biblioteca) {

        System.out.println("\n========== BUSCAR LIBRO ==========");
        System.out.print("Ingrese el texto que desea buscar: ");

        String texto = sc.nextLine();

        List<Libro> encontrados = biblioteca.buscarLibro(texto);

        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron coincidencias.");
        } else {
            System.out.println("\nResultados encontrados:");
            mostrarLibros(encontrados);
        }
    }

    // Gestiona el alquiler de un libro
    private static void alquilarLibro(Scanner sc,
            Biblioteca biblioteca, Cliente cliente) {

        System.out.println("\n========== ALQUILAR LIBRO ==========");

        List<Libro> libros = biblioteca.getLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        mostrarLibros(libros);

        System.out.print("Ingrese el número del libro que desea alquilar: ");
        int indice = leerEntero(sc) - 1;

        if (indice < 0 || indice >= libros.size()) {
            System.out.println("Número de libro incorrecto.");
            return;
        }

        Libro seleccionado = libros.get(indice);

        if (seleccionado.getStock() <= 0) {
            System.out.println("No hay stock disponible para este libro.");
            return;
        }

        System.out.println("\nLibro seleccionado: "
                + seleccionado.getTitulo());
        System.out.println("Precio de alquiler: $"
                + seleccionado.getPrecioAlquiler());

        System.out.println("\n¿Desea confirmar el alquiler?");
        System.out.println("1. Confirmar");
        System.out.println("2. Cancelar");
        System.out.print("Opción: ");

        int confirmacion = leerEntero(sc);

        if (confirmacion != 1) {
            System.out.println("Alquiler cancelado.");
            return;
        }

        boolean resultado = biblioteca.alquilarLibro(cliente, seleccionado);

        if (resultado) {
            System.out.println("Libro alquilado correctamente.");
            System.out.println("Stock restante: "
                    + seleccionado.getStock());
        } else {
            System.out.println("No se pudo completar el alquiler.");
        }
    }

    // Gestiona la compra de un libro
    private static void comprarLibro(Scanner sc,
            Biblioteca biblioteca, Cliente cliente) {

        System.out.println("\n========== COMPRAR LIBRO ==========");

        List<Libro> libros = biblioteca.getLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        mostrarLibros(libros);

        System.out.print("Ingrese el número del libro que desea comprar: ");
        int indice = leerEntero(sc) - 1;

        if (indice < 0 || indice >= libros.size()) {
            System.out.println("Número de libro incorrecto.");
            return;
        }

        Libro seleccionado = libros.get(indice);

        if (seleccionado.getStock() <= 0) {
            System.out.println("No hay stock disponible para este libro.");
            return;
        }

        System.out.println("\nLibro seleccionado: "
                + seleccionado.getTitulo());
        System.out.println("Precio de venta: $"
                + seleccionado.getPrecioVenta());
        System.out.println("Total a pagar: $"
                + seleccionado.getPrecioVenta());

        System.out.println("\n¿Desea confirmar la compra?");
        System.out.println("1. Confirmar");
        System.out.println("2. Cancelar");
        System.out.print("Opción: ");

        int confirmacion = leerEntero(sc);

        if (confirmacion != 1) {
            System.out.println("Compra cancelada.");
            return;
        }

        boolean resultado = biblioteca.venderLibro(cliente, seleccionado);

        if (resultado) {
            System.out.println("Compra realizada correctamente.");
            System.out.println("Stock restante: "
                    + seleccionado.getStock());
        } else {
            System.out.println("No se pudo completar la compra.");
        }
    }

    // Muestra los libros que actualmente tiene alquilados el cliente
    private static void verLibrosAlquilados(Cliente cliente) {

        System.out.println("\n========== LIBROS ALQUILADOS ==========");

        List<Libro> lista = cliente.getLibrosAlquilados();

        if (lista.isEmpty()) {
            System.out.println("No tiene libros alquilados.");
            return;
        }

        mostrarLibros(lista);
    }

    // Muestra los libros comprados por el cliente
    private static void verLibrosComprados(Cliente cliente) {

        System.out.println("\n========== LIBROS COMPRADOS ==========");

        List<Libro> lista = cliente.getLibrosComprados();

        if (lista.isEmpty()) {
            System.out.println("No tiene libros comprados.");
            return;
        }

        mostrarLibros(lista);
    }

    // Gestiona la devolución de un libro alquilado
    private static void devolverLibro(Scanner sc, Cliente cliente) {

        System.out.println("\n========== DEVOLVER LIBRO ==========");

        List<Libro> alquilados = cliente.getLibrosAlquilados();

        if (alquilados.isEmpty()) {
            System.out.println("No hay libros alquilados para devolver.");
            return;
        }

        mostrarLibros(alquilados);

        System.out.print("Ingrese el número del libro que desea devolver: ");
        int indice = leerEntero(sc) - 1;

        if (indice < 0 || indice >= alquilados.size()) {
            System.out.println("Número de libro incorrecto.");
            return;
        }

        Libro libro = alquilados.get(indice);

        // Incrementa el stock al recibir la devolución
        libro.setStock(libro.getStock() + 1);

        // Elimina el libro de la lista de alquileres del cliente
        cliente.devolverLibroAlquilado(libro);

        System.out.println("Libro devuelto correctamente.");
        System.out.println("Stock actualizado: " + libro.getStock());
    }

    // Muestra una lista de libros numerados
    private static void mostrarLibros(List<Libro> libros) {

        for (int i = 0; i < libros.size(); i++) {

            System.out.println("\nLibro número: " + (i + 1));
            System.out.println(libros.get(i).mostrar());
            System.out.println("----------------------------------");
        }
    }

    // Lee y valida un número entero
    private static int leerEntero(Scanner sc) {

        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número entero válido: ");
            }
        }
    }
}
