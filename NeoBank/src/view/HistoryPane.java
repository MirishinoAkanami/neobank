package view;
import controller.UserSession;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class HistoryPane extends VBox {

    public HistoryPane() {
        setSpacing(10);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: #2b2b2b;");

        Label title = new Label("📜 Transaction History");
        title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-font-weight: bold;");

        VBox list = new VBox(5);

        UserSession.get().getHistory().forEach(record -> {
            Label lbl = new Label(formatRecord(record));
            lbl.setStyle("-fx-text-fill: #ffffff;");
            list.getChildren().add(lbl);
        });

        ScrollPane scroll = new ScrollPane(list);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #2b2b2b; -fx-background-color: #2b2b2b;");

        getChildren().addAll(title, scroll);
    }

    private String formatRecord(String record) {
        String[] parts = record.split(" ");
        try {
            double amt = Double.parseDouble(parts[1]);
            return parts[0] + " " + BankGUI.peso(amt);
        } catch (Exception e) {
            return record;
        }
    }
}
