package com.restaurantsim;



import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    // Referanse til loggvisningen som vi kan oppdatere fra andre tråder.
    private static TextArea logArea = new TextArea();
    private static TextArea bestillingInfoArea = new TextArea();
   
    
    // Lag en liste for å holde kundene
    private static ObservableList<String> aktiveKunder = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        // 1) Opprett et BorderPane som hovedlayout
        BorderPane root = new BorderPane();

        //2)  Top: Menylinje med tittel og kontrollknapper
        HBox topPane = new HBox();
        topPane.setSpacing(10);
        Label title = new Label("Restaurant Simulering");
        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");
        Button stoppButton = new Button("Stopp");
        topPane.getChildren().addAll(title, startButton, pauseButton, stoppButton);
        root.setTop(topPane);

         
        //3)  Left: Liste over aktive kunder (eksempel)
        VBox leftPane = new VBox();
        leftPane.setSpacing(10);
        Label kunderLabel = new Label("Aktive kunder:");
        ListView<String> kunderListView = new ListView<>(aktiveKunder);
        leftPane.getChildren().addAll(kunderLabel, kunderListView);
        root.setLeft(leftPane);

        // Center: Canvas for simulering (kan senere utvides med animasjon)
        // For nå holder vi det tomt.
        // Du kan f.eks. bruke et Canvas for visuelle oppdateringer.
        // root.setCenter(canvas);

        // 5) Right: Informasjonspanel for bestillingsinfo og kokkestatus
        VBox rightPane = new VBox(10);
          Label ordreLabel = new Label("Bestillingsinfo:");
          TextArea bestillingInfoArea = new TextArea();
          bestillingInfoArea.setEditable(false);
          rightPane.getChildren().addAll(ordreLabel, bestillingInfoArea);
          root.setRight(rightPane);


        // 6) Bottom: Statuslinje og loggvisning
        VBox bottomPane = new VBox(5);
        Label statusLabel = new Label("Status: Venter på bestillinger...");
        logArea.setEditable(false); // Gjør loggvisningen skrivebeskyttet
        bottomPane.getChildren().addAll(statusLabel, logArea);
        root.setBottom(bottomPane);
        
      

        // 7) Opprett scenen og vis den
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Restaurant Simulering");
        stage.setScene(scene);
        stage.show();

        // 8) Start restaurantsimuleringen (opprett og start tråder)
        RestaurantSimulation simulation = new RestaurantSimulation(5);
        
        // Registrer og start kokker
        Kokk kokk1 = new Kokk("Kokk-1", Måltider.PIZZA, simulation.getBestillingsKø());
        Kokk kokk2 = new Kokk("Kokk-2", null, simulation.getBestillingsKø());
        simulation.registrerKokk(kokk1);
        simulation.registrerKokk(kokk2);
        
         // Start kunder
        simulation.startKunder();
        

        // Koble kontrollknapper (eksempel)
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
            // Her kan du legge til kode for å stoppe alle tråder.
        });
    }

    /**
     * Metode for å oppdatere loggvisningen.
     * @param message
     */
    public static void appendLog(String message) {
        // Platform.runLater sikrer at vi oppdaterer GUI på JavaFX-tråden.
        Platform.runLater(() -> {
            logArea.appendText(message + "\n");
        });
    }
    
    public static void addKundeTilListe(String kundeNavn) {
    Platform.runLater(() -> {
        aktiveKunder.add(kundeNavn);
    });
}

    public static void removeKundeFraListe(String kundeNavn) {
    Platform.runLater(() -> {
        aktiveKunder.remove(kundeNavn);
    });
}

    public static void appendBestillingsinfo(String message) {
    Platform.runLater(() -> {
        bestillingInfoArea.appendText(message + "\n");
    });
}
    
    

    

    
    public static void main(String[] args) {
        launch(args);
    }
}
