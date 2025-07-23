package Model;

/**
 * Producto importado que tiene recargo de arancel y no aplica descuentos.
 * @author Mateo Gonzáles y Mateo Rivera
 */
public class ProductoImportado extends Producto{
    private double porcentajeArancel = 0.15;
    private String paisOrigen;
    
    public ProductoImportado(){
        // Constructor vacío
    }
    
    public ProductoImportado(String descripcion, String categoria, double precioBase, int unidadesDisponibles, String etiqueta, String paisOrigen) {
        super(descripcion, categoria, precioBase, unidadesDisponibles, etiqueta);
        this.paisOrigen = paisOrigen;
    }
    
    @Override
    public String obtenerInformacionExtra() {
        return String.format("Importado - Recargo %.2f%% arancel - País de Origen: %s", porcentajeArancel * 100, paisOrigen);
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase() * (1 + porcentajeArancel);
    }
    
    @Override
    public String getTipoProducto() {
        return "Importado";
    }
    
    public String getPaisOrigen() {
        return paisOrigen;
    }   
    
    public double getArancel() {
        return porcentajeArancel;
    }     
}
