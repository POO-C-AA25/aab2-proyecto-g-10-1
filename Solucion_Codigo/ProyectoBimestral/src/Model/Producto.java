package Model;

import java.util.UUID;

/**
 * Clase abstracta que representa un producto general del SuperMaxi.
 * Cada producto tiene código, descripción, categoría (como String), precio base, stock y fecha de vencimiento aleatoria.
 */
public abstract class Producto {
    private String codigoProducto;   // Código único para identificar el producto
    private String descripcion;      // Descripción del producto
    private String categoria;        // Categoría del producto (Vivienda, Educación, etc.) como String
    private double precioBase;       // Precio base sin descuentos
    private int unidadesDisponibles; // Stock actual    
    private String etiqueta;         // Etiqueta de producto Local o Importado
    
    public Producto() {
        // Constructor vacío
    }
    
    // Constructor para productos, genera fecha de vencimiento aleatoria entre hoy y 90 días después
    public Producto(String descripcion, String categoria, double precioBase, int unidadesDisponibles, String etiqueta) {
        this.codigoProducto = UUID.randomUUID().toString().substring(0, 8);
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precioBase = precioBase;
        this.unidadesDisponibles = unidadesDisponibles;
        this.etiqueta = etiqueta;
    }

    // Método abstracto para obtener información adicional de cada tipo de producto
    public abstract String obtenerInformacionExtra();

    // Método abstracto para calcular precio final con descuentos o recargos según el tipo
    public abstract double calcularPrecioFinal();
    
    // Método abstracto para saber que tipo de producto es
    public abstract String getTipoProducto();

    // Reduce stock si hay suficientes unidades
    public boolean reducirInventario(int cantidad) {
        if (cantidad <= unidadesDisponibles) {
            unidadesDisponibles -= cantidad;
            return true;
        }
        return false;
    }

    // Añade unidades al stock
    public void reponerInventario(int cantidad) {
        unidadesDisponibles += cantidad;
    }

    // Getters y setters
    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }
    
    public String getEtiqueta() {
        return etiqueta;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
}