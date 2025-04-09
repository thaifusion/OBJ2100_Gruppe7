// App.java
package com.restaurantsim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private static TextArea logArea = new TextArea();
    private static TextArea bestillingInfoArea = new TextArea();
    private static ObservableList<String> aktiveKunder = FXCollections.observableArrayList();
    private static Label scoreboardLabel = new Label("Scoreboard: Happy: 0, Angry: 0");

    public static RestaurantSimulation simulation;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // Topp
        Label title = new Label("Restaurant Simulering");
        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");
        Button stoppButton = new Button("Stopp");
        Button visLoggKnapp = new Button("Vis logg");
        visLoggKnapp.setOnAction(e -> LoggViewer.visLoggVindu());

        HBox tittelBox = new HBox(title);
        tittelBox.setAlignment(Pos.CENTER_LEFT);
        tittelBox.setPadding(new Insets(10));

        HBox buttonBox = new HBox(10, startButton, pauseButton, stoppButton);
        buttonBox.setAlignment(Pos.CENTER);

        HBox loggBox = new HBox(visLoggKnapp);
        loggBox.setAlignment(Pos.CENTER);

        HBox scoreboardBox = new HBox(scoreboardLabel);
        scoreboardBox.setAlignment(Pos.CENTER);
        scoreboardBox.setPadding(new Insets(10, 0, 10, 0));
        scoreboardLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox topPane = new VBox(tittelBox, buttonBox, loggBox, scoreboardBox);
        root.setTop(topPane);

        // Venstre panel
        VBox leftPane = new VBox(10);
        Label kunderLabel = new Label("Aktive kunder:");
        ListView<String> kunderListView = new ListView<>(aktiveKunder);
        leftPane.getChildren().addAll(kunderLabel, kunderListView);
        leftPane.setPadding(new Insets(10));
        root.setLeft(leftPane);

        // Høyre panel
        VBox rightPane = new VBox(10);
        Label ordreLabel = new Label("Bestillingsinfo:");
        bestillingInfoArea.setEditable(false);
        rightPane.getChildren().addAll(ordreLabel, bestillingInfoArea);
        rightPane.setPadding(new Insets(10));
        root.setRight(rightPane);

        // Bunn
        VBox bottomPane = new VBox(5);
        Label statusLabel = new Label("Status: Venter på bestillinger...");
        logArea.setEditable(false);
        logArea.setStyle("-fx-control-inner-background: #f9f9f9;");
        bottomPane.getChildren().addAll(statusLabel, logArea);
        bottomPane.setPadding(new Insets(10));
        root.setBottom(bottomPane);

        // Scene
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Restaurant Simulering");
        stage.setScene(scene);
        stage.show();

        // Initier simulering
        simulation = new RestaurantSimulation(5);

        // Registrer kokker
        Kokk kokk1 = new Kokk("Eivind Hellstrøm", Måltider.PASTA, simulation.getBestillingsKø(), simulation.getHentekø(), simulation);
        Kokk kokk2 = new Kokk("Jamie Oliver", simulation.getBestillingsKø(), simulation.getHentekø(), simulation); // allrounder
        Kokk kokk3 = new Kokk("Arne Brimi", Måltider.SALAT, simulation.getBestillingsKø(), simulation.getHentekø(), simulation);
        Kokk kokk4 = new Kokk("Lars Monsen", Måltider.BURGER, simulation.getBestillingsKø(), simulation.getHentekø(), simulation);
        Kokk kokk5 = new Kokk("Gordon Ramsay", simulation.getBestillingsKø(), simulation.getHentekø(), simulation); // allrounder

        startButton.setOnAction(e -> {
            if (!simulation.kjører()) {
                simulation.startSimulering();
                simulation.startKokk(kokk1);
                simulation.startKokk(kokk2);
                simulation.startKokk(kokk3);
                simulation.startKokk(kokk4);
                simulation.startKokk(kokk5);

                startButton.setDisable(true);
                pauseButton.setDisable(false);
                stoppButton.setDisable(false);
                statusLabel.setText("Status: Simulering startet");
                appendLog("Simulering startet.");
            }
        });

        pauseButton.setOnAction(e -> {
            statusLabel.setText("Status: Simulering pausert");
            appendLog("Simuleringen er midlertidig satt på pause.");
        });

        stoppButton.setOnAction(e -> {
            simulation.stopSimulering();
            startButton.setDisable(false);
            pauseButton.setDisable(true);
            stoppButton.setDisable(true);
            startButton.setText("Fortsett");
            statusLabel.setText("Status: Simulering stoppet");
            appendLog("Simulering stoppet.");
        });

        pauseButton.setDisable(true);
        stoppButton.setDisable(true);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateScoreboard();
            }
        }.start();
    }

    public static void appendLog(String message) {
        Platform.runLater(() -> {
            logArea.appendText(message + "\n");
            logArea.setScrollTop(Double.MAX_VALUE);
        });
    }

    public static void appendBestillingsinfo(String message) {
        Platform.runLater(() -> {
            bestillingInfoArea.appendText(message + "\n");
            bestillingInfoArea.setScrollTop(Double.MAX_VALUE);
        });
    }

    public static void addKundeTilListe(String kundeNavn) {
        Platform.runLater(() -> aktiveKunder.add(kundeNavn));
    }

    public static void removeKundeFraListe(String kundeNavn) {
        Platform.runLater(() -> aktiveKunder.remove(kundeNavn));
    }

    private void updateScoreboard() {
        Platform.runLater(() -> {
            scoreboardLabel.setText("Scoreboard: Happy: " + simulation.getHappyCount() +
                    ", Angry: " + simulation.getAngryCount());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}