package com.restaurantsim;



import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        HBox topPane = new HBox(10);
        Label title = new Label("Restaurant Simulering");
        Button startButton = new Button("Start");
        Button stoppButton = new Button("Stopp");       
        buttonBox.getChildren().addAll(startButton, stoppButton);

        Button visLoggKnapp = new Button("Vis logg");
        HBox loggButtonBox = new HBox(visLoggKnapp);
        loggButtonBox.setAlignment(Pos.CENTER);


        HBox scoreboardBox = new HBox(scoreboardLabel);
        scoreboardBox.setAlignment(Pos.CENTER);
        scoreboardLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        scoreboardLabel.setId("scoreboard");
        scoreboardBox.setPadding(new Insets(20, 0, 20, 0));

        topPane.getChildren().addAll(tittelBox, buttonBox, loggButtonBox, scoreboardBox);
        Button pauseButton = new Button("Pause");
        Button stoppButton = new Button("Stopp");
        topPane.getChildren().addAll(title, startButton, pauseButton, stoppButton);
        root.setTop(topPane);

        // Venstre panel
        VBox leftPane = new VBox(10);
        Label kunderLabel = new Label("Aktive kunder:");
        ListView<String> kunderListView = new ListView<>(aktiveKunder);
        leftPane.getChildren().addAll(kunderLabel, kunderListView);
        root.setLeft(leftPane);

        // Høyre panel
        VBox rightPane = new VBox(10);
        Label ordreLabel = new Label("Bestillingsinfo:");
        bestillingInfoArea.setEditable(false);
        rightPane.getChildren().addAll(ordreLabel, bestillingInfoArea);
        root.setRight(rightPane);

        // Bunn
        VBox bottomPane = new VBox(5);
        Label statusLabel = new Label("Status: Venter på bestillinger...");
        logArea.setEditable(false);
        logArea.setStyle("-fx-control-inner-background: #f9f9f9;");
        bottomPane.getChildren().addAll(statusLabel, scoreboardLabel, logArea);
        root.setBottom(bottomPane);

        // Scene
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Restaurant Simulering");
        stage.setScene(scene);
        stage.show();

        // Opprett simulering
        simulation = new RestaurantSimulation(5);
        
        // Registrer og start kokker (f.eks. en kokk spesialisert på PIZZA og en som kan alt)
        Kokk kokk1 = new Kokk("Eivind Hellstrøm", simulation.getBestillingsKø(), simulation.getHentekø(), Måltider.PASTA, simulation);
        Kokk kokk2 = new Kokk("Jamie Oliver", simulation.getBestillingsKø(), simulation.getHentekø(), simulation);
        Kokk kokk3 = new Kokk("Arne Brimi", simulation.getBestillingsKø(), simulation.getHentekø(), Måltider.SALAT, simulation);
        Kokk kokk4 = new Kokk("Lars Monsen", simulation.getBestillingsKø(), simulation.getHentekø(), Måltider.BURGER, simulation);
        Kokk kokk5 = new Kokk("Gordon Ramsay", simulation.getBestillingsKø(), simulation.getHentekø(), simulation);

        // --- Koble kontrollknapper til handlinger ---

        // Registrer kokker
        Kokk kokk1 = new Kokk("Gordon Ramsay", Måltider.PIZZA, simulation.getBestillingsKø(), simulation.getHentekø(), simulation);
        Kokk kokk2 = new Kokk("Anthony Bourdain", Måltider.SALAT, simulation.getBestillingsKø(), simulation.getHentekø(), simulation);
        Kokk kokk3 = new Kokk("Guy Fieri", Måltider.BURGER, simulation.getBestillingsKø(), simulation.getHentekø(), simulation);
        Kokk kokk4 = new Kokk("Ina Garten", Måltider.PASTA, simulation.getBestillingsKø(), simulation.getHentekø(), simulation);
        Kokk kokk5 = new Kokk("Jamie Oliver", null, simulation.getBestillingsKø(), simulation.getHentekø(), simulation); // allrounder

        startButton.setOnAction(e -> {
            if (!simulation.kjører()) {
                simulation.startSimulering();
                startButton.setDisable(true);
                stoppButton.setDisable(false);

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
            }
        });

        pauseButton.setOnAction(e -> {
            statusLabel.setText("Status: Simulering pausert");
            appendLog("Simulering er midlertidig satt på pause.");
        });

        stoppButton.setOnAction(e -> {
            simulation.stopSimulering();
            startButton.setDisable(false);
            stoppButton.setDisable(true);
            statusLabel.setText("Status: Simulering stoppet");
        });

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

    public static void addKundeTilListe(String kundeNavn) {
        Platform.runLater(() -> aktiveKunder.add(kundeNavn));
    }

    public static void removeKundeFraListe(String kundeNavn) {
        Platform.runLater(() -> aktiveKunder.remove(kundeNavn));
    }

    public static void appendBestillingsinfo(String message) {
        Platform.runLater(() -> {
            bestillingInfoArea.appendText(message + "\n");
            bestillingInfoArea.setScrollTop(Double.MAX_VALUE);
        });
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