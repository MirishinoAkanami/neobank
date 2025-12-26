import javafx.application.Application;
import javafx.stage.Stage;
import view.BankGUI;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BankGUI.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
