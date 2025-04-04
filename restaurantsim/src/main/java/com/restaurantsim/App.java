package com.restaurantsim;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    // GUI-elementer
    private BorderPane pane = new BorderPane();
    private Scene scene;
    private VBox venstreVbox = new VBox();
    private VBox midtVbox = new VBox();
    private VBox høyreVbox = new VBox();
    private HBox buttons = new HBox();
    private ListView<Kunde> kundeListView = new ListView<>();
    private ListView<Ordre> ordreListView = new ListView<>();
    private ListView<Kokk> kokkListView = new ListView<>();
    private ListView<String> loggListView = new ListView<>();
    private Button startButton = new Button("Start");
    private Button stopButton = new Button("Stop");
    private Button pauseButton = new Button("Pause");
    private Label kundeLbl = new Label("Kunde");
    private Label ordreLbl = new Label("Ordre");
    private Label kokkLbl = new Label("Kokk");
    private Label loggLbl = new Label("Logg");

    // Systemvariabler
    private final double VINDU_BREDDE = 1000;
    private final double VINDU_HØYDE = 600;
    private final Restaurant restaurant = new Restaurant(2, 5);
    private Ordre ordre;

    @Override
    public void start(Stage stage) {
        pane.setPrefSize(VINDU_BREDDE, VINDU_HØYDE);
        stage.setResizable(false);

        // VBox Config
        venstreVbox.setPrefWidth(VINDU_BREDDE / 3);
        venstreVbox.setPrefHeight(VINDU_HØYDE);
        midtVbox.setPrefWidth(VINDU_BREDDE / 3);
        midtVbox.setPrefHeight(VINDU_HØYDE);
        høyreVbox.setPrefWidth(VINDU_BREDDE / 3);
        høyreVbox.setPrefHeight(VINDU_HØYDE);

        // Legger til og plasserer GUI-elementer
        pane.setLeft(venstreVbox);
        venstreVbox.getChildren().addAll(kundeLbl, kundeListView, buttons);
        buttons.getChildren().addAll(startButton, stopButton, pauseButton);

        pane.setCenter(midtVbox);
        midtVbox.getChildren().addAll(ordreLbl, ordreListView);

        pane.setRight(høyreVbox);
        høyreVbox.getChildren().addAll(kokkLbl, kokkListView, loggLbl, loggListView);

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

        










    }



}