package view;
import controller.UserSession;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class DashboardView {

    private BorderPane root;

    public Scene getScene() {

        root = new BorderPane();
        root.setStyle("-fx-background-color: #1e1e1e;");

        HBox header = new HBox(20);
        header.setPadding(new Insets(15));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #252526;");

        Label logo = new Label("🏦 NeoBank");
        logo.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 24px;
            -fx-font-weight: bold;
        """);

        Label userInfo = new Label(
                "User: " + UserSession.get().getId() +
                " | Balance: " + BankGUI.peso(UserSession.get().getBalance())
        );
        userInfo.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");

        header.getChildren().addAll(logo, userInfo);
        root.setTop(header);

        VBox menu = new VBox(10);
        menu.setPadding(new Insets(15));
        menu.setPrefWidth(180);
        menu.setStyle("-fx-background-color: #2b2b2b;");

        Button depositBtn = sideBtn("💰 Deposit");
        Button withdrawBtn = sideBtn("🏧 Withdraw");
        Button transferBtn = sideBtn("🔁 Transfer");
        Button historyBtn = sideBtn("📜 History");
        Button adminBtn = sideBtn("🛠 Admin");
        Button logoutBtn = sideBtn("🚪 Logout");

        menu.getChildren().addAll(
                depositBtn,
                withdrawBtn,
                transferBtn,
                historyBtn
        );

        if (UserSession.get().isAdmin()) {
            menu.getChildren().add(adminBtn);
        }

        menu.getChildren().add(logoutBtn);
        root.setLeft(menu);
        root.setCenter(new DepositPane());

        depositBtn.setOnAction(e -> root.setCenter(new DepositPane()));
        withdrawBtn.setOnAction(e -> root.setCenter(new WithdrawPane()));
        transferBtn.setOnAction(e -> root.setCenter(new TransferPane()));
        historyBtn.setOnAction(e -> root.setCenter(new HistoryPane()));
        adminBtn.setOnAction(e -> root.setCenter(new AdminPane()));

        logoutBtn.setOnAction(e -> {
            UserSession.clear();
            BankGUI.setScene(new LoginView().getScene());
        });

        return new Scene(root, 900, 600, Color.BLACK);
    }

    private Button sideBtn(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("""
            -fx-background-color: #3c3f41;
            -fx-text-fill: white;
            -fx-font-size: 13px;
            -fx-font-weight: bold;
        """);

        btn.setOnMouseEntered(e ->
                btn.setStyle("""
                    -fx-background-color: #5c6bc0;
                    -fx-text-fill: white;
                    -fx-font-size: 13px;
                    -fx-font-weight: bold;
                """)
        );

        btn.setOnMouseExited(e ->
                btn.setStyle("""
                    -fx-background-color: #3c3f41;
                    -fx-text-fill: white;
                    -fx-font-size: 13px;
                    -fx-font-weight: bold;
                """)
        );

        return btn;
    }
}
