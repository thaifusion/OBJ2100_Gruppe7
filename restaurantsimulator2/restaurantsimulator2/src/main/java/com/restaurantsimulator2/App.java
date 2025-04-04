package com.restaurantsimulator2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    // Gui-komponenter
    private final BorderPane root = new BorderPane();
    private VBox venstreVbox = new VBox();
    private VBox midtVbox = new VBox();
    private VBox høyreVbox = new VBox();
    private ListView<Kunde> kundeListView = new ListView<>();
    private ListView<Ordre> ordreListView = new ListView<>();
    private ListView<Kokk> kokkListView = new ListView<>();
    private ListView<String> loggListView = new ListView<>();
    private Label kundeLabel = new Label("Kunder:");
    private Label ordreLabel = new Label("Ordre:");
    private Label kokkLabel = new Label("Kokker:");
    private Label loggLabel = new Label("Hendelseslogg:");

    // Systemvariabler
    private final double VINDU_BREDDE = 1000;
    private final double VINDU_HØYDE = 800;

    @Override
    public void start(Stage stage) {
        root.setPrefSize(VINDU_BREDDE, VINDU_HØYDE);

        root.setLeft(venstreVbox);
        venstreVbox.getChildren().addAll(kundeLabel, kundeListView);
        venstreVbox.setPrefWidth(VINDU_BREDDE / 3);

        root.setCenter(midtVbox);
        midtVbox.getChildren().addAll(ordreLabel, ordreListView);
        midtVbox.setPrefWidth(VINDU_BREDDE / 3);

        root.setRight(høyreVbox);
        høyreVbox.getChildren().addAll(kokkLabel,kokkListView, loggLabel,loggListView);
        høyreVbox.setPrefWidth(VINDU_BREDDE / 3);


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}