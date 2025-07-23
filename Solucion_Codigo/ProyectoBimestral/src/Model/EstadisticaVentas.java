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
}