package View;

import controller.SuperMaxiController;
import view.SuperMaxiView;


/**
 *
 * @author Mateo Gonzáles y Mateo Rivera
 */
public class SuperMaxiMain {
    public static void main(String[] args) {        
        SuperMaxiController controller = new SuperMaxiController();
        SuperMaxiView menu = new SuperMaxiView(controller);
        menu.mostrarMenu();
    }
}
