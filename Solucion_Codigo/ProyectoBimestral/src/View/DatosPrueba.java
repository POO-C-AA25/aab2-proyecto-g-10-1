package View;


import Model.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatosPrueba {
    public static ArrayList<Producto> obtenerProductosPrueba() {
        ArrayList<Producto> lista = new ArrayList<>();

        // Productos Locales
        lista.add(new ProductoLocal("Camisa Algodón", "Vestimenta", 15.99, 100, "Local"));
        lista.add(new ProductoLocal("Pantalón Jeans", "Vestimenta", 25.50, 80, "Local"));
        lista.add(new ProductoLocal("Colchón Simple", "Vivienda", 120.00, 20, "Local"));
        lista.add(new ProductoLocal("Mesa de Centro", "Vivienda", 45.00, 15, "Local"));
        lista.add(new ProductoLocal("Cepillo de Dientes", "Salud", 3.50, 150, "Local"));
        lista.add(new ProductoLocal("Jabón de Manos", "Salud", 1.20, 200, "Local"));
        lista.add(new ProductoLocal("Leche Entera", "Alimentación", 1.00, 100, "Local"));
        lista.add(new ProductoLocal("Pan Integral", "Alimentación", 1.50, 90, "Local"));

        // Productos Importados
        ProductoImportado vino = new ProductoImportado("Vino Tinto Reserva", "Alimentación", 18.00, 50, "Importado", "Chile");
        lista.add(vino);

        ProductoImportado perfume = new ProductoImportado("Perfume Mujer", "Salud", 40.00, 30, "Importado", "Francia");
        lista.add(perfume);

        ProductoImportado laptop = new ProductoImportado("Laptop Gamer", "Tecnología", 1200.00, 10, "Importado", "Estados Unidos");
        lista.add(laptop);

        ProductoImportado camisetaMarca = new ProductoImportado("Camiseta Deportiva", "Vestimenta", 35.00, 60, "Importado", "Italia");
        lista.add(camisetaMarca);

        // Productos Perecibles
        lista.add(new ProductoPerecible("Yogurt Natural", "Alimentación", 1.50, 100, "Local", LocalDate.now().plusDays(10)));
        lista.add(new ProductoPerecible("Pollo Fresco", "Alimentación", 5.00, 50, "Local", LocalDate.now().plusDays(5)));
        lista.add(new ProductoPerecible("Lechuga", "Alimentación", 0.80, 80, "Local", LocalDate.now().plusDays(3)));
        lista.add(new ProductoPerecible("Manzana Roja", "Alimentación", 0.50, 120, "Local", LocalDate.now().plusDays(15)));

        lista.add(new ProductoPerecible("Medicamento Analgésico", "Salud", 8.00, 40, "Local", LocalDate.now().plusYears(1)));
        lista.add(new ProductoPerecible("Antibiótico", "Salud", 12.00, 25, "Local", LocalDate.now().plusYears(2)));

        // Productos No Perecibles (con material)
        lista.add(new ProductoNoPerecible("Cuchillo Cocina", "Hogar", 10.00, 75, "Local", "Acero inoxidable"));
        lista.add(new ProductoNoPerecible("Silla Oficina", "Vivienda", 55.00, 20, "Local", "Plástico"));
        lista.add(new ProductoNoPerecible("Bolsa Tela", "Vestimenta", 5.00, 150, "Local", "Algodón"));
        lista.add(new ProductoNoPerecible("Guantes de Seguridad", "Salud", 7.00, 60, "Local", "Látex"));
        lista.add(new ProductoNoPerecible("Lámpara Mesa", "Vivienda", 25.00, 30, "Local", "Metal"));

        lista.add(new ProductoNoPerecible("Zapatos de Cuero", "Vestimenta", 45.00, 40, "Local", "Cuero"));

        // Más productos variados

        lista.add(new ProductoLocal("Taza Cerámica", "Hogar", 3.00, 120, "Local"));
        lista.add(new ProductoLocal("Libro de Cocina", "Educación", 18.00, 70, "Local"));
        lista.add(new ProductoLocal("Bolígrafo Azul", "Educación", 0.80, 300, "Local"));

        ProductoImportado camara = new ProductoImportado("Cámara Digital", "Tecnología", 250.00, 15, "Importado", "Japón");
        lista.add(camara);

        ProductoImportado reloj = new ProductoImportado("Reloj de Pulsera", "Vestimenta", 75.00, 40, "Importado", "Suiza");
        lista.add(reloj);

        lista.add(new ProductoPerecible("Queso Fresco", "Alimentación", 2.50, 90, "Local", LocalDate.now().plusDays(12)));
        lista.add(new ProductoPerecible("Huevos", "Alimentación", 0.15, 300, "Local", LocalDate.now().plusDays(7)));

        lista.add(new ProductoNoPerecible("Mochila Escolar", "Vestimenta", 35.00, 50, "Local", "Nylon"));
        lista.add(new ProductoNoPerecible("Sartén Antiadherente", "Hogar", 20.00, 40, "Local", "Aluminio"));

        lista.add(new ProductoLocal("Jabón Líquido", "Salud", 4.00, 180, "Local"));
        lista.add(new ProductoLocal("Toalla Baño", "Hogar", 8.00, 70, "Local"));

        lista.add(new ProductoImportado("Tablet", "Tecnología", 400.00, 25, "Importado", "Corea del Sur"));
        lista.add(new ProductoImportado("Audífonos Bluetooth", "Tecnología", 60.00, 50, "Importado", "China"));
        lista.add(new ProductoImportado("Vestido de Fiesta", "Vestimenta", 150.00, 10, "Importado", "España"));

        lista.add(new ProductoPerecible("Mantequilla", "Alimentación", 3.20, 60, "Local", LocalDate.now().plusDays(20)));
        lista.add(new ProductoPerecible("Lechuga Orgánica", "Alimentación", 1.10, 40, "Local", LocalDate.now().plusDays(5)));

        lista.add(new ProductoNoPerecible("Cuchara de Madera", "Hogar", 2.50, 100, "Local", "Madera"));
        lista.add(new ProductoNoPerecible("Mesa Plegable", "Vivienda", 80.00, 10, "Local", "Metal"));

        lista.add(new ProductoLocal("Crema Hidratante", "Salud", 7.50, 90, "Local"));
        lista.add(new ProductoLocal("Papel Higiénico", "Salud", 2.00, 300, "Local"));

        lista.add(new ProductoImportado("Chaqueta de Cuero", "Vestimenta", 120.00, 25, "Importado", "Argentina"));
        lista.add(new ProductoImportado("Zapatos Deportivos", "Vestimenta", 90.00, 30, "Importado", "Brasil"));

        lista.add(new ProductoPerecible("Salmón Fresco", "Alimentación", 10.00, 25, "Local", LocalDate.now().plusDays(7)));
        lista.add(new ProductoPerecible("Zanahoria", "Alimentación", 0.60, 150, "Local", LocalDate.now().plusDays(14)));

        lista.add(new ProductoNoPerecible("Cortina Tela", "Vivienda", 55.00, 20, "Local", "Tela"));
        lista.add(new ProductoNoPerecible("Alfombra", "Vivienda", 75.00, 15, "Local", "Fibra"));

        return lista;
    }
}