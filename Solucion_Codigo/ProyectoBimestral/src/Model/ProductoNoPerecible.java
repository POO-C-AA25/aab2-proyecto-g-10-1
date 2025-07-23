package Model;

/**
 * Producto que no tiene fecha de vencimiento y aplica descuento por stock alto.
 * Posee como atributo adicional el material del que está hecho.
 */
public class ProductoNoPerecible extends Producto {
    private String material;

    public ProductoNoPerecible() {
        // Constructor vacío
    }

    public ProductoNoPerecible(String descripcion, String categoria, double precioBase, int unidadesDisponibles, String etiqueta, String material) {
        super(descripcion, categoria, precioBase, unidadesDisponibles, etiqueta);
        this.material = material;
    }    
        
    @Override
    public String obtenerInformacionExtra() {
        if (getUnidadesDisponibles() > 50) {
            return "No perecible - Material: " + material + " - Descuento por stock alto (10%)";
        }
        return "No perecible - Material: " + material;
    }

    @Override
    public double calcularPrecioFinal() {
        if (getUnidadesDisponibles() > 50) {
            return getPrecioBase() * 0.9;  // 10% descuento por stock alto
        }
        return getPrecioBase();
    }
    
    @Override
    public String getTipoProducto() {
        return "NoPerecible";
    }
    
    public String getMaterial() {
        return material;
    }
}
