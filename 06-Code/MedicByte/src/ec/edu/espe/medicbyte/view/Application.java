package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.controller.HomeController;

/**
 *
 * @author Andres Jonathan J.
 */
public class Application {
    public static void main(String[] args) {
        HomeController controller = new HomeController();
        controller.init();
    }
}
