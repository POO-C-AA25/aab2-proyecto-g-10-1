package Model;

import java.util.UUID;

/**
 * @author Mateo Rivera
 */
public class ConsumidorFinal extends Cliente {

    private String claveGenerica; // Clave o código genérico para consumidor final

    public ConsumidorFinal() {
        super(String.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits())).substring(0, 10), 
                "Consumidor Final", "N/A", "N/A", "No especificado");
        this.claveGenerica = "CF-DEFAULT";
    }
    
    @Override
    public String getTipoCliente() {
        return "CONSUMIDOR_FINAL";
    }
}