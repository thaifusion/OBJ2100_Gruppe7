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
    private static Label scoreboardLabel = new Label("Scoreboard: 😊 Happy: 0 | 😠 Angry: 0");
    public static RestaurantSimulation simulation;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // --- Toppanelet: sentrert tittel, knapper og scoreboard ---
        VBox topPane = new VBox(10);
        topPane.setPadding(new Insets(10));

        Label tittel = new Label("Restaurant Simulering");
        tittel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        HBox tittelBox = new HBox(tittel);
        tittelBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
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
        root.setTop(topPane);

        // --- Venstre panel: aktive kunder ---
        VBox leftPane = new VBox(10);
        Label kunderLabel = new Label("Aktive kunder:");
        ListView<String> kunderListView = new ListView<>(aktiveKunder);
        kunderListView.setPrefSize(200, 200);
        leftPane.getChildren().addAll(kunderLabel, kunderListView);

        // --- Høyre panel: bestillingsinfo ---
        VBox rightPane = new VBox(10);
        Label ordreLabel = new Label("Bestillingsinfo:");
        bestillingInfoArea.setEditable(false);
        bestillingInfoArea.setPrefSize(300, 200);
        rightPane.getChildren().addAll(ordreLabel, bestillingInfoArea);

        // --- Kombiner venstre og høyre panel i midten ---
        HBox centerPane = new HBox(50); // spacing mellom venstre og høyre
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setPadding(new Insets(10));
        centerPane.getChildren().addAll(leftPane, rightPane);
        root.setCenter(centerPane);

        // --- Bunnpanel: statuslinje og loggvisning ---
        VBox bottomPane = new VBox(5);
        bottomPane.setPadding(new Insets(0, 0, 30, 0)); // Top, Right, Bottom, Left
        Label statusLabel = new Label("Status: Venter på bestillinger...");
        logArea.setEditable(false);
        logArea.setPrefHeight(150);
        bottomPane.getChildren().addAll(statusLabel, logArea);

        HBox bottomWrapper = new HBox(bottomPane);
        bottomWrapper.setAlignment(Pos.CENTER);
        root.setBottom(bottomWrapper);

        // --- Sceneoppsett ---
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Restaurant Simulering");
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.show();

        // --- Start simulering ---
        simulation = new RestaurantSimulation(5);
        LoggerUtil.tømLogg(); // Tømmer loggfilen før ny simulering
        // Registrer og start kokker (f.eks. en kokk spesialisert på PIZZA og en som kan alt)
        Kokk kokk1 = new Kokk("Eivind Hellstrøm", simulation.getBestillingsKø(), simulation.getHentekø(), Måltider.PASTA, simulation);
        Kokk kokk2 = new Kokk("Jamie Oliver", simulation.getBestillingsKø(), simulation.getHentekø(), Måltider.PIZZA, simulation);
        Kokk kokk3 = new Kokk("Arne Brimi", simulation.getBestillingsKø(), simulation.getHentekø(), simulation);
        Kokk kokk4 = new Kokk("Lars Monsen", simulation.getBestillingsKø(), simulation.getHentekø(), simulation);
        Kokk kokk5 = new Kokk("Gordon Ramsay", simulation.getBestillingsKø(), simulation.getHentekø(), simulation);

        // --- Koble kontrollknapper til handlinger ---
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
                statusLabel.setText("Status: Simulering startet");
                appendLog("Simulering startet.");
            }   
        });

        stoppButton.setOnAction(e -> {
            simulation.stopSimulering();
            startButton.setDisable(false);
            stoppButton.setDisable(true);
            statusLabel.setText("Status: Simulering stoppet");
        });

        stoppButton.setDisable(true);

        visLoggKnapp.setOnAction(e -> { LoggViewer.visLoggVindu(); });
            
    
        // --- Scoreboard oppdatering ---
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateScoreboard();
            }
        }.start();
    }

    public static void appendLog(String message) {
        Platform.runLater(() -> logArea.appendText(message + "\n"));
    }

    public static void addKundeTilListe(String kundeNavn) {
        Platform.runLater(() -> aktiveKunder.add(kundeNavn));
    }

    public static void removeKundeFraListe(String kundeNavn) {
        Platform.runLater(() -> aktiveKunder.remove(kundeNavn));
    }

    public static void appendBestillingsinfo(String message) {
        Platform.runLater(() -> bestillingInfoArea.appendText(message + "\n"));
    }

    private void updateScoreboard() {
        Platform.runLater(() -> {
            scoreboardLabel.setText("Scoreboard: 😊 Happy: " + simulation.getHappyCount() +
                                    " | 😠 Angry: " + simulation.getAngryCount());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
