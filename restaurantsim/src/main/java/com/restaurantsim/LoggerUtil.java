package com.restaurantsim;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;


public class LoggerUtil {
    private static final String FILNAVN = "restaurant_log.txt";

    public static void loggTilFil(String melding) {
        try (FileWriter writer = new FileWriter(FILNAVN, true)) {
            writer.write(LocalDateTime.now() + " - " + melding + "\n");
        } catch (IOException e) {
            System.err.println("Feil ved skriving til loggfil: " + e.getMessage());
        }
    }

    public static void tømLogg() {
        try (FileWriter writer = new FileWriter(FILNAVN, false)) {
            writer.write(""); // Tømmer innholdet i filen
        } catch (IOException e) {
            System.err.println("Feil ved tømming av loggfil: " + e.getMessage());
        }
    }
}
