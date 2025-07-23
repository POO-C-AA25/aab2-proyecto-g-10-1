package Model;

/**
 * @author Mateo Gonzáles y Mateo Rivera
 */
public class ClientePersonaNatural extends Cliente {

    private String estadoCivil;  // Estado civil del cliente

    public ClientePersonaNatural() {
        // Constructor vacío
    }

    public ClientePersonaNatural(String id, String nombre, String email, String celular, String ubicacion, String estadoCivil) {
        super(id, nombre, email, celular, ubicacion);
        this.estadoCivil = estadoCivil;
    }
    
    @Override
    public String getTipoCliente() {
        return "NATURAL";
    }
}