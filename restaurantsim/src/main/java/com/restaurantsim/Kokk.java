package com.restaurantsim;

import com.restaurantsim.LoggerUtil;


public class Kokk implements Runnable {

    private final String kokkNavn;
    private final Bestillingskø bestillingsKø;
    private Måltider spesialisering;

    public Kokk(String kokkNavn, Bestillingskø bestillingsKø) {
        this.kokkNavn = kokkNavn;
        this.bestillingsKø = bestillingsKø;
    }
    
public Kokk(String kokkNavn, Bestillingskø bestillingsKø, Måltider spesialisering) {
    this.kokkNavn = kokkNavn;
    this.bestillingsKø = bestillingsKø;
    this.spesialisering = spesialisering;
}

public Kokk(String kokkNavn, Måltider spesialisering, Bestillingskø bestillingsKø) {
    this.kokkNavn = kokkNavn;
    this.spesialisering = spesialisering;
    this.bestillingsKø = bestillingsKø;
}

@Override
public void run() {
    while (true) {
        try {
            // Hent neste bestilling (blokkerer om køen er tom).
            Bestilling best = bestillingsKø.hentBestilling();

            // Logg når kokken starter
            App.appendLog("[Kokk " + kokkNavn + "] Tilbereder " + best.getMåltid() + " for kunde " + best.getKundeId());
            LoggerUtil.loggTilFil("[Kokk " + kokkNavn + "] Tilbereder " + best.getMåltid() + " for kunde " + best.getKundeId());

            App.appendBestillingsinfo(kokkNavn + " → lager " + best.getMåltid() + " for kunde " + best.getKundeId());

            // Valgfritt: behold eller fjern denne
            //System.out.println(kokkNavn + " tilbereder: " + best);
            
            // Simuler tilberedningstid (10 sek).
            Thread.sleep(10000);

            // Ferdig melding
            App.appendLog("[Kokk " + kokkNavn + "] Ferdig med bestilling for kunde " + best.getKundeId());
            App.appendBestillingsinfo(kokkNavn + " ✓ ferdig for kunde " + best.getKundeId());

            // Regn ut ventetid
            long nåTid = System.currentTimeMillis();
            long ventetid = nåTid - best.getBestillingstid(); // i millisekunder

            // Bestem happy vs. angry (grense: 12 sekunder = 12000 ms)
            if (ventetid <= 12000) {
                App.appendLog("Kunde " + best.getKundeId() + " er 😊 fornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                App.simulation.incrementHappy(); // 
            } else {
                App.appendLog("Kunde " + best.getKundeId() + " er 😠 misfornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                App.simulation.incrementAngry(); 
            }

        } catch (InterruptedException e) {
            App.appendLog("[Kokk " + kokkNavn + "] Avbrutt. Avslutter kokketråden.");
            break;
        }
    }
}

    public Måltider getSpesialisering() {
    return spesialisering;
}


}
