package view;

import Model.*;
import controller.SuperMaxiController;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Scanner;

public class SuperMaxiView {
    private SuperMaxiController controller;
    private Factura facturaEnProceso;

    public SuperMaxiView(SuperMaxiController controller) {
        this.controller = controller;
        this.facturaEnProceso = null;
    }

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
             System.out.println("\n+------------------------------------------------------+");
            System.out.println("|              SUPERMAXI LOJA - FACTURACIÓN            |");
            System.out.println("+------------------------------------------------------+");
            System.out.println("| 1. Ver productos disponibles                         |");
            System.out.println("| 2. Registrar un nuevo producto                       |");
            System.out.println("| 3. Crear una factura                                 |");
            System.out.println("| 4. Consultar ventas                                  |");
            System.out.println("| 5. Buscar producto por código                        |");
            System.out.println("| 6. Salir del sistema                                 |");
            System.out.println("+------------------------------------------------------+");
            System.out.print(">> Seleccione una opción del menú: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> mostrarInventario();
                case 2 -> registrarProducto();
                case 3 -> gestionarFactura();
                case 4 -> mostrarEstadisticas();
                case 5 -> buscarProductoPorCodigo();
                case 6 -> salir();
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 6);
    }

    
    // Función para buscar producto por código
    private void buscarProductoPorCodigo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el código del producto: ");
        String codigo = sc.nextLine().trim();
        Producto p = controller.buscarProductoPorCodigo(codigo);
        if (p == null) {
            System.out.println("Producto no encontrado.");
        } else {
            System.out.println("\nDetalles del producto:");
            System.out.println("Código: " + p.getCodigoProducto());
            System.out.println("Descripción: " + p.getDescripcion());
            System.out.println("Categoría: " + p.getCategoria());
            System.out.println("Precio base: " + p.getPrecioBase());
            System.out.println("Precio final: " + String.format("%.2f", p.calcularPrecioFinal()));
            System.out.println("Stock: " + p.getUnidadesDisponibles());
            System.out.println("Etiqueta: " + p.getEtiqueta());
            System.out.println("Información adicional: " + p.obtenerInformacionExtra());
            System.out.println();
        }
    }
    
    private void mostrarInventario() {
        ArrayList<Producto> productos = controller.obtenerProductos();

        System.out.println("+----------+-------------------------------+------------+----------+-------+-----------+-------------------------+");
        System.out.println("| CÓDIGO   | DESCRIPCIÓN                   | CATEGORÍA  | PRECIO   | STOCK | ETIQUETA  | INFORMACIÓN EXTRA        |");
        System.out.println("+----------+-------------------------------+------------+----------+-------+-----------+-------------------------+");

        for (Producto p : productos) {
            String infoExtra = p.obtenerInformacionExtra(); // Cada subclase retorna info específica
            System.out.printf("| %-8s | %-29s | %-10s | %8.2f | %5d | %-9s | %-23s |\n",
                    p.getCodigoProducto(),
                    p.getDescripcion(),
                    p.getCategoria(),
                    p.getPrecioBase(),
                    p.getUnidadesDisponibles(),
                    p.getEtiqueta(),
                    infoExtra);
        }

        System.out.println("+----------+-------------------------------+------------+----------+-------+-----------+-------------------------+");
    }


    private void registrarProducto() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Registrar Nuevo Producto ---");
        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        System.out.print("Categoría: ");
        String categoria = sc.nextLine();

        System.out.print("Precio base: ");
        double precio = Double.parseDouble(sc.nextLine());

        System.out.print("Stock disponible: ");
        int stock = Integer.parseInt(sc.nextLine());

        System.out.print("Etiqueta (Local/Importado): ");
        String etiqueta = sc.nextLine();

        System.out.println("Seleccione tipo de producto:");
        System.out.println("1. Producto Local");
        System.out.println("2. Producto Importado");
        System.out.println("3. Producto Perecible");
        System.out.println("4. Producto No Perecible");
        System.out.print("Opción: ");
        int tipo = Integer.parseInt(sc.nextLine());

        Producto nuevoProducto = null;

        switch (tipo) {
            case 1 -> {
                nuevoProducto = new ProductoLocal(descripcion, categoria, precio, stock, etiqueta);
            }
            case 2 -> {
                System.out.print("País de orígen: ");
                String pais = sc.nextLine();
                nuevoProducto = new ProductoImportado(descripcion, categoria, precio, stock, etiqueta, pais);
            }
            case 3 -> {
                System.out.print("Fecha de vencimiento (AAAA-MM-DD): ");
                String fechaVencimientoStr = sc.nextLine();
                LocalDate fechaVencimiento;
                try {
                    fechaVencimiento = LocalDate.parse(fechaVencimientoStr);
                    nuevoProducto = new ProductoPerecible(descripcion, categoria, precio, stock, etiqueta, fechaVencimiento);
                } catch (Exception e) {
                    System.out.println("Fecha inválida. Producto no registrado.");
                    return;
                }
            }
            case 4 -> {
                System.out.print("Material del producto: ");
                String material = sc.nextLine();
                nuevoProducto = new ProductoNoPerecible(descripcion, categoria, precio, stock, etiqueta, material);
            }

            default -> {
                System.out.println("Opción inválida. Producto no registrado.");
                return;
            }
        }

        if (controller.agregarProducto(nuevoProducto)) {
            System.out.println("Producto registrado exitosamente.");
        } else {
            System.out.println("Error al registrar producto.");
        }
    }


    private void gestionarFactura() {
        Scanner sc = new Scanner(System.in);
        facturaEnProceso = null;
        System.out.println("=== Crear nueva factura ===");
        Cliente cliente = seleccionarCliente();
        if (cliente == null) {
            System.out.println("Cliente no válido. Cancelando factura.");
            return;
        }
        facturaEnProceso = controller.crearFactura(cliente);

        boolean continuar = true;
        while (continuar) {
            mostrarInventario();
            System.out.print("Ingrese código de producto para agregar al carrito (o 'fin' para terminar): ");
            String codigo = sc.nextLine().trim();

            if (codigo.equalsIgnoreCase("fin")) {
                if (facturaEnProceso.getDetalles().isEmpty()) {
                    System.out.println("No hay productos en el carrito. Cancelando factura.");
                    return;
                }
                continuar = false;
                break;
            }

            try {
                System.out.print("Ingrese cantidad: ");
                int cantidad = Integer.parseInt(sc.nextLine());

                boolean agregado = controller.agregarDetalleAFactura(facturaEnProceso, codigo, cantidad);
                System.out.println(agregado ? "Producto agregado al carrito." : "No se pudo agregar producto.");

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            mostrarCarrito();

            System.out.print("¿Desea eliminar algún producto del carrito? (s/n): ");
            String resp = sc.nextLine().trim();
            if (resp.equalsIgnoreCase("s")) {
                System.out.print("Ingrese código del producto a eliminar: ");
                String codigoEliminar = sc.nextLine().trim();
                boolean eliminado = controller.eliminarDetalleDeFactura(facturaEnProceso, codigoEliminar);
                System.out.println(eliminado ? "Producto eliminado del carrito." : "Producto no encontrado en el carrito.");
                mostrarCarrito();
            }
        }

        controller.calcularTotalesFactura(facturaEnProceso);
        mostrarFactura(facturaEnProceso);

        System.out.print("¿Desea guardar esta factura? (s/n): ");
        String guardar = sc.nextLine().trim();
        if (guardar.equalsIgnoreCase("s")) {
            boolean guardado = controller.guardarFactura(facturaEnProceso);
            boolean guardado2 = controller.agregarCliente(cliente);
            System.out.println(guardado ? "Factura guardada con éxito." : "Error al guardar factura.");
        } else {
            System.out.println("Factura no guardada.");
        }
    }

    private Cliente seleccionarCliente() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Seleccione tipo de cliente ===");
        System.out.println("1. Cliente Persona Natural");
        System.out.println("2. Cliente Empresa");
        System.out.println("3. Consumidor Final");
        System.out.print("Ingrese una opción: ");

        int opcion = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        switch (opcion) {
            case 1 -> {
                System.out.print("Ingrese nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Ingrese cédula: ");
                String cedula = sc.nextLine();
                System.out.print("Ingrese email: ");
                String email = sc.nextLine();
                System.out.print("Ingrese celular: ");
                String celular = sc.nextLine();
                System.out.print("Ingrese ubicación: ");
                String ubicacion = sc.nextLine();
                System.out.print("Ingrese el Estado Civil: ");
                String estadoCivil = sc.nextLine();

                return new ClientePersonaNatural(cedula, nombre, email, celular, ubicacion, estadoCivil);
            }
            case 2 -> {
                System.out.print("Ingrese razón social: ");
                String razonSocial = sc.nextLine();
                System.out.print("Ingrese RUC: ");
                String ruc = sc.nextLine();
                System.out.print("Ingrese email: ");
                String email = sc.nextLine();
                System.out.print("Ingrese celular: ");
                String celular = sc.nextLine();
                System.out.print("Ingrese ubicación: ");
                String ubicacion = sc.nextLine();
                System.out.print("Ingrese el nombre del contacto de la empresa: ");
                String nombre = sc.nextLine();

                return new ClienteEmpresa(ruc, razonSocial, email, celular, ubicacion, nombre);
            }
            case 3 -> {
                return new ConsumidorFinal();
            }
            default -> {
                System.out.println("Opción no válida.");
                return null;
            }
        }
    }


    private void mostrarCarrito() {
        System.out.println("\n+------------------ Carrito de Compra ------------------+");
        System.out.println("| Código  | Nombre Producto           | Cant | Precio   | Subtotal  |");
        System.out.println("+---------+--------------------------+------+----------+-----------+");
        for (DetallesFactura df : facturaEnProceso.getDetalles()) {
            System.out.printf("| %-7s | %-24s | %4d | %8.2f | %9.2f |\n",
                    df.getProducto().getCodigoProducto(),
                    df.getProducto().getDescripcion(),
                    df.getUnidadesVendidas(),
                    df.getPrecioIndividual(),
                    df.getSubtotal());
        }
        System.out.println("+--------------------------------------------------------+");
    }

    private void mostrarFactura(Factura factura) {
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                       SUPERMERCADO SUPERMAXI                         |");
        System.out.println("|                      Loja - \"El placer de comprar\"                   |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.printf("| FACTURA N°: %-60s|\n", factura.getCodigoFactura());
        System.out.printf("| Fecha Emisión: %-55s|\n", factura.getFecha());
        System.out.println("+----------------------------------------------------------------------+");

        if (factura.getDatosCliente() != null) {
            System.out.printf("| Cliente: %-60s|\n", factura.getDatosCliente().getNombre());
            System.out.printf("| CI/Número: %-58s|\n", factura.getDatosCliente().getId());
            System.out.printf("| Correo:   %-58s|\n", factura.getDatosCliente().getEmail());
            System.out.printf("| Teléfono: %-58s|\n", factura.getDatosCliente().getCelular());
            System.out.printf("| Dirección:%-58s|\n", factura.getDatosCliente().getUbicacion());
        } else {
            System.out.printf("| Cliente: %-60s|\n", "No asignado");
        }

        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("| Detalles de la compra:                                               |");
        System.out.println("+------------+------------------------------+------+--------+--------+");
        System.out.println("| ProductoID | Nombre del Producto           | Cant | Precio | Total  |");
        System.out.println("+------------+------------------------------+------+--------+--------+");

        for (DetallesFactura df : factura.getDetalles()) {
            String etiqueta = df.getProducto().getEtiqueta() != null ? df.getProducto().getEtiqueta() : "";
            System.out.printf("| %-10s | %-28s | %4d | %6.2f | %6.2f |\n",
                    df.getProducto().getCodigoProducto(),
                    df.getProducto().getDescripcion() + (etiqueta.isBlank() ? "" : " [" + etiqueta + "]"),
                    df.getUnidadesVendidas(),
                    df.getPrecioIndividual(),
                    df.getSubtotal());
        }

        System.out.println("+----------------------------------------------------------------------+");
        System.out.printf("| Subtotal: %-58.2f |\n", factura.getMontoBase());
        System.out.printf("| IVA:      %-58.2f |\n", factura.getImpuesto());
        System.out.printf("| Total:    %-58.2f |\n", factura.getMontoFinal());
        System.out.println("+----------------------------------------------------------------------+");
    }

    private void mostrarEstadisticas() {
        ArrayList<String[]> stats = controller.obtenerEstadisticas();
        System.out.println("=== Estadísticas de Ventas ===");
        System.out.printf("%-10s %-25s %-15s %-10s %-12s%n", "CÓDIGO", "DESCRIPCIÓN", "CATEGORÍA", "CANTIDAD", "TOTAL $");
        System.out.println("------------------------------------------------------------------------------------");

        for (String[] fila : stats) {
            System.out.printf("%-10s %-25s %-15s %-10s %-12s%n",
                    fila[0], fila[1], fila[2], fila[3], fila[4]);
        }

        System.out.println("\n--- Análisis General ---");

        String[] productoMasVendido = controller.obtenerProductoMasVendido();
        if (productoMasVendido != null) {
            System.out.printf("Producto más vendido: %s (Código: %s) - %s unidades vendidas%n",
                    productoMasVendido[1], productoMasVendido[0], productoMasVendido[2]);
        }

        String[] categoriaMasVendida = controller.obtenerCategoriaMasVendida();
        if (categoriaMasVendida != null) {
            System.out.printf("Categoría más vendida: %s - %s unidades vendidas%n",
                    categoriaMasVendida[0], categoriaMasVendida[1]);
        }
    }
    
    private void mostrarEstadisticasDia() {
        ArrayList<String[]> stats = controller.obtenerEstadisticasDelDia();
        System.out.println("=== Estadísticas de Ventas ===");
        System.out.printf("%-10s %-25s %-10s %-12s%n", "CÓDIGO", "DESCRIPCIÓN", "CANTIDAD", "TOTAL $");
        System.out.println("------------------------------------------------------------------------------------");

        for (String[] fila : stats) {
            System.out.printf("%-10s %-25s %-10s %-12s%n",
                    fila[0], fila[1], fila[2], fila[3]);
        }

        System.out.println("\n--- Análisis General ---");

        RegistroProducto productoMasVendido = controller.obtenerProductoMasVendidoDelDia();
        if (productoMasVendido != null) {
            System.out.printf("Producto más vendido: %s (Código: %s) - %s unidades vendidas%n",
                    productoMasVendido.nombreProducto, productoMasVendido.codigoProducto, productoMasVendido.unidadesVendidas);
        }

        RegistroCategoria categoriaMasVendida = controller.obtenerCategoriaMasVendidoDelDia();
        if (categoriaMasVendida != null) {
            System.out.printf("Categoría más vendida: %s - %s unidades vendidas - Total Generado:%.2f%n",
                    categoriaMasVendida.nombreCategoria, categoriaMasVendida.unidadesVendidas, categoriaMasVendida.totalGenerado);
        }
    }

    
    public void salir() {
        System.out.println("Saliendo del sistema...");
        mostrarEstadisticasDia();
        System.out.println("\nGracias por usar el sistema.");
    }
}