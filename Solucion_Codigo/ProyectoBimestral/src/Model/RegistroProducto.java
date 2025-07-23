package Model;


/**
 * Representa un registro estadístico de ventas por producto.
 * Clase auxiliar usada por EstadisticaVentas.
 */
public class RegistroProducto {

    public String codigoProducto;
    public String nombreProducto;
    public int unidadesVendidas;
    public double totalGenerado;

    public RegistroProducto(String codigoProducto, String nombreProducto) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.unidadesVendidas = 0;
        this.totalGenerado = 0.0;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public double getTotalGenerado() {
        return totalGenerado;
    }
    
    
}
