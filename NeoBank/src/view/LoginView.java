package view;
import view.BankGUI;
import controller.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Account;

public class LoginView {

    public Scene getScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2b2b2b;");

        VBox box = new VBox(15);
        box.setPadding(new Insets(30));
        box.setAlignment(Pos.CENTER);

        Label title = new Label("🏦 Neo Bank");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("User ID (numbers only, max 6 digits)");
        idField.setMaxWidth(250);

        PasswordField passField = new PasswordField();
        passField.setPromptText("Password (min 8 characters)");
        passField.setMaxWidth(250);

        Label error = new Label();
        error.setStyle("-fx-text-fill: #ff6666;");

        Button loginBtn = new Button("🔐 Log In");
        Button registerBtn = new Button("📝 Sign Up");

        String btnStyle = "-fx-background-color: #5c6bc0; -fx-text-fill: white; -fx-font-weight: bold;";
        loginBtn.setStyle(btnStyle);
        registerBtn.setStyle(btnStyle);

        loginBtn.setMaxWidth(250);
        registerBtn.setMaxWidth(250);

        Runnable loginAction = () -> {
            String idText = idField.getText().trim();
            String pass = passField.getText();

            if (idText.isEmpty() || pass.isEmpty()) {
                error.setText("Please fill in all fields.");
                return;
            }

            if (!idText.matches("\\d+")) {
                error.setText("User ID must be numeric.");
                return;
            }

            if (idText.length() > 6) {
                error.setText("User ID must be at most 6 digits.");
                return;
            }

            int id = Integer.parseInt(idText);

            Account acc = BankGUI.controller.login(id, pass);
            if (acc != null) {
                UserSession.set(acc);
                BankGUI.setScene(new DashboardView().getScene());
            } else {
                error.setText("Invalid ID or password.");
            }
        };

        loginBtn.setOnAction(e -> loginAction.run());

        passField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginAction.run();
            }
        });

        registerBtn.setOnAction(e ->
                BankGUI.setScene(new RegisterView().getScene())
        );

        box.getChildren().addAll(
                title,
                idField,
                passField,
                loginBtn,
                registerBtn,
                error
        );

        root.setCenter(box);
        return new Scene(root, 400, 420);
    }
}
