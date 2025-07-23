package Model;


public class DetallesFactura {
    private Producto producto;        // Producto involucrado en la compra
    private int unidadesVendidas;     // Cantidad comprada
    private double precioIndividual;  // Precio de venta por unidad
    private double valorSubtotal;     // Precio total por esta línea (cant * precio)

    // Constructor vacío
    public DetallesFactura() {
        
    }

    public DetallesFactura(Producto producto, int unidadesVendidas, double precioIndividual) {
        this.producto = producto;
        this.unidadesVendidas = unidadesVendidas;
        this.precioIndividual = precioIndividual;
        calcularSubtotal();
    }

    // Calcula el subtotal de esta línea
    public void calcularSubtotal() {
        this.valorSubtotal = unidadesVendidas * precioIndividual;
    }
    
    public Producto getProducto() {
        return producto;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public double getPrecioIndividual() {
        return precioIndividual;
    }

    public double getSubtotal() {
        return valorSubtotal;
    }
}