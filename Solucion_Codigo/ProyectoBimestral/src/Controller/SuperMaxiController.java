package controller;

import Model.*;
import java.util.ArrayList;

public class SuperMaxiController {

    private DBMananger db;
    private EstadisticaVentas estadisticas;

    public SuperMaxiController(DBMananger db) {
        this.db = db;
        this.estadisticas = new EstadisticaVentas();
    }

    // --------------------- CLIENTES ---------------------

    public boolean agregarCliente(Cliente cliente) {
        if (cliente == null) return false;
        return db.insertarCliente(cliente);
    }

   
    // --------------------- PRODUCTOS ---------------------

    public boolean agregarProducto(Producto producto) {
        if (producto == null) return false;
        return db.insertarProducto(producto);
    }

    public ArrayList<Producto> obtenerProductos() {
        return db.obtenerProductos();
    }

    public Producto buscarProductoPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) return null;
        for (Producto p : obtenerProductos()) {
            if (p.getCodigoProducto().equalsIgnoreCase(codigo.trim())) {
                return p;
            }
        }
        return null;
    }   
    
    public String[] obtenerProductoMasVendido() {
        return db.obtenerProductoMasVendido();
    }

    public String[] obtenerCategoriaMasVendida() {
        return db.obtenerCategoriaMasVendida();
    }


    // --------------------- FACTURAS ---------------------

    public Factura crearFactura(Cliente cliente) {
        if (cliente == null) return null;
        Factura factura = new Factura();
        factura.asignarCliente(cliente);
        return factura;
    }

    public boolean agregarDetalleAFactura(Factura factura, String codigoProducto, int cantidad) throws Exception {
        if (factura == null) throw new Exception("Factura inválida");
        if (cantidad <= 0) throw new Exception("Cantidad debe ser mayor que cero");
        Producto producto = buscarProductoPorCodigo(codigoProducto);
        if (producto == null) throw new Exception("Producto no encontrado");
        if (producto.getUnidadesDisponibles() < cantidad) throw new Exception("Stock insuficiente");

        double precioFinal = producto.calcularPrecioFinal();
        DetallesFactura detalle = new DetallesFactura(producto, cantidad, precioFinal);
        factura.añadirDetalle(detalle);

        producto.reducirInventario(cantidad);
        db.actualizarStockProducto(producto.getCodigoProducto(), producto.getUnidadesDisponibles());

        actualizarEstadisticasConDetalle(detalle);

        return true;
    }
    
    public boolean eliminarDetalleDeFactura(Factura factura, String codigoProducto) {
        if (factura == null || codigoProducto == null || codigoProducto.isBlank()) return false;

        DetallesFactura detalleAEliminar = null;
        for (DetallesFactura detalle : factura.getDetalles()) {
            if (detalle.getProducto().getCodigoProducto().equalsIgnoreCase(codigoProducto.trim())) {
                detalleAEliminar = detalle;
                break;
            }
        }
        if (detalleAEliminar == null) return false;

        Producto producto = buscarProductoPorCodigo(codigoProducto);
        if (producto != null) {
            producto.reponerInventario(detalleAEliminar.getUnidadesVendidas()); 
            db.actualizarStockProducto(producto.getCodigoProducto(), producto.getUnidadesDisponibles());
        }

        factura.getDetalles().remove(detalleAEliminar);

        return true;
    }

    private void actualizarEstadisticasConDetalle(DetallesFactura detalle) {
        String codigo = detalle.getProducto().getCodigoProducto();
        String nombre = detalle.getProducto().getDescripcion();
        String categoria = detalle.getProducto().getCategoria();
        int cantidad = detalle.getUnidadesVendidas();
        double subtotal = detalle.getSubtotal();

        RegistroProducto prod = buscarRegistroProducto(codigo);
        if (prod == null) {
            prod = new RegistroProducto(codigo, nombre);
            estadisticas.getVentasPorProductoOrdenadas().add(prod);
        }
        prod.unidadesVendidas += cantidad;
        prod.totalGenerado += subtotal;

        RegistroCategoria cat = buscarRegistroCategoria(categoria);
        if (cat == null) {
            cat = new RegistroCategoria(categoria);
            estadisticas.getVentasPorCategoriaOrdenadas().add(cat);
        }
        cat.unidadesVendidas += cantidad;
        cat.totalGenerado += subtotal;
        
        db.actualizarEstadisticas(detalle);
    }
    
    public ArrayList<String[]> obtenerEstadisticas() {
        return db.obtenerEstadisticas();
    }


    private RegistroProducto buscarRegistroProducto(String codigo) {
        for (RegistroProducto rp : estadisticas.getVentasPorProductoOrdenadas()) {
            if (rp.codigoProducto.equalsIgnoreCase(codigo)) {
                return rp;
            }
        }
        return null;
    }

    private RegistroCategoria buscarRegistroCategoria(String nombreCategoria) {
        for (RegistroCategoria rc : estadisticas.getVentasPorCategoriaOrdenadas()) {
            if (rc.nombreCategoria.equalsIgnoreCase(nombreCategoria)) {
                return rc;
            }
        }
        return null;
    }

    public void calcularTotalesFactura(Factura factura) {
        if (factura == null) return;

        double base = 0.0;
        Deducibles deducibles = new Deducibles();
        double montoDeduccionTotal = 0.0;

        for (DetallesFactura d : factura.getDetalles()) {
            base += d.getSubtotal();
            montoDeduccionTotal += deducibles.calcularMontoDeducible(
                d.getProducto().getCategoria(),
                factura.getDatosCliente().getTipoCliente(),
                d.getSubtotal()
            );
        }

        factura.setMontoBase(base);
        factura.setImpuesto(montoDeduccionTotal);
        factura.setMontoFinal(base - montoDeduccionTotal);
    }

    public boolean guardarFactura(Factura factura) {
        if (factura == null) return false;
        if (!db.insertarFactura(factura)) return false;

        for (DetallesFactura d : factura.getDetalles()) {
            if (!db.insertarDetalleFactura(d, factura.getCodigoFactura())) return false;
        }
        return true;
    }

    // --------------------- ESTADÍSTICAS ---------------------

    public ArrayList<RegistroProducto> obtenerVentasPorProducto() {
        return estadisticas.getVentasPorProductoOrdenadas();
    }

    public ArrayList<RegistroCategoria> obtenerVentasPorCategoria() {
        return estadisticas.getVentasPorCategoriaOrdenadas();
    }
}