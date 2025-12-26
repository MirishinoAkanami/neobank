package view;

import controller.BankController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankGUI {

    public static Stage stage;
    public static BankController controller = new BankController();
    public static final String FILE = "accounts.dat";

    public static void start(Stage primaryStage) {
        stage = primaryStage;
        controller.load(FILE);
        setScene(new LoginView().getScene());
        stage.setTitle("Neo Bank");
        stage.show();
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public static String peso(double amount) {
        if (amount >= 1_000_000_000_000.0)
            return String.format("₱%.2fT", amount / 1_000_000_000_000.0);
        if (amount >= 1_000_000_000.0)
            return String.format("₱%.2fB", amount / 1_000_000_000.0);
        if (amount >= 1_000_000.0)
            return String.format("₱%.2fM", amount / 1_000_000.0);
        return String.format("₱%,.2f", amount);
    }
    
    public static void saveData() {
    	controller.save(FILE);
 }

}
