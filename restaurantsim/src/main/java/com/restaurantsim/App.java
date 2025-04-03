package com.restaurantsim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
=======
import javafx.scene.control.ListView;
>>>>>>> Stashed changes
=======
import javafx.scene.control.ListView;
>>>>>>> Stashed changes
=======
import javafx.scene.control.ListView;
>>>>>>> Stashed changes
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scala.Int;

public class App extends Application {

<<<<<<< Updated upstream
    // TextArea for logging (nedre panelet)
    private static TextArea logArea = new TextArea();
    // TextArea for bestillingsinformasjon (høyre panelet)
    private static TextArea bestillingInfoArea = new TextArea();
    
    // ObservableList for aktive kunder (venstre panelet)
    private static ObservableList<String> aktiveKunder = FXCollections.observableArrayList();
    
    // Label for scoreboard (viser fornøyde vs. sinte kunder)
    private static Label scoreboardLabel = new Label("Scoreboard: Happy: 0, Angry: 0");
    
    // Referanse til simuleringsobjektet
    private RestaurantSimulation simulation;
=======
    // GUI-elementer
    private BorderPane pane = new BorderPane();
    private Scene scene;
    private VBox venstreVbox = new VBox();
    private VBox midtVbox = new VBox();
    private VBox høyreVbox = new VBox();
    

    // Systemvariabler
    private final double VINDU_BREDDE = 1000;
    private final double VINDU_HØYDE = 600;
    private final Restaurant restaurant = new Restaurant(3, 5);
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

    @Override
    public void start(Stage stage) {
        // Opprett hovedlayout: BorderPane
        BorderPane root = new BorderPane();

        // --- Top-pane: Menylinje med tittel og kontrollknapper ---
        HBox topPane = new HBox(10);
        Label title = new Label("Restaurant Simulering");
        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");
        Button stoppButton = new Button("Stopp");
        topPane.getChildren().addAll(title, startButton, pauseButton, stoppButton);
        root.setTop(topPane);

<<<<<<< Updated upstream
        // --- Left-pane: Liste over aktive kunder ---
        VBox leftPane = new VBox(10);
        Label kunderLabel = new Label("Aktive kunder:");
        ListView<String> kunderListView = new ListView<>(aktiveKunder);
        leftPane.getChildren().addAll(kunderLabel, kunderListView);
        root.setLeft(leftPane);

        // --- Center-pane: (Tomt for nå – kan utvides med Canvas for animasjon) ---
        // root.setCenter(canvas);

        // --- Right-pane: Bestillingsinfo og kokkestatus ---
        VBox rightPane = new VBox(10);
        Label ordreLabel = new Label("Bestillingsinfo:");
        bestillingInfoArea.setEditable(false);
        rightPane.getChildren().addAll(ordreLabel, bestillingInfoArea);
        root.setRight(rightPane);
=======
        // Legger til GUI-elementer
        pane.setLeft(venstreVbox);
        venstreVbox.setStyle("-fx-background-color: lightblue;");
        venstreVbox.getChildren().add(new venstreVboxController().kundeListView);

        pane.setCenter(midtVbox);
        midtVbox.setStyle("-fx-background-color: lightgreen;");
        midtVbox.getChildren().add(new midtVboxController().underBehandlingListView);

        pane.setRight(høyreVbox);
        høyreVbox.setStyle("-fx-background-color: lightyellow;");
        høyreVbox.getChildren().add(new høyreVboxController().kokkListView);
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

        // --- Bottom-pane: Statuslinje, scoreboard og loggvisning ---
        VBox bottomPane = new VBox(5);
        Label statusLabel = new Label("Status: Venter på bestillinger...");
        logArea.setEditable(false);
        bottomPane.getChildren().addAll(statusLabel, scoreboardLabel, logArea);
        root.setBottom(bottomPane);

        // Opprett og vis scenen
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Restaurant Simulering");
        stage.setScene(scene);
        stage.show();

        // --- Start restaurantsimuleringen ---
        // Opprett simuleringsobjekt med en bestillingskø med kapasitet 5
        simulation = new RestaurantSimulation(5);

        // Registrer og start kokker (f.eks. en kokk spesialisert på PIZZA og en som kan alt)
        Kokk kokk1 = new Kokk("Kokk-1", Måltider.PIZZA, simulation.getBestillingsKø());
        Kokk kokk2 = new Kokk("Kokk-2", simulation.getBestillingsKø());
        simulation.registrerKokk(kokk1);
        simulation.registrerKokk(kokk2);

        // Start kundetråder
        simulation.startKunder();

        // --- Koble kontrollknapper til handlinger ---
        startButton.setOnAction(e -> {
            simulation.startKunder();
            statusLabel.setText("Status: Simulering startet");
            appendLog("Simulering startet.");
        });
        pauseButton.setOnAction(e -> {
            statusLabel.setText("Status: Simulering pausert");
            appendLog("Simulering pausert.");
        });
        stoppButton.setOnAction(e -> {
            statusLabel.setText("Status: Simulering stoppet");
            appendLog("Simulering stoppet.");
            // Her kan du legge til kode for å stoppe alle tråder
        });
        
        // --- Start en AnimationTimer for å oppdatere scoreboard kontinuerlig ---
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateScoreboard();
            }
        }.start();
    }
    
    /**
     * Legg en melding til logArea (GUI) på JavaFX-tråden.
     */
    public static void appendLog(String message) {
        Platform.runLater(() -> logArea.appendText(message + "\n"));
    }
    
    /**
     * Legg til en kunde i den aktive kundelisten.
     */
    public static void addKundeTilListe(String kundeNavn) {
        Platform.runLater(() -> aktiveKunder.add(kundeNavn));
    }
    
    /**
     * Fjern en kunde fra den aktive kundelisten.
     */
    public static void removeKundeFraListe(String kundeNavn) {
        Platform.runLater(() -> aktiveKunder.remove(kundeNavn));
    }
    
    /**
     * Legg til melding i bestillingInfoArea (GUI).
     */
    public static void appendBestillingsinfo(String message) {
        Platform.runLater(() -> bestillingInfoArea.appendText(message + "\n"));
    }
    
    /**
     * Oppdaterer scoreboardLabel med antall fornøyde (happy) og sinte (angry) kunder.
     * Dette forutsetter at simulation har metoder getHappyCount() og getAngryCount().
     */
    private void updateScoreboard() {
        Platform.runLater(() -> {
            scoreboardLabel.setText("Scoreboard: Happy: " + simulation.getHappyCount() +
                                      ", Angry: " + simulation.getAngryCount());
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
<<<<<<< Updated upstream
}
=======

    class venstreVboxController {
        ListView<Kunde> kundeListView = new ListView<>();
        
        public venstreVboxController() {
            kundeListView.setPrefHeight(VINDU_HØYDE / 1.5);
        }

        public void nyKunde() throws InterruptedException {
            try {
                kundeListView.getItems().add(new Kunde(restaurant));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
>>>>>>> Stashed changes
    class venstreVboxController {
        ListView<Kunde> kundeListView = new ListView<>();
        
        public venstreVboxController() {
            kundeListView.setPrefHeight(VINDU_HØYDE / 1.5);
        }

        public void nyKunde() throws InterruptedException {
            try {
                kundeListView.getItems().add(new Kunde(restaurant));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    class midtVboxController {
        ListView<Ordrelinje> underBehandlingListView = new ListView<>();

        public midtVboxController() {
            underBehandlingListView.setPrefHeight(VINDU_HØYDE / 1.5);
        }
    }

    class høyreVboxController {
        ListView<Kokk> kokkListView = new ListView<>();

        public høyreVboxController() {
            kokkListView.setPrefHeight(VINDU_HØYDE / 1.5);
        }

        public void leggTilKokk(Kokk kokk) {
            for (int i = 0; i < 5; i++) {
                kokkListView.getItems().add(kokk);
            }
        }
    }
<<<<<<< Updated upstream
<<<<<<< Updated upstream
}
>>>>>>> Stashed changes
=======
}
>>>>>>> Stashed changes
=======
}
>>>>>>> Stashed changes
