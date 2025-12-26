package view;

import view.BankGUI;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import model.Account;

public class AdminPane extends VBox {

    public AdminPane() {
        setSpacing(10);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: #2b2b2b;");

        Label title = new Label("🛠 Admin Panel");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        VBox list = new VBox(5);

        BankGUI.controller.getAllAccounts().forEach(acc -> {
            HBox row = new HBox(10);
            row.setPadding(new Insets(5));

            Label info = new Label(
                    "ID: " + acc.getId() +
                    " | Balance: " + BankGUI.peso(acc.getBalance()) +
                    " | Type: " + (acc.isAdmin() ? "ADMIN" : "USER") +
                    " | Locked: " + (!acc.isAdmin() && acc.isLocked())
            );
            info.setStyle("-fx-text-fill: #ffffff;");

            row.getChildren().add(info);

            if (!acc.isAdmin()) {
                Button resetBtn = new Button("Reset PIN / Unlock");
                resetBtn.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white; -fx-font-weight: bold;");
                resetBtn.setOnMouseEntered(e -> resetBtn.setStyle("-fx-background-color: #7986cb; -fx-text-fill: white; -fx-font-weight: bold;"));
                resetBtn.setOnMouseExited(e -> resetBtn.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white; -fx-font-weight: bold;"));

                resetBtn.setOnAction(e -> {
                    TextInputDialog newPinDialog = new TextInputDialog();
                    newPinDialog.setTitle("Reset PIN");
                    newPinDialog.setHeaderText(null);
                    newPinDialog.setContentText("Enter new 4-digit PIN for Account " + acc.getId() + ":");

                    String np = newPinDialog.showAndWait().orElse(null);
                    if (np == null || np.length() != 4) return;

                    acc.setPin(np);
                    acc.unlock();
                    BankGUI.controller.save(BankGUI.FILE);

                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setTitle("PIN Reset");
                    ok.setHeaderText(null);
                    ok.setContentText("Account " + acc.getId() + " PIN has been reset.");
                    ok.showAndWait();

                    row.getChildren().clear();
                    row.getChildren().add(info);
                    row.getChildren().add(resetBtn);
                });

                row.getChildren().add(resetBtn);
            }

            list.getChildren().add(row);
        });

        ScrollPane scroll = new ScrollPane(list);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #2b2b2b; -fx-background-color: #2b2b2b;");

        getChildren().addAll(title, scroll);
    }
}
