package Model;

/**
 * @author Mateo Gonzáles y Mateo Rivera
 */
public class ClienteEmpresa extends Cliente {
    
    private String nombreContacto; // Nombre de la persona de contacto en la empresa

    public ClienteEmpresa() {
        // Constructor vacío
    }

    public ClienteEmpresa(String id, String nombre, String email, String celular, String ubicacion, String nombreContacto) {
        super(id, nombre, email, celular, ubicacion);
        this.nombreContacto = nombreContacto;
    }

    @Override
    public String getTipoCliente() {
        return "EMPRESA";
    }
}