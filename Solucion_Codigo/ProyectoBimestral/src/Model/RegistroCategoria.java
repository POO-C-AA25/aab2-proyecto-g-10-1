package Model;

/**
 * Representa un registro estadístico de ventas por categoría de productos.
 * @author Mateo Gonzáles y Mateo Rivera
 */
public class RegistroCategoria {
    
    public String nombreCategoria;
    public int unidadesVendidas;
    public double totalGenerado;

    public RegistroCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
        this.unidadesVendidas = 0;
        this.totalGenerado = 0.0;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public double getTotalGenerado() {
        return totalGenerado;
    }
    
    
}
