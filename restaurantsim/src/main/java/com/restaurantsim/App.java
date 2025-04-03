package com.restaurantsim;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    // GUI-elementer
    private BorderPane pane = new BorderPane();
    private Scene scene;
    private VBox venstreVbox = new VBox();
    private VBox midtVbox = new VBox();
    private VBox høyreVbox = new VBox();

    // Systemvariabler
    private final double VINDU_BREDDE = 1000;
    private final double VINDU_HØYDE = 600;

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
        venstreVbox.setStyle("-fx-background-color: lightblue;");

        pane.setCenter(midtVbox);
        midtVbox.setStyle("-fx-background-color: lightgreen;");

        pane.setRight(høyreVbox);
        høyreVbox.setStyle("-fx-background-color: lightyellow;");


        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}