package View;

import Model.DBMananger;
import controller.SuperMaxiController;
import view.SuperMaxiView;


/**
 *
 * @author Mateo Gonzáles y Mateo Rivera
 */
public class SuperMaxiMain {
    public static void main(String[] args) {
        DBMananger db = new DBMananger();
        db.crearTablas();  // Crear tablas en caso de no exisitir
        SuperMaxiController controller = new SuperMaxiController(db);
        SuperMaxiView menu = new SuperMaxiView(controller);
        menu.mostrarMenu();
    }
}
