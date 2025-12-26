package view;
import view.BankGUI;
import controller.UserSession;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class DepositPane extends VBox {

    public DepositPane() {
        setSpacing(10);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: #2b2b2b;");

        Label title = new Label("💰 Deposit");
        title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-font-weight: bold;");

        TextField amtField = new TextField();
        amtField.setPromptText("Amount");
        amtField.setStyle(
                "-fx-background-color: #3c3f41;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-prompt-text-fill: #aaaaaa;"
        );

        Button depBtn = new Button("Deposit");
        depBtn.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white; -fx-font-weight: bold;");
        depBtn.setOnMouseEntered(e ->
                depBtn.setStyle("-fx-background-color: #7986cb; -fx-text-fill: white; -fx-font-weight: bold;"));
        depBtn.setOnMouseExited(e ->
                depBtn.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white; -fx-font-weight: bold;"));

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: #66ff66;");

        depBtn.setOnAction(e -> {
            try {
                double amt = Double.parseDouble(amtField.getText());

                if (amt <= 0) {
                    msg.setText("Amount must be positive");
                    return;
                }

                UserSession.get().deposit(amt);

                BankGUI.controller.save(BankGUI.FILE);

                BankGUI.setScene(new DashboardView().getScene());

            } catch (NumberFormatException ex) {
                msg.setText("Invalid amount");
            }
        });

        amtField.setOnAction(e -> depBtn.fire());

        getChildren().addAll(title, amtField, depBtn, msg);
    }
}
