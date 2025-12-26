package view;

import view.BankGUI;
import controller.UserSession;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class WithdrawPane extends VBox {

    public WithdrawPane() {
        setSpacing(10);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: #2b2b2b;");

        Label title = new Label("🏧 Withdraw");
        title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-font-weight: bold;");

        TextField amtField = new TextField();
        amtField.setPromptText("Amount");
        amtField.setStyle(
                "-fx-background-color: #3c3f41;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-prompt-text-fill: #aaaaaa;"
        );

        PasswordField pinField = new PasswordField();
        pinField.setPromptText("Enter PIN");
        pinField.setStyle(
                "-fx-background-color: #3c3f41;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-prompt-text-fill: #aaaaaa;"
        );

        Button witBtn = new Button("Withdraw");
        witBtn.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white; -fx-font-weight: bold;");
        witBtn.setOnMouseEntered(e ->
                witBtn.setStyle("-fx-background-color: #7986cb; -fx-text-fill: white; -fx-font-weight: bold;"));
        witBtn.setOnMouseExited(e ->
                witBtn.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white; -fx-font-weight: bold;"));

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: #66ff66;");

        witBtn.setOnAction(e -> {
            try {
                double amt = Double.parseDouble(amtField.getText());
                String pin = pinField.getText();

                if (!pin.equals(UserSession.get().getPin())) {
                    msg.setStyle("-fx-text-fill: #ff6666;");
                    msg.setText("Invalid PIN");
                    return;
                }

                if (amt <= 0) {
                    msg.setStyle("-fx-text-fill: #ff6666;");
                    msg.setText("Amount must be positive");
                    return;
                }

                if (!UserSession.get().withdraw(amt)) {
                    msg.setStyle("-fx-text-fill: #ff6666;");
                    msg.setText("Insufficient funds");
                    return;
                }

                // Save data
                BankGUI.controller.save(BankGUI.FILE);

                // Refresh dashboard so balance updates
                BankGUI.setScene(new DashboardView().getScene());

            } catch (NumberFormatException ex) {
                msg.setStyle("-fx-text-fill: #ff6666;");
                msg.setText("Invalid amount");
            }
        });

        // ENTER key triggers withdraw
        amtField.setOnAction(e -> witBtn.fire());
        pinField.setOnAction(e -> witBtn.fire());

        getChildren().addAll(title, amtField, pinField, witBtn, msg);
    }
}
