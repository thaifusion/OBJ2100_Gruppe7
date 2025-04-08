package com.restaurantsim;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoggViewer {

    public static void visLoggVindu() {
        Stage loggVindu = new Stage();
        loggVindu.setTitle("Loggfil - restaurant_log.txt");

        TextArea tekstArea = new TextArea();
        tekstArea.setEditable(false);
        tekstArea.setWrapText(true);
        tekstArea.setPrefSize(600, 400);
        tekstArea.setFont(Font.font("Consolas", 12));

        try {
            String innhold = Files.readString(Paths.get("restaurant_log.txt"));
            tekstArea.setText(innhold);
        } catch (IOException e) {
            tekstArea.setText("Kunne ikke lese loggfilen: " + e.getMessage());
        }

        VBox layout = new VBox(tekstArea);
        layout.setPadding(new Insets(10));
        Scene scene = new Scene(layout);
        loggVindu.setScene(scene);
        loggVindu.show();
        System.out.println("Antall aktive tråder: " + Thread.activeCount());
    }
}
