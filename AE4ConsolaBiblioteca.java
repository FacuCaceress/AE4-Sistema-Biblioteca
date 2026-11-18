
package ae4.consolabiblioteca;

import entidades.Caceres.Moreira.*;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase AE4ConsolaBiblioteca.
 * Contiene el punto de entrada del sistema y administra
 * los diferentes menús mediante consola.
 */
public class AE4ConsolaBiblioteca {

    // Scanner para leer los datos ingresados por teclado
    private static Scanner teclado = new Scanner(System.in);

    // Objetos principales del sistema
    private static SistemaLogin sistemaLogin;
    private static Biblioteca biblioteca;

    // Método principal del programa
    public static void main(String[] args) {

        // Recupera los datos guardados anteriormente
        DatosSistema datos = Persistencia.cargar();

        if (datos != null) {

            sistemaLogin = datos.getSistemaLogin();
            biblioteca = datos.getBiblioteca();

        } else {

            // Crea un sistema nuevo si no existen datos
            sistemaLogin = new SistemaLogin();
            biblioteca = new Biblioteca();
        }

        int opcion;

        do {
            System.out.println("\n====================================");
            System.out.println("       SISTEMA DE BIBLIOTECA");
            System.out.println("====================================");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Registrar bibliotecario");
            System.out.println("4. Recuperar nombre de usuario");
            System.out.println("5. Recuperar contraseña");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;

                case 2:
                    registrarCliente();
                    break;

                case 3:
                    registrarBibliotecario();
                    break;

                case 4:
                    recuperarUsuario();
                    break;

                case 5:
                    recuperarContrasena();
                    break;

                case 0:
                    System.out.println("\nGuardando información...");

                    if (Persistencia.guardar(sistemaLogin, biblioteca)) {
                        System.out.println("Información guardada correctamente.");
                    } else {
                        System.out.println("No se pudo guardar la información.");
                    }

                    System.out.println("Gracias por utilizar el sistema.");
                    break;

                default:
                    System.out.println("Opción incorrecta.");
            }

        } while (opcion != 0);

        teclado.close();
    }

    // Permite iniciar sesión en el sistema
    private static void iniciarSesion() {

        System.out.println("\n========== INICIO DE SESIÓN ==========");

        System.out.print("Nombre de usuario: ");
        String usuario = teclado.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = teclado.nextLine();

        if (sistemaLogin.iniciarSesion(usuario, contrasena)) {

            Usuario usuarioActual = sistemaLogin.getUsuarioActual();

            System.out.println("\nBienvenido/a, "
                    + usuarioActual.getNombre() + " "
                    + usuarioActual.getApellido());

            // Comprueba el tipo de usuario que inició sesión
            if (usuarioActual instanceof Cliente) {

                menuCliente((Cliente) usuarioActual);

            } else if (usuarioActual instanceof Bibliotecario) {

                menuBibliotecario((Bibliotecario) usuarioActual);
            }

            sistemaLogin.cerrarSesion();

        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    // Registra un nuevo cliente
    private static void registrarCliente() {

        System.out.println("\n========== REGISTRO DE CLIENTE ==========");

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Apellido: ");
        String apellido = teclado.nextLine();

        System.out.print("Fecha de nacimiento: ");
        String fecha = teclado.nextLine();

        System.out.print("Nombre de usuario: ");
        String usuario = teclado.nextLine();

        System.out.print("Correo electrónico: ");
        String mail = teclado.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = teclado.nextLine();

        System.out.print("Número de socio: ");
        int numeroSocio = leerEntero();

        Cliente cliente = new Cliente(nombre, apellido, fecha,
                usuario, mail, contrasena, numeroSocio);

        if (sistemaLogin.registrarCliente(cliente)) {
            System.out.println("Cliente registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el cliente.");
            System.out.println("El usuario o correo ya se encuentra registrado.");
        }
    }

    // Registra un nuevo bibliotecario
    private static void registrarBibliotecario() {

        System.out.println("\n========== REGISTRO DE BIBLIOTECARIO ==========");

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Apellido: ");
        String apellido = teclado.nextLine();

        System.out.print("Fecha de nacimiento: ");
        String fecha = teclado.nextLine();

        System.out.print("Nombre de usuario: ");
        String usuario = teclado.nextLine();

        System.out.print("Correo electrónico: ");
        String mail = teclado.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = teclado.nextLine();

        System.out.print("Número de legajo: ");
        int legajo = leerEntero();

        Bibliotecario bibliotecario = new Bibliotecario(nombre,
                apellido, fecha, usuario, mail, contrasena, legajo);

        if (sistemaLogin.registrarBibliotecario(bibliotecario)) {
            System.out.println("Bibliotecario registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el bibliotecario.");
            System.out.println("El usuario o correo ya se encuentra registrado.");
        }
    }

    // Recupera el nombre de usuario mediante el correo electrónico
    private static void recuperarUsuario() {

        System.out.println("\n========== RECUPERAR USUARIO ==========");

        System.out.print("Ingrese su correo electrónico: ");
        String mail = teclado.nextLine();

        String usuario = sistemaLogin.recuperarUsuario(mail);

        if (usuario != null) {
            System.out.println("Su nombre de usuario es: " + usuario);
        } else {
            System.out.println("No se encontró una cuenta con ese correo.");
        }
    }

    // Recupera la contraseña mediante el correo electrónico
    private static void recuperarContrasena() {

        System.out.println("\n========== RECUPERAR CONTRASEÑA ==========");

        System.out.print("Ingrese su correo electrónico: ");
        String mail = teclado.nextLine();

        String contrasena = sistemaLogin.recuperarContrasena(mail);

        if (contrasena != null) {
            System.out.println("Su contraseña es: " + contrasena);
        } else {
            System.out.println("No se encontró una cuenta con ese correo.");
        }
    }

    // Menú de opciones para el cliente
    private static void menuCliente(Cliente cliente) {

        int opcion;

        do {
            System.out.println("\n========== MENÚ DEL CLIENTE ==========");
            System.out.println("1. Consultar catálogo de libros");
            System.out.println("2. Buscar libro");
            System.out.println("3. Alquilar libro");
            System.out.println("4. Comprar libro");
            System.out.println("5. Consultar libros alquilados");
            System.out.println("6. Consultar libros comprados");
            System.out.println("7. Devolver libro");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    biblioteca.listar();
                    break;

                case 2:
                    buscarLibro();
                    break;

                case 3:
                    alquilarLibro(cliente);
                    break;

                case 4:
                    venderLibro(cliente);
                    break;

                case 5:
                    mostrarLibrosCliente(cliente.getLibrosAlquilados(),
                            "LIBROS ALQUILADOS");
                    break;

                case 6:
                    mostrarLibrosCliente(cliente.getLibrosComprados(),
                            "LIBROS COMPRADOS");
                    break;

                case 7:
                    devolverLibro(cliente);
                    break;

                case 0:
                    System.out.println("Sesión cerrada correctamente.");
                    break;

                default:
                    System.out.println("Opción incorrecta.");
            }

        } while (opcion != 0);
    }

    // Menú de opciones para el bibliotecario
    private static void menuBibliotecario(Bibliotecario bibliotecario) {

        int opcion;

        do {
            System.out.println("\n========== MENÚ DEL BIBLIOTECARIO ==========");
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

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    biblioteca.listar();
                    break;

                case 2:
                    agregarLibro();
                    break;

                case 3:
                    eliminarLibro();
                    break;

                case 4:
                    modificarLibro();
                    break;

                case 5:
                    actualizarStock();
                    break;

                case 6:
                    buscarLibro();
                    break;

                case 7:
                    registrarAutor();
                    break;

                case 8:
                    buscarAutor();
                    break;

                case 9:
                    listarAutores();
                    break;

                case 0:
                    System.out.println("Sesión cerrada correctamente.");
                    break;

                default:
                    System.out.println("Opción incorrecta.");
            }

        } while (opcion != 0);
    }

    // Busca libros por título, autor u otros datos
    private static void buscarLibro() {

        System.out.println("\n========== BUSCAR LIBRO ==========");

        System.out.print("Ingrese el texto que desea buscar: ");
        String texto = teclado.nextLine();

        List<Libro> resultados = biblioteca.buscarLibro(texto);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron libros.");
        } else {
            for (Libro libro : resultados) {
                System.out.println("\n" + libro.mostrar());
                System.out.println("--------------------------------");
            }
        }
    }

    // Registra un nuevo autor
    private static void registrarAutor() {

        System.out.println("\n========== REGISTRAR AUTOR ==========");

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Apellido: ");
        String apellido = teclado.nextLine();

        System.out.print("Fecha de nacimiento: ");
        String fecha = teclado.nextLine();

        System.out.print("Bibliografía: ");
        String bibliografia = teclado.nextLine();

        Autor autor = new Autor(nombre, apellido, fecha, bibliografia);

        if (biblioteca.agregarAutor(autor)) {
            System.out.println("Autor registrado correctamente.");
        } else {
            System.out.println("El autor ya está registrado.");
        }
    }

    // Busca autores por nombre o apellido
    private static void buscarAutor() {

        System.out.println("\n========== BUSCAR AUTOR ==========");

        System.out.print("Ingrese el nombre o apellido: ");
        String nombre = teclado.nextLine();

        List<Autor> resultados = biblioteca.buscarAutorPorNombre(nombre);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron autores.");
        } else {
            for (Autor autor : resultados) {
                System.out.println(autor);
            }
        }
    }

    // Lista todos los autores registrados
    private static void listarAutores() {

        System.out.println("\n========== AUTORES REGISTRADOS ==========");

        if (biblioteca.getAutores().isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        for (Autor autor : biblioteca.getAutores()) {
            System.out.println(autor);
        }
    }

    // Agrega un libro de cualquier categoría
    private static void agregarLibro() {

        System.out.println("\n========== AGREGAR LIBRO ==========");
        System.out.println("1. Arte");
        System.out.println("2. Científico");
        System.out.println("3. Literatura");
        System.out.println("4. Tecnología");
        System.out.print("Seleccione la categoría: ");

        int categoria = leerEntero();

        if (categoria < 1 || categoria > 4) {
            System.out.println("Categoría incorrecta.");
            return;
        }

        System.out.print("Título: ");
        String titulo = teclado.nextLine();

        System.out.print("Editorial: ");
        String editorial = teclado.nextLine();

        System.out.print("Edición: ");
        int edicion = leerEntero();

        System.out.print("Fecha de publicación: ");
        String fecha = teclado.nextLine();

        Autor autor = seleccionarAutor();

        System.out.print("Precio de venta: ");
        float precioVenta = leerFloat();

        System.out.print("Precio de alquiler: ");
        float precioAlquiler = leerFloat();

        System.out.print("Stock: ");
        int stock = leerEntero();

        System.out.println("Formato:");
        System.out.println("1. Físico");
        System.out.println("2. Digital");
        System.out.print("Seleccione: ");

        int opcionFormato = leerEntero();

        FormatoLibro formato;

        if (opcionFormato == 1) {
            formato = FormatoLibro.FISICO;
        } else if (opcionFormato == 2) {
            formato = FormatoLibro.DIGITAL;
        } else {
            System.out.println("Formato incorrecto.");
            return;
        }

        Libro libro = null;

        switch (categoria) {

            case 1:
                System.out.println("Tipos de arte:");
                for (TipoArte tipo : TipoArte.values()) {
                    System.out.println((tipo.ordinal() + 1) + ". " + tipo);
                }

                System.out.print("Seleccione el tipo de arte: ");
                int tipoArte = leerEntero();

                if (tipoArte >= 1 && tipoArte <= TipoArte.values().length) {
                    libro = new Arte(titulo, editorial, edicion, fecha,
                            autor, precioVenta, precioAlquiler, stock,
                            formato, TipoArte.values()[tipoArte - 1]);
                }
                break;

            case 2:
                System.out.println("Áreas científicas:");
                for (AreaCientifica area : AreaCientifica.values()) {
                    System.out.println((area.ordinal() + 1) + ". " + area);
                }

                System.out.print("Seleccione el área: ");
                int areaCientifica = leerEntero();

                if (areaCientifica >= 1
                        && areaCientifica <= AreaCientifica.values().length) {
                    libro = new Cientifico(titulo, editorial, edicion, fecha,
                            autor, precioVenta, precioAlquiler, stock,
                            formato, AreaCientifica.values()[areaCientifica - 1]);
                }
                break;

            case 3:
                System.out.println("Géneros literarios:");
                for (GeneroLiterario genero : GeneroLiterario.values()) {
                    System.out.println((genero.ordinal() + 1) + ". " + genero);
                }

                System.out.print("Seleccione el género: ");
                int genero = leerEntero();

                if (genero >= 1 && genero <= GeneroLiterario.values().length) {
                    libro = new Literatura(titulo, editorial, edicion, fecha,
                            autor, precioVenta, precioAlquiler, stock,
                            formato, GeneroLiterario.values()[genero - 1]);
                }
                break;

            case 4:
                System.out.println("Áreas tecnológicas:");
                for (AreaTecnologia area : AreaTecnologia.values()) {
                    System.out.println((area.ordinal() + 1) + ". " + area);
                }

                System.out.print("Seleccione el área: ");
                int areaTecnologia = leerEntero();

                if (areaTecnologia >= 1
                        && areaTecnologia <= AreaTecnologia.values().length) {
                    libro = new Tecnologia(titulo, editorial, edicion, fecha,
                            autor, precioVenta, precioAlquiler, stock,
                            formato, AreaTecnologia.values()[areaTecnologia - 1]);
                }
                break;
        }

        if (libro == null) {
            System.out.println("No se pudo crear el libro. Opción incorrecta.");
            return;
        }

        if (biblioteca.agregarLibro(libro)) {
            System.out.println("Libro agregado correctamente.");
        } else {
            System.out.println("El libro ya se encuentra registrado.");
        }
    }

    // Permite seleccionar un autor existente
    private static Autor seleccionarAutor() {

        List<Autor> autores = biblioteca.getAutores();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            System.out.println("El libro se registrará sin autor.");
            return null;
        }

        System.out.println("\nAutores disponibles:");

        for (int i = 0; i < autores.size(); i++) {
            System.out.println((i + 1) + ". " + autores.get(i).getNombre()
                    + " " + autores.get(i).getApellido());
        }

        System.out.println("0. Sin autor");
        System.out.print("Seleccione un autor: ");

        int opcion = leerEntero();

        if (opcion == 0) {
            return null;
        }

        if (opcion >= 1 && opcion <= autores.size()) {
            return autores.get(opcion - 1);
        }

        System.out.println("Selección incorrecta.");
        return null;
    }

    // Elimina un libro según su posición en la lista
    private static void eliminarLibro() {

        biblioteca.listar();

        if (biblioteca.getLibros().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el número del libro que desea eliminar: ");
        int posicion = leerEntero() - 1;

        if (posicion < 0 || posicion >= biblioteca.getLibros().size()) {
            System.out.println("Número de libro incorrecto.");
            return;
        }

        Libro libro = biblioteca.getLibros().get(posicion);

        if (biblioteca.eliminarLibro(libro)) {
            System.out.println("Libro eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el libro.");
        }
    }

    // Modifica los datos principales de un libro
    private static void modificarLibro() {

        biblioteca.listar();

        if (biblioteca.getLibros().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el número del libro que desea modificar: ");
        int posicion = leerEntero() - 1;

        if (posicion < 0 || posicion >= biblioteca.getLibros().size()) {
            System.out.println("Número de libro incorrecto.");
            return;
        }

        Libro libro = biblioteca.getLibros().get(posicion);

        System.out.print("Nuevo título: ");
        libro.setTitulo(teclado.nextLine());

        System.out.print("Nueva editorial: ");
        libro.setEditorial(teclado.nextLine());

        System.out.print("Nueva edición: ");
        libro.setEdicion(leerEntero());

        System.out.print("Nueva fecha de publicación: ");
        libro.setFechaPublicacion(teclado.nextLine());

        System.out.print("Nuevo precio de venta: ");
        libro.setPrecioVenta(leerFloat());

        System.out.print("Nuevo precio de alquiler: ");
        libro.setPrecioAlquiler(leerFloat());

        System.out.println("Libro modificado correctamente.");
    }

    // Actualiza el stock de un libro
    private static void actualizarStock() {

        biblioteca.listar();

        if (biblioteca.getLibros().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el número del libro: ");
        int posicion = leerEntero() - 1;

        if (posicion < 0 || posicion >= biblioteca.getLibros().size()) {
            System.out.println("Número de libro incorrecto.");
            return;
        }

        System.out.print("Ingrese el nuevo stock: ");
        int stock = leerEntero();

        Libro libro = biblioteca.getLibros().get(posicion);

        if (biblioteca.actualizarStock(libro, stock)) {
            System.out.println("Stock actualizado correctamente.");
        } else {
            System.out.println("No se pudo actualizar el stock.");
        }
    }

    // Permite alquilar un libro
    private static void alquilarLibro(Cliente cliente) {

        biblioteca.listar();

        if (biblioteca.getLibros().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el número del libro que desea alquilar: ");
        int posicion = leerEntero() - 1;

        if (posicion < 0 || posicion >= biblioteca.getLibros().size()) {
            System.out.println("Número de libro incorrecto.");
            return;
        }

        Libro libro = biblioteca.getLibros().get(posicion);

        if (biblioteca.alquilarLibro(cliente, libro)) {
            System.out.println("Libro alquilado correctamente.");
        } else {
            System.out.println("No hay stock disponible o el libro no existe.");
        }
    }

    // Permite comprar un libro
    private static void venderLibro(Cliente cliente) {

        biblioteca.listar();

        if (biblioteca.getLibros().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el número del libro que desea comprar: ");
        int posicion = leerEntero() - 1;

        if (posicion < 0 || posicion >= biblioteca.getLibros().size()) {
            System.out.println("Número de libro incorrecto.");
            return;
        }

        Libro libro = biblioteca.getLibros().get(posicion);

        if (biblioteca.venderLibro(cliente, libro)) {
            System.out.println("Compra realizada correctamente.");
        } else {
            System.out.println("No hay stock disponible o el libro no existe.");
        }
    }

    // Permite devolver un libro alquilado
    private static void devolverLibro(Cliente cliente) {

        mostrarLibrosCliente(cliente.getLibrosAlquilados(),
                "LIBROS DISPONIBLES PARA DEVOLUCIÓN");

        if (cliente.getLibrosAlquilados().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el número del libro que desea devolver: ");
        int posicion = leerEntero() - 1;

        if (posicion < 0 || posicion >= cliente.getLibrosAlquilados().size()) {
            System.out.println("Número de libro incorrecto.");
            return;
        }

        Libro libro = cliente.getLibrosAlquilados().get(posicion);

        cliente.devolverLibroAlquilado(libro);
        libro.setStock(libro.getStock() + 1);

        System.out.println("Libro devuelto correctamente.");
    }

    // Muestra los libros alquilados o comprados por un cliente
    private static void mostrarLibrosCliente(List<Libro> libros, String titulo) {

        System.out.println("\n========== " + titulo + " ==========");

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        for (int i = 0; i < libros.size(); i++) {
            System.out.println((i + 1) + ". " + libros.get(i));
        }
    }

    // Lee un número entero desde el teclado
    private static int leerEntero() {

        while (true) {
            try {
                int numero = Integer.parseInt(teclado.nextLine().trim());
                return numero;
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número entero válido: ");
            }
        }
    }

    // Lee un número decimal desde el teclado
    private static float leerFloat() {

        while (true) {
            try {
                String valor = teclado.nextLine().trim().replace(',', '.');
                return Float.parseFloat(valor);
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
}
