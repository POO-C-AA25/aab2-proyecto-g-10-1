package Model;

/**
 * Producto local que aplica descuento promocional especial.
 * @author Usuario
 */
public class ProductoLocal extends Producto{
    // Porcentaje de descuento promocional
    private double porcentajeDescuento = 0.05;

    public ProductoLocal() {
        // Constructor vacío
    }
    
    public ProductoLocal(String descripcion, String categoria, double precioBase, int unidadesDisponibles, String etiqueta) {
        super(descripcion, categoria, precioBase, unidadesDisponibles, etiqueta);
    }  
    
    @Override
    public String obtenerInformacionExtra() {
        return String.format("Producto local con %.2f%% descuento", porcentajeDescuento * 100);
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase() * (1 - porcentajeDescuento);
    }
    
    @Override
    public String getTipoProducto() {
        return "Local";
    }
}
