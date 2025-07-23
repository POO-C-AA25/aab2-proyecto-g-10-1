package Model;


/**
 * Representa los datos personales y de contacto de un cliente
 * @author Mateo Gonzáles y Mateo Rivera
 */
public class Cliente {
    private String id;         // Cédula o RUC del cliente
    private String nombre;     // Nombre completo
    private String email;      // Dirección de correo electrónico
    private String celular;    // Número telefónico
    private String ubicacion;  // Dirección domiciliaria

    // Constructor vacío
    public Cliente() {
    }

    // Constructor completo
    public Cliente(String id, String nombre, String email, String celular, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.celular = celular;
        this.ubicacion = ubicacion;
    }

    /**
     * Devuelve el tipo de cliente. Se sobreescribe en subclases.
     */
    public String getTipoCliente() {
        return "GENERAL";
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getCelular() {
        return celular;
    }

    public String getUbicacion() {
        return ubicacion;
    }
}
