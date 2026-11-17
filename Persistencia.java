package entidades.Caceres.Moreira;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author Caceres.Moreira
 */

/**
 * Clase Persistencia.
 * Permite guardar y recuperar la información del sistema
 * mediante archivos de texto legibles.
 */
public class Persistencia {

    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final String ARCHIVO_LIBROS = "libros.txt";
    private static final String ARCHIVO_MOVIMIENTOS = "movimientos.txt";

    // Constructor privado para evitar instanciar la clase
    private Persistencia() {
    }

    // Abre un archivo para escritura utilizando UTF-8
    private static BufferedWriter abrirEscritura(String nombreArchivo)
            throws IOException {

        return Files.newBufferedWriter(
                new File(nombreArchivo).toPath(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);
    }

    // Prepara un texto para guardarlo separado por punto y coma
    private static String campoLegible(String texto) {

        if (texto == null) {
            return "";
        }

        return texto.replace("\\", "\\\\")
                .replace(";", "\\;")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    // Separa los campos respetando los puntos y coma escapados
    private static String[] separarCampos(String linea) {

        List<String> campos = new ArrayList<>();
        StringBuilder campo = new StringBuilder();
        boolean escape = false;

        for (int i = 0; i < linea.length(); i++) {

            char caracter = linea.charAt(i);

            if (escape) {

                switch (caracter) {
                    case 'n':
                        campo.append('\n');
                        break;
                    case 'r':
                        campo.append('\r');
                        break;
                    default:
                        campo.append(caracter);
                        break;
                }

                escape = false;

            } else if (caracter == '\\') {

                escape = true;

            } else if (caracter == ';') {

                campos.add(campo.toString());
                campo.setLength(0);

            } else {

                campo.append(caracter);
            }
        }

        if (escape) {
            campo.append('\\');
        }

        campos.add(campo.toString());

        return campos.toArray(new String[0]);
    }

    // Decodifica textos de los formatos anteriores y del formato intermedio TXT:
    private static String decodificarAntiguo(String texto) {

        if (texto == null || texto.equals("-")) {
            return null;
        }

        // Formato intermedio: TXT:texto
        if (texto.startsWith("TXT:")) {
            String contenido = texto.substring(4);
            StringBuilder resultado = new StringBuilder();
            boolean escape = false;

            for (int i = 0; i < contenido.length(); i++) {
                char caracter = contenido.charAt(i);

                if (escape) {
                    switch (caracter) {
                        case 't':
                            resultado.append('\t');
                            break;
                        case 'n':
                            resultado.append('\n');
                            break;
                        case 'r':
                            resultado.append('\r');
                            break;
                        default:
                            resultado.append(caracter);
                            break;
                    }
                    escape = false;
                } else if (caracter == '\\') {
                    escape = true;
                } else {
                    resultado.append(caracter);
                }
            }

            if (escape) {
                resultado.append('\\');
            }

            return resultado.toString();
        }

        // Formato antiguo: Base64. Si no es Base64, conserva el texto.
        try {
            return new String(
                    Base64.getDecoder().decode(texto),
                    StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return texto;
        }
    }

    // Guarda toda la información del sistema
    public static boolean guardar(SistemaLogin sistemaLogin,
            Biblioteca biblioteca) {

        if (sistemaLogin == null || biblioteca == null) {
            System.out.println("No se pueden guardar datos nulos.");
            return false;
        }

        try {
            guardarUsuarios(sistemaLogin);
            guardarLibros(biblioteca);
            guardarMovimientos(sistemaLogin, biblioteca);

            System.out.println("Información guardada correctamente.");
            System.out.println("Archivos creados o actualizados:");
            System.out.println("- " + ARCHIVO_USUARIOS);
            System.out.println("- " + ARCHIVO_LIBROS);
            System.out.println("- " + ARCHIVO_MOVIMIENTOS);

            return true;

        } catch (IOException | IllegalArgumentException
                | NullPointerException e) {

            System.out.println("Error al guardar los datos: "
                    + e.getMessage());
            return false;
        }
    }

    // Guarda clientes y bibliotecarios en formato legible
    private static void guardarUsuarios(SistemaLogin sistemaLogin)
            throws IOException {

        try (BufferedWriter salida = abrirEscritura(ARCHIVO_USUARIOS)) {

            for (Usuario usuario : sistemaLogin.getUsuarios()) {

                if (usuario instanceof Bibliotecario) {

                    Bibliotecario bibliotecario = (Bibliotecario) usuario;

                    salida.write("BIBLIOTECARIO;"
                            + campoLegible(bibliotecario.getNombre()) + ";"
                            + campoLegible(bibliotecario.getApellido()) + ";"
                            + campoLegible(bibliotecario.getFechaNacimiento()) + ";"
                            + campoLegible(bibliotecario.getUsuario()) + ";"
                            + campoLegible(bibliotecario.getMail()) + ";"
                            + campoLegible(bibliotecario.getContrasena()) + ";"
                            + bibliotecario.getLegajo());

                    salida.newLine();

                } else if (usuario instanceof Cliente) {

                    Cliente cliente = (Cliente) usuario;

                    salida.write("CLIENTE;"
                            + campoLegible(cliente.getNombre()) + ";"
                            + campoLegible(cliente.getApellido()) + ";"
                            + campoLegible(cliente.getFechaNacimiento()) + ";"
                            + campoLegible(cliente.getUsuario()) + ";"
                            + campoLegible(cliente.getMail()) + ";"
                            + campoLegible(cliente.getContrasena()) + ";"
                            + cliente.getNumeroSocio());

                    salida.newLine();
                }
            }
        }
    }

    // Guarda autores y libros en formato legible
    private static void guardarLibros(Biblioteca biblioteca)
            throws IOException {

        List<Autor> autores = new ArrayList<>();

        for (Autor autor : biblioteca.getAutores()) {
            agregarAutorUnico(autores, autor);
        }

        for (Libro libro : biblioteca.getLibros()) {
            if (libro != null) {
                agregarAutorUnico(autores, libro.getAutor());
            }
        }

        try (BufferedWriter salida = abrirEscritura(ARCHIVO_LIBROS)) {

            // Primero guarda los autores
            for (Autor autor : autores) {

                salida.write("AUTOR;"
                        + campoLegible(autor.getNombre()) + ";"
                        + campoLegible(autor.getApellido()) + ";"
                        + campoLegible(autor.getFechaNacimiento()) + ";"
                        + campoLegible(autor.getBibliografia()));

                salida.newLine();
            }

            // Después guarda los libros
            for (Libro libro : biblioteca.getLibros()) {

                if (libro == null) {
                    continue;
                }

                int indiceAutor = buscarAutor(autores, libro.getAutor());
                String categoria;

                if (libro instanceof Arte) {

                    if (((Arte) libro).getTipoArte() == null) {
                        throw new IllegalArgumentException(
                                "Un libro de Arte no tiene categoría.");
                    }

                    categoria = ((Arte) libro).getTipoArte().name();

                } else if (libro instanceof Cientifico) {

                    if (((Cientifico) libro).getAreaCientifica() == null) {
                        throw new IllegalArgumentException(
                                "Un libro Científico no tiene área.");
                    }

                    categoria = ((Cientifico) libro)
                            .getAreaCientifica().name();

                } else if (libro instanceof Literatura) {

                    if (((Literatura) libro).getGenero() == null) {
                        throw new IllegalArgumentException(
                                "Un libro de Literatura no tiene género.");
                    }

                    categoria = ((Literatura) libro).getGenero().name();

                } else if (libro instanceof Tecnologia) {

                    if (((Tecnologia) libro).getAreaTecnologia() == null) {
                        throw new IllegalArgumentException(
                                "Un libro de Tecnología no tiene área.");
                    }

                    categoria = ((Tecnologia) libro)
                            .getAreaTecnologia().name();

                } else {
                    throw new IllegalArgumentException(
                            "Tipo de libro no reconocido.");
                }

                if (libro.getFormato() == null) {
                    throw new IllegalArgumentException(
                            "El libro " + libro.getTitulo()
                            + " no tiene formato asignado.");
                }

                salida.write("LIBRO;"
                        + libro.getTipoLibro() + ";"
                        + campoLegible(libro.getTitulo()) + ";"
                        + campoLegible(libro.getEditorial()) + ";"
                        + libro.getEdicion() + ";"
                        + campoLegible(libro.getFechaPublicacion()) + ";"
                        + indiceAutor + ";"
                        + libro.getPrecioVenta() + ";"
                        + libro.getPrecioAlquiler() + ";"
                        + libro.getStock() + ";"
                        + libro.getFormato().name() + ";"
                        + categoria);

                salida.newLine();
            }
        }
    }

    // Evita guardar dos veces al mismo autor
    private static void agregarAutorUnico(List<Autor> autores,
            Autor autor) {

        if (autor != null && buscarAutor(autores, autor) == -1) {
            autores.add(autor);
        }
    }

    // Busca un autor por nombre y apellido
    private static int buscarAutor(List<Autor> autores, Autor autor) {

        if (autor == null) {
            return -1;
        }

        for (int i = 0; i < autores.size(); i++) {

            Autor existente = autores.get(i);

            if (existente.getNombre() != null
                    && existente.getApellido() != null
                    && autor.getNombre() != null
                    && autor.getApellido() != null
                    && existente.getNombre().equalsIgnoreCase(
                            autor.getNombre())
                    && existente.getApellido().equalsIgnoreCase(
                            autor.getApellido())) {

                return i;
            }
        }

        return -1;
    }

    // Guarda los alquileres y las compras de los clientes
    private static void guardarMovimientos(SistemaLogin sistemaLogin,
            Biblioteca biblioteca) throws IOException {

        try (BufferedWriter salida = abrirEscritura(
                ARCHIVO_MOVIMIENTOS)) {

            List<Libro> libros = biblioteca.getLibros();

            for (Usuario usuario : sistemaLogin.getUsuarios()) {

                if (usuario instanceof Cliente) {

                    Cliente cliente = (Cliente) usuario;

                    for (Libro libro : cliente.getLibrosAlquilados()) {

                        int indice = buscarLibro(libros, libro);

                        if (indice >= 0) {
                            salida.write("ALQUILER;"
                                    + campoLegible(cliente.getUsuario()) + ";"
                                    + indice);
                            salida.newLine();
                        }
                    }

                    for (Libro libro : cliente.getLibrosComprados()) {

                        int indice = buscarLibro(libros, libro);

                        if (indice >= 0) {
                            salida.write("COMPRA;"
                                    + campoLegible(cliente.getUsuario()) + ";"
                                    + indice);
                            salida.newLine();
                        }
                    }
                }
            }
        }
    }

    // Busca la posición de un libro en el catálogo
    private static int buscarLibro(List<Libro> libros, Libro libro) {

        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i) == libro) {
                return i;
            }
        }

        return -1;
    }

    // Comprueba que el registro tenga la cantidad esperada de campos
    private static void validarCampos(String[] datos, int cantidad,
            String archivo) throws IOException {

        if (datos.length != cantidad) {
            throw new IOException(
                    "Registro inválido en " + archivo + ".");
        }
    }

    // Recupera toda la información almacenada
    public static DatosSistema cargar() {

        File archivoUsuarios = new File(ARCHIVO_USUARIOS);
        File archivoLibros = new File(ARCHIVO_LIBROS);
        File archivoMovimientos = new File(ARCHIVO_MOVIMIENTOS);

        if (!archivoUsuarios.exists()
                && !archivoLibros.exists()
                && !archivoMovimientos.exists()) {

            System.out.println("No hay datos anteriores. "
                    + "Se iniciará una biblioteca nueva.");

            return null;
        }

        try {

            Biblioteca biblioteca = cargarLibros();
            SistemaLogin sistemaLogin = cargarUsuarios();

            cargarMovimientos(sistemaLogin, biblioteca);

            System.out.println("Datos recuperados correctamente.");

            return new DatosSistema(sistemaLogin, biblioteca);

        } catch (IOException | IllegalArgumentException
                | IndexOutOfBoundsException | NullPointerException e) {

            System.out.println("Error al recuperar los datos: "
                    + e.getMessage());

            throw new IllegalStateException(
                    "No se pudieron recuperar los datos. "
                    + "No se debe continuar guardando sobre los archivos "
                    + "existentes hasta solucionar el problema.", e);
        }
    }

    // Recupera autores y libros; admite el formato nuevo y el anterior
    private static Biblioteca cargarLibros() throws IOException {

        Biblioteca biblioteca = new Biblioteca();
        List<Autor> autores = new ArrayList<>();
        List<Libro> libros = new ArrayList<>();

        File archivo = new File(ARCHIVO_LIBROS);

        if (!archivo.exists()) {
            return biblioteca;
        }

        try (BufferedReader entrada = Files.newBufferedReader(
                archivo.toPath(), StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = entrada.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                // El formato anterior utilizaba tabulaciones y Base64
                if (linea.indexOf('\t') >= 0) {

                    String[] datos = linea.split("\t", -1);

                    if (datos[0].equals("A")) {

                        validarCampos(datos, 5, ARCHIVO_LIBROS);

                        autores.add(new Autor(
                                decodificarAntiguo(datos[1]),
                                decodificarAntiguo(datos[2]),
                                decodificarAntiguo(datos[3]),
                                decodificarAntiguo(datos[4])));

                    } else if (datos[0].equals("L")) {

                        validarCampos(datos, 12, ARCHIVO_LIBROS);

                        agregarLibroRecuperado(
                                libros, autores,
                                datos[1],
                                decodificarAntiguo(datos[2]),
                                decodificarAntiguo(datos[3]),
                                datos[4],
                                decodificarAntiguo(datos[5]),
                                datos[6], datos[7], datos[8],
                                datos[9], datos[10], datos[11]);

                    } else {
                        throw new IOException(
                                "Registro antiguo desconocido en "
                                + ARCHIVO_LIBROS + ".");
                    }

                } else {

                    String[] datos = separarCampos(linea);

                    if (datos[0].equals("AUTOR")) {

                        validarCampos(datos, 5, ARCHIVO_LIBROS);

                        autores.add(new Autor(
                                datos[1], datos[2], datos[3], datos[4]));

                    } else if (datos[0].equals("LIBRO")) {

                        validarCampos(datos, 12, ARCHIVO_LIBROS);

                        agregarLibroRecuperado(
                                libros, autores,
                                datos[1], datos[2], datos[3],
                                datos[4], datos[5], datos[6],
                                datos[7], datos[8], datos[9],
                                datos[10], datos[11]);

                    } else {
                        throw new IOException(
                                "Registro desconocido en "
                                + ARCHIVO_LIBROS + ".");
                    }
                }
            }
        }

        biblioteca.setAutores(autores);
        biblioteca.setLibros(libros);

        return biblioteca;
    }

    // Construye un libro a partir de los campos recuperados
    private static void agregarLibroRecuperado(List<Libro> libros,
            List<Autor> autores, String tipo, String titulo,
            String editorial, String edicionTexto, String fecha,
            String indiceAutorTexto, String precioVentaTexto,
            String precioAlquilerTexto, String stockTexto,
            String formatoTexto, String categoria) throws IOException {

        int edicion = Integer.parseInt(edicionTexto);
        int indiceAutor = Integer.parseInt(indiceAutorTexto);

        Autor autor = null;

        if (indiceAutor >= 0 && indiceAutor < autores.size()) {
            autor = autores.get(indiceAutor);
        } else if (indiceAutor != -1) {
            throw new IOException("Índice de autor inválido.");
        }

        float precioVenta = Float.parseFloat(precioVentaTexto);
        float precioAlquiler = Float.parseFloat(precioAlquilerTexto);
        int stock = Integer.parseInt(stockTexto);
        FormatoLibro formato = FormatoLibro.valueOf(formatoTexto);

        Libro libro;

        switch (tipo) {

            case "Arte":
                libro = new Arte(
                        titulo, editorial, edicion, fecha, autor,
                        precioVenta, precioAlquiler, stock, formato,
                        TipoArte.valueOf(categoria));
                break;

            case "Cientifico":
                libro = new Cientifico(
                        titulo, editorial, edicion, fecha, autor,
                        precioVenta, precioAlquiler, stock, formato,
                        AreaCientifica.valueOf(categoria));
                break;

            case "Literatura":
                libro = new Literatura(
                        titulo, editorial, edicion, fecha, autor,
                        precioVenta, precioAlquiler, stock, formato,
                        GeneroLiterario.valueOf(categoria));
                break;

            case "Tecnologia":
                libro = new Tecnologia(
                        titulo, editorial, edicion, fecha, autor,
                        precioVenta, precioAlquiler, stock, formato,
                        AreaTecnologia.valueOf(categoria));
                break;

            default:
                throw new IOException("Tipo de libro desconocido: " + tipo);
        }

        libros.add(libro);
    }

    // Recupera clientes y bibliotecarios; admite el formato nuevo y anterior
    private static SistemaLogin cargarUsuarios() throws IOException {

        SistemaLogin sistemaLogin = new SistemaLogin();
        File archivo = new File(ARCHIVO_USUARIOS);

        if (!archivo.exists()) {
            return sistemaLogin;
        }

        try (BufferedReader entrada = Files.newBufferedReader(
                archivo.toPath(), StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = entrada.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String tipo;
                String[] datos;

                if (linea.indexOf('\t') >= 0) {

                    // Formato anterior: C/B, campos Base64 e ID
                    String[] antiguos = linea.split("\t", -1);
                    validarCampos(antiguos, 9, ARCHIVO_USUARIOS);

                    tipo = antiguos[0].equals("C")
                            ? "CLIENTE" : "BIBLIOTECARIO";

                    datos = new String[]{
                        tipo,
                        decodificarAntiguo(antiguos[2]),
                        decodificarAntiguo(antiguos[3]),
                        decodificarAntiguo(antiguos[4]),
                        decodificarAntiguo(antiguos[5]),
                        decodificarAntiguo(antiguos[6]),
                        decodificarAntiguo(antiguos[7]),
                        antiguos[8]
                    };

                } else {

                    datos = separarCampos(linea);
                    validarCampos(datos, 8, ARCHIVO_USUARIOS);
                    tipo = datos[0];
                }

                int numero = Integer.parseInt(datos[7]);

                if (tipo.equals("CLIENTE")) {

                    Cliente cliente = new Cliente(
                            datos[1], datos[2], datos[3],
                            datos[4], datos[5], datos[6], numero);

                    sistemaLogin.registrarCliente(cliente);

                } else if (tipo.equals("BIBLIOTECARIO")) {

                    Bibliotecario bibliotecario = new Bibliotecario(
                            datos[1], datos[2], datos[3],
                            datos[4], datos[5], datos[6], numero);

                    sistemaLogin.registrarBibliotecario(bibliotecario);

                } else {
                    throw new IOException(
                            "Tipo de usuario desconocido: " + tipo);
                }
            }
        }

        return sistemaLogin;
    }

    // Recupera alquileres y compras; admite el formato nuevo y anterior
    private static void cargarMovimientos(SistemaLogin sistemaLogin,
            Biblioteca biblioteca) throws IOException {

        File archivo = new File(ARCHIVO_MOVIMIENTOS);

        if (!archivo.exists() || sistemaLogin.getUsuarios().isEmpty()) {
            return;
        }

        List<Libro> libros = biblioteca.getLibros();

        try (BufferedReader entrada = Files.newBufferedReader(
                archivo.toPath(), StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = entrada.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String tipo;
                String nombreUsuario;
                int indiceLibro;

                if (linea.indexOf('\t') >= 0) {

                    // Formato anterior: tipo, usuario Base64 e índice
                    String[] antiguos = linea.split("\t", -1);
                    validarCampos(antiguos, 3, ARCHIVO_MOVIMIENTOS);

                    tipo = antiguos[0];
                    nombreUsuario = decodificarAntiguo(antiguos[1]);
                    indiceLibro = Integer.parseInt(antiguos[2]);

                } else {

                    String[] datos = separarCampos(linea);
                    validarCampos(datos, 3, ARCHIVO_MOVIMIENTOS);

                    tipo = datos[0];
                    nombreUsuario = datos[1];
                    indiceLibro = Integer.parseInt(datos[2]);
                }

                if (indiceLibro < 0 || indiceLibro >= libros.size()) {
                    throw new IOException(
                            "Índice de libro inválido en "
                            + ARCHIVO_MOVIMIENTOS + ".");
                }

                Libro libro = libros.get(indiceLibro);
                boolean clienteEncontrado = false;

                for (Usuario usuario : sistemaLogin.getUsuarios()) {

                    if (usuario instanceof Cliente
                            && usuario.getUsuario() != null
                            && usuario.getUsuario().equalsIgnoreCase(
                                    nombreUsuario)) {

                        Cliente cliente = (Cliente) usuario;

                        if (tipo.equals("ALQUILER")) {
                            cliente.agregarLibroAlquilado(libro);

                        } else if (tipo.equals("COMPRA")) {
                            cliente.agregarLibroComprado(libro);

                        } else {
                            throw new IOException(
                                    "Movimiento desconocido: " + tipo);
                        }

                        clienteEncontrado = true;
                        break;
                    }
                }

                if (!clienteEncontrado) {
                    throw new IOException(
                            "No se encontró el cliente del movimiento: "
                            + nombreUsuario);
                }
            }
        }
    }
}
