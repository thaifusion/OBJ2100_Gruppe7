package com.restaurantsim;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private BorderPane pane = new BorderPane();
    private Scene scene;
    private Bestilling bestilling1;
    private Kunde kunde1;
    private Maltider måltid1;


    @Override
    public void start(Stage stage) throws IOException {
        måltid1 = new Maltider("Spagetti", 10);
        bestilling1 = new Bestilling(kunde1, måltid1, 10);
        kunde1 = new Kunde(1, 10, bestilling1);
        System.out.println(kunde1.getKundeId()
        );

        // 1) Opprett en Bestillingskø med kapasitet 5.
        //Bestillingskø bestillingsKø = new Bestillingskø(5);

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }

}