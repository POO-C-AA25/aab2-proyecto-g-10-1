package Model;

/**
 * @author Mateo Rivera
 */
public class ConsumidorFinal extends Cliente {

    private String claveGenerica; // Clave o código genérico para consumidor final

    public ConsumidorFinal() {
        // Datos genéricos por defecto
        super("9999999999", "Consumidor Final", "N/A", "N/A", "No especificado");
        this.claveGenerica = "CF-DEFAULT";
    }
    
    @Override
    public String getTipoCliente() {
        return "CONSUMIDOR_FINAL";
    }
}