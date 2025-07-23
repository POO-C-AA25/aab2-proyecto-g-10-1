package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DBMananger {
    private String URL = "jdbc:sqlite:db/supermaxiLoja.db";
    private Connection conn;

    // Establecer conexión
    public void conectar() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos: " + e.getMessage(), e);
        }
    }

    // Cerrar conexión
    public void cerrar() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexión: " + e.getMessage(), e);
        }
    }


    // Crear tablas si no existen
    public void crearTablas() {
        try {
            conectar();
            Statement st = conn.createStatement();

            // Tabla Producto
            String sqlProducto = "CREATE TABLE IF NOT EXISTS Producto (" +//metodo predertimnado por sql
                    "codigo TEXT PRIMARY KEY, " +
                    "descripcion TEXT, " +
                    "categoria TEXT, " +
                    "precioBase REAL, " +
                    "unidadesDisponibles INTEGER, " +//integer = entero
                    "etiqueta TEXT, " +
                    "tipoProducto TEXT, " +           // Local, Importado, Perecible, NoPerecible
                    "paisOrigen TEXT, " +             // si es Importado
                    "arancel REAL, " +  // decimal              // si es Importado
                    "fechaVencimiento TEXT, " +       // si es Perecible
                    "material TEXT" +                 // si es NoPerecible
                    ")";
            st.execute(sqlProducto);//para ejecutar

            // Tabla Cliente
            String sqlCliente = "CREATE TABLE IF NOT EXISTS Cliente (" +
                    "id TEXT PRIMARY KEY, " +
                    "nombre TEXT, " +
                    "email TEXT, " +
                    "celular TEXT, " +
                    "ubicacion TEXT, " +
                    "tipoCliente TEXT" +
                    ")";
            st.execute(sqlCliente);//para ejecutar 

            // Tabla Factura
            String sqlFactura = "CREATE TABLE IF NOT EXISTS Factura (" +
                    "codigoFactura TEXT PRIMARY KEY, " +
                    "fecha TEXT, " +
                    "idCliente TEXT, " +
                    "montoBase REAL, " +
                    "impuesto REAL, " +
                    "montoFinal REAL, " +
                    "FOREIGN KEY(idCliente) REFERENCES Cliente(id)" +
                    ")";
            st.execute(sqlFactura);

            // Tabla DetallesFactura
            String sqlDetalle = "CREATE TABLE IF NOT EXISTS DetallesFactura (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "codigoFactura TEXT, " +
                    "codigoProducto TEXT, " +
                    "unidadesVendidas INTEGER, " +
                    "precioIndividual REAL, " +
                    "valorSubtotal REAL, " +
                    "FOREIGN KEY(codigoFactura) REFERENCES Factura(codigoFactura), " +
                    "FOREIGN KEY(codigoProducto) REFERENCES Producto(codigo)" +
                    ")";
            st.execute(sqlDetalle);
            
            // Tabla EstadísticasVentas
            String sqlEstadistica = "CREATE TABLE IF NOT EXISTS EstadisticaVentas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "codigoProducto TEXT, " +             // Referencia al producto
                    "descripcion TEXT, " +                // Nombre del producto
                    "categoria TEXT, " +                  // Categoría del producto
                    "cantidadVendida INTEGER, " +         // Total acumulado vendido
                    "montoTotal REAL, " +                 // Total facturado (cantidad * precio final)
                    "FOREIGN KEY(codigoProducto) REFERENCES Producto(codigo)" +
                    ")";
            st.execute(sqlEstadistica);

            st.close();//se cierra el statement lo que preparamos
            cerrar();//llama al metodo cerrar para cerrar todo
            System.out.println("Tablas creadas correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al crear tablas: " + e.getMessage());
        }
    }


    // Insertar Producto
    public boolean insertarProducto(Producto p) {//paar devolver si se igreso o no se infreso v o f el cliente
        try {
            conectar();
            String sql = "INSERT INTO Producto(codigo, descripcion, categoria, precioBase, unidadesDisponibles, etiqueta, tipoProducto, paisOrigen, arancel, fechaVencimiento, material) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);//preparar lo que vamos a hcer
            ps.setString(1, p.getCodigoProducto());
            ps.setString(2, p.getDescripcion());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecioBase());
            ps.setInt(5, p.getUnidadesDisponibles());
            ps.setString(6, p.getEtiqueta());
            ps.setString(7, p.getTipoProducto());  // Nuevo método en clase padre o en subclases

            switch (p.getTipoProducto()) {
                case "Importado" -> {
                    ProductoImportado imp = (ProductoImportado) p;
                    ps.setString(8, imp.getPaisOrigen());
                    ps.setDouble(9, imp.getArancel());
                    ps.setNull(10, Types.DATE);       // Fecha de vencimiento nula
                    ps.setNull(11, Types.VARCHAR);    // Material nulo
                }
                case "Perecible" -> {
                    ProductoPerecible per = (ProductoPerecible) p;
                    ps.setNull(8, Types.VARCHAR);                         // Sin país origen
                    ps.setNull(9, Types.DOUBLE);                          // Sin arancel
                    ps.setString(10, per.getVencimiento().toString());    // Fecha como DATE
                    ps.setNull(11, Types.VARCHAR);                        // Sin material
                }
                case "NoPerecible" -> {
                    ProductoNoPerecible np = (ProductoNoPerecible) p;
                    ps.setNull(8, Types.VARCHAR);                         // Sin país origen
                    ps.setNull(9, Types.DOUBLE);                          // Sin arancel
                    ps.setNull(10, Types.DATE);                           // Sin vencimiento
                    ps.setString(11, np.getMaterial());                   // Material
                }
                default -> { // Producto Local
                    ps.setNull(8, Types.VARCHAR);
                    ps.setNull(9, Types.DOUBLE);
                    ps.setNull(10, Types.DATE);
                    ps.setNull(11, Types.VARCHAR);
                }
            }
            ps.executeUpdate();
            ps.close();
            cerrar();
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertarProducto: " + e.getMessage());
            return false;
        }
    }

    // Consultar lista de Productos
    public ArrayList<Producto> obtenerProductos() {
        ArrayList<Producto> productos = new ArrayList<>();

        try {
            conectar();
            String sql = "SELECT * FROM Producto";// sirve para seleccionar o leer toda la tabla
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();//esto queire decri que vamos a genrrar un resultado 

            while (rs.next()) {
                String tipo = rs.getString("tipoProducto");
                String codigo = rs.getString("codigo");
                String descripcion = rs.getString("descripcion");
                String categoria = rs.getString("categoria");
                double precio = rs.getDouble("precioBase");
                int stock = rs.getInt("unidadesDisponibles");
                String etiqueta = rs.getString("etiqueta");

                Producto producto = null;

                switch (tipo) {
                    case "Importado" -> {
                        String pais = rs.getString("paisOrigen");
                        double arancel = rs.getDouble("arancel");

                        ProductoImportado pi = new ProductoImportado(descripcion, categoria, precio, stock, etiqueta, pais);
                        pi.setCodigoProducto(codigo);
                        producto = pi;
                    }
                    case "Perecible" -> {
                        String fechaStr = rs.getString("fechaVencimiento");
                        LocalDate fechaVencimiento = (fechaStr != null) ? LocalDate.parse(fechaStr) : null;

                        ProductoPerecible pp = new ProductoPerecible(descripcion, categoria, precio, stock, etiqueta, fechaVencimiento);
                        pp.setCodigoProducto(codigo);
                        producto = pp;
                    }
                    case "NoPerecible" -> {
                        String material = rs.getString("material");

                        ProductoNoPerecible pnp = new ProductoNoPerecible(descripcion, categoria, precio, stock, etiqueta, material);
                        pnp.setCodigoProducto(codigo);
                        producto = pnp;
                    }
                    default -> { // Local u otro genérico
                        ProductoLocal pl = new ProductoLocal(descripcion, categoria, precio, stock, etiqueta);
                        pl.setCodigoProducto(codigo);
                        producto = pl;
                    }
                }

                if (producto != null) {
                    productos.add(producto);
                }
            }

            rs.close();
            ps.close();
            cerrar();
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }

        return productos;
    }
    
    public void actualizarStockProducto(String codigoProducto, int nuevoStock) {
        try {
            conectar();
            String sql = "UPDATE Producto SET unidadesDisponibles = ? WHERE codigo = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, nuevoStock);
            ps.setString(2, codigoProducto);
            ps.executeUpdate();
            ps.close();
            cerrar();
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando stock del producto", e);
        }
    }



    // Insertar Cliente
    public boolean insertarCliente(Cliente c) {
        try {
            conectar();
            String sql = "INSERT INTO Cliente(id, nombre, email, celular, ubicacion, tipoCliente) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getId());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getCelular());
            ps.setString(5, c.getUbicacion());
            ps.setString(6, c.getTipoCliente());
            ps.executeUpdate();
            ps.close();
            cerrar();
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertarCliente: " + e.getMessage());
            return false;
        }
    }

    

    // Insertar Factura
    public boolean insertarFactura(Factura f) {
        try {
            conectar();
            String sql = "INSERT INTO Factura(codigoFactura, fecha, idCliente, montoBase, impuesto, montoFinal) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, f.getCodigoFactura());
            ps.setString(2, f.getFecha().toString());  // ISO-8601
            ps.setString(3, f.getDatosCliente().getId());
            ps.setDouble(4, f.getMontoBase());
            ps.setDouble(5, f.getImpuesto());
            ps.setDouble(6, f.getMontoFinal());
            ps.executeUpdate();
            ps.close();
            cerrar();
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertarFactura: " + e.getMessage());
            return false;
        }
    }

    
    // Insertar DetallesFactura
    public boolean insertarDetalleFactura(DetallesFactura d, String codigoFactura) {
        try {
            conectar();
            String sql = "INSERT INTO DetallesFactura(codigoFactura, codigoProducto, unidadesVendidas, precioIndividual, valorSubtotal) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, codigoFactura);
            ps.setString(2, d.getProducto().getCodigoProducto());
            ps.setInt(3, d.getUnidadesVendidas());
            ps.setDouble(4, d.getPrecioIndividual());
            ps.setDouble(5, d.getSubtotal());
            ps.executeUpdate();
            ps.close();
            cerrar();
            return true;
        } catch (SQLException e) {
            System.err.println("Error insertarDetalleFactura: " + e.getMessage());
            return false;
        }
    }

    
    public void actualizarEstadisticas(DetallesFactura detalle) {
        try {
            conectar();
            String codigo = detalle.getProducto().getCodigoProducto();
            String descripcion = detalle.getProducto().getDescripcion();
            String categoria = detalle.getProducto().getCategoria();
            int cantidad = detalle.getUnidadesVendidas();
            double subtotal = detalle.getSubtotal();

            // Verificamos si ya existe un registro de este producto
            String sqlSelect = "SELECT cantidadVendida, montoTotal FROM EstadisticaVentas WHERE codigoProducto = ?";
            PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setString(1, codigo);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                // Ya existe: actualizamos los valores
                int nuevaCantidad = rs.getInt("cantidadVendida") + cantidad;
                double nuevoMonto = rs.getDouble("montoTotal") + subtotal;
                String sqlUpdate = "UPDATE EstadisticaVentas SET cantidadVendida = ?, montoTotal = ? WHERE codigoProducto = ?";
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, nuevaCantidad);
                psUpdate.setDouble(2, nuevoMonto);
                psUpdate.setString(3, codigo);
                psUpdate.executeUpdate();
                psUpdate.close();
            } else {
                // No existe: insertamos un nuevo registro
                String sqlInsert = "INSERT INTO EstadisticaVentas (codigoProducto, descripcion, categoria, cantidadVendida, montoTotal) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
                psInsert.setString(1, codigo);
                psInsert.setString(2, descripcion);
                psInsert.setString(3, categoria);
                psInsert.setInt(4, cantidad);
                psInsert.setDouble(5, subtotal);
                psInsert.executeUpdate();
                psInsert.close();
            }
            rs.close();
            psSelect.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estadísticas: " + e.getMessage(), e);
        }
    }

    public ArrayList<String[]> obtenerEstadisticas() {
        ArrayList<String[]> lista = new ArrayList<>();
        try {
            conectar();
            String sql = "SELECT codigoProducto, descripcion, categoria, cantidadVendida, montoTotal FROM EstadisticaVentas";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String[] fila = {
                    rs.getString("codigoProducto"),
                    rs.getString("descripcion"),
                    rs.getString("categoria"),
                    String.valueOf(rs.getInt("cantidadVendida")),
                    String.format("%.2f", rs.getDouble("montoTotal"))
                };
                lista.add(fila);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener estadísticas: " + e.getMessage(), e);
        } 
        return lista;
    }
    
    public String[] obtenerProductoMasVendido() {
        try {
            conectar();
            String sql = """
                SELECT codigoProducto, descripcion, cantidadVendida 
                FROM EstadisticaVentas 
                ORDER BY cantidadVendida DESC 
                LIMIT 1
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String[] resultado = {
                    rs.getString("codigoProducto"),
                    rs.getString("descripcion"),
                    String.valueOf(rs.getInt("cantidadVendida"))
                };
                rs.close();
                ps.close();
                return resultado;
            }

            rs.close();
            ps.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener producto más vendido: " + e.getMessage(), e);
        } 
    }
    
    public String[] obtenerCategoriaMasVendida() {
        try {
            conectar();
            String sql = """
                SELECT categoria, SUM(cantidadVendida) as totalVendida 
                FROM EstadisticaVentas 
                GROUP BY categoria 
                ORDER BY totalVendida DESC 
                LIMIT 1
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String[] resultado = {
                    rs.getString("categoria"),
                    String.valueOf(rs.getInt("totalVendida"))
                };
                rs.close();
                ps.close();
                return resultado;
            }

            rs.close();
            ps.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener categoría más vendida: " + e.getMessage(), e);
        } 
    }
}
