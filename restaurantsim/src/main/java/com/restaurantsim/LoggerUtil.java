package com.restaurantsim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LoggerUtil {
    private static final String FILNAVN = "restaurant_log.txt";

    static {
        // Sørg for at filen eksisterer ved oppstart
        File fil = new File(FILNAVN);
        if (!fil.exists()) {
            try {
                fil.createNewFile();
            } catch (IOException e) {
                System.err.println("Klarte ikke å opprette loggfilen: " + e.getMessage());
            }
        }
    }

    public static void loggTilFil(String melding) {
        try (FileWriter writer = new FileWriter(FILNAVN, true)) {
            writer.write(LocalDateTime.now() + " - " + melding + "\n");
        } catch (IOException e) {
            System.err.println("Feil ved skriving til loggfil: " + e.getMessage());
        }
    }

    public static void tømLogg() {
        try (FileWriter writer = new FileWriter(FILNAVN, false)) {
            writer.write(""); // Tømmer innholdet
        } catch (IOException e) {
            System.err.println("Feil ved tømming av loggfil: " + e.getMessage());
        }
    }
}