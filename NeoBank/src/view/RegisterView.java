package view;

import view.BankGUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterView {

    public Scene getScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2b2b2b;"); // Dark Mode

        Label title = new Label("Neo Bank - Sign Up");
        title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 24px; -fx-font-weight: bold;");

        TextField idField = new TextField();
        idField.setPromptText("Account ID (max 6 digits)");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Password (min 8 chars)");

        PasswordField pinField = new PasswordField();
        pinField.setPromptText("4-digit PIN");

        String fieldStyle = "-fx-background-color: #3c3f41; -fx-text-fill: #ffffff; -fx-prompt-text-fill: #aaaaaa;";
        idField.setStyle(fieldStyle);
        passField.setStyle(fieldStyle);
        pinField.setStyle(fieldStyle);

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: #ff6666;");

        Button signupBtn = new Button("Sign Up");
        signupBtn.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;");
        signupBtn.setOnMouseEntered(e -> signupBtn.setStyle("-fx-background-color: #66bb6a; -fx-text-fill: white; -fx-font-weight: bold;"));
        signupBtn.setOnMouseExited(e -> signupBtn.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;"));

        Button backBtn = new Button("Back to Login");
        backBtn.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white; -fx-font-weight: bold;");
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: #7986cb; -fx-text-fill: white; -fx-font-weight: bold;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white; -fx-font-weight: bold;"));

        // Sign Up action
        signupBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String pass = passField.getText();
                String pin = pinField.getText();

                boolean success = BankGUI.controller.register(id, pass, pin);
                if (!success) {
                    msg.setText("Invalid data or ID already exists");
                    return;
                }

                BankGUI.controller.save(BankGUI.FILE);

                Alert ok = new Alert(Alert.AlertType.INFORMATION);
                ok.setTitle("Registration Successful");
                ok.setHeaderText(null);
                ok.setContentText("Account created! You can now login.");
                ok.showAndWait();

                BankGUI.setScene(new LoginView().getScene());

            } catch (NumberFormatException ex) {
                msg.setText("Invalid ID");
            }
        });

        backBtn.setOnAction(e -> BankGUI.setScene(new LoginView().getScene()));

        pinField.setOnAction(e -> signupBtn.fire());
        passField.setOnAction(e -> signupBtn.fire());
        idField.setOnAction(e -> signupBtn.fire());

        root.getChildren().addAll(title, idField, passField, pinField, signupBtn, backBtn, msg);

        return new Scene(root, 450, 400);
    }
}
