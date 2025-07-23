package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Factura{
    private String codigoFactura;
    private LocalDate fecha;
    private Cliente datosCliente;
    private ArrayList<DetallesFactura> detalles;
    private double montoBase;
    private double impuesto;
    private double montoFinal;

    public Factura() {
        this.codigoFactura = UUID.randomUUID().toString().substring(0, 8);
        this.fecha = LocalDate.now();
        this.detalles = new ArrayList<>();
    }

    public void asignarCliente(Cliente cliente) {
        this.datosCliente = cliente;
    }

    public void añadirDetalle(DetallesFactura detalle) {
        detalles.add(detalle);
    }

    // Getters y Setters para establecer y recuperar
    
    public String getCodigoFactura() {
        return codigoFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Cliente getDatosCliente() {
        return datosCliente;
    }

    public ArrayList<DetallesFactura> getDetalles() {
        return detalles;
    }

    public double getMontoBase() {
        return montoBase;
    }

    public double getImpuesto() {
        return impuesto;
    }
    
    public double getMontoFinal() {
        return montoFinal;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }
    
    public void setMontoBase(double montoBase) {
        this.montoBase = montoBase;
    }

    public void setMontoFinal(double montoFinal) {
        this.montoFinal = montoFinal;
    }

}