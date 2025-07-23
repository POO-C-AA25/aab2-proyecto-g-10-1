package Model;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Clase que almacena estadísticas de ventas por producto y por categoría.
 * Ya no contiene lógica de cálculo ni persistencia; se usa solo como estructura de datos.
 */
public class EstadisticaVentas {

    private ArrayList<RegistroProducto> ventasPorProducto;
    private ArrayList<RegistroCategoria> ventasPorCategoria;
    
    // Listas temporales solo para ventas del día actual (sesión)
    private ArrayList<RegistroProducto> ventasPorProductoDelDia = new ArrayList<>();
    private ArrayList<RegistroCategoria> ventasPorCategoriaDelDia = new ArrayList<>();

    public EstadisticaVentas() {
        ventasPorProducto = new ArrayList<>();
        ventasPorCategoria = new ArrayList<>();
    }

    public ArrayList<RegistroProducto> getVentasPorProductoOrdenadas() {
        ventasPorProducto.sort(Comparator.comparingInt(rp -> -rp.unidadesVendidas));
        return ventasPorProducto;
    }

    public ArrayList<RegistroCategoria> getVentasPorCategoriaOrdenadas() {
        ventasPorCategoria.sort(Comparator.comparingInt(rc -> -rc.unidadesVendidas));
        return ventasPorCategoria;
    }
    
    public ArrayList<RegistroProducto> getVentasPorProductoDiaOrdenadas() {
        ventasPorProductoDelDia.sort(Comparator.comparingInt(rp -> -rp.unidadesVendidas));
        return ventasPorProductoDelDia;
    }

    public ArrayList<RegistroCategoria> getVentasPorCategoriaDiaOrdenadas() {
        ventasPorCategoriaDelDia.sort(Comparator.comparingInt(rc -> -rc.unidadesVendidas));
        return ventasPorCategoriaDelDia;
    }
}