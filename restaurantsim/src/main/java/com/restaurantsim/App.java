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



    @Override
    public void start(Stage stage) throws IOException {

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