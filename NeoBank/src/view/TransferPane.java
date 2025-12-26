package view;

import controller.UserSession;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class TransferPane extends VBox {

    public TransferPane() {
        setSpacing(10);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: #2b2b2b;");

        Label title = new Label("🔁 Transfer");
        title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-font-weight: bold;");

        TextField targetField = new TextField();
        targetField.setPromptText("Target Account ID");

        TextField amtField = new TextField();
        amtField.setPromptText("Amount");

        PasswordField pinField = new PasswordField();
        pinField.setPromptText("Enter PIN");

        Button transferBtn = new Button("Transfer");

        Label msg = new Label();

        transferBtn.setOnAction(e -> {
            try {
                int targetId = Integer.parseInt(targetField.getText());
                double amt = Double.parseDouble(amtField.getText());
                String pin = pinField.getText();

                // PIN check
                if (!pin.equals(UserSession.getPin())) {
                    msg.setText("❌ Invalid PIN");
                    return;
                }

                boolean success = BankGUI.controller.transfer(
                        UserSession.getId(),
                        targetId,
                        amt
                );

                if (!success) {
                    msg.setText("❌ Transfer failed");
                    return;
                }

                BankGUI.controller.save(BankGUI.FILE);
                msg.setText("✅ Sent " + BankGUI.peso(amt) +
                        " | Balance: " + BankGUI.peso(UserSession.getBalance()));

                targetField.clear();
                amtField.clear();
                pinField.clear();

            } catch (NumberFormatException ex) {
                msg.setText("❌ Invalid input");
            }
        });

        // Enter key support
        targetField.setOnAction(e -> transferBtn.fire());
        amtField.setOnAction(e -> transferBtn.fire());
        pinField.setOnAction(e -> transferBtn.fire());

        getChildren().addAll(title, targetField, amtField, pinField, transferBtn, msg);
    }
}
