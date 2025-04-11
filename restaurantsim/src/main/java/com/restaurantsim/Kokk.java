// Kokk.java
package com.restaurantsim;

public class Kokk implements Runnable {

    private final String kokkNavn;
    private final Bestillingskø bestillingskø;
    private final Bestillingskø hentekø;
    private final Måltider spesialisering;
    private final RestaurantSimulation simulation;
    private long tilberedningstid;
    private volatile boolean erOpptatt = false;

    public Kokk(String kokkNavn, Måltider spesialisering, Bestillingskø bestillingskø, Bestillingskø hentekø, RestaurantSimulation simulation) {
        this.kokkNavn = kokkNavn;
        this.spesialisering = spesialisering;
        this.bestillingskø = bestillingskø;
        this.hentekø = hentekø;
        this.simulation = simulation;
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && simulation.kjører()) {
            try {
                Bestilling best = bestillingskø.hentBestilling();
               

                // Hvis dette er allround-kokk, sjekk om en spesialkokk kan ta bestillingen
                if (spesialisering == null) {
                    if (simulation.finnesLedigSpesialist(best.getMåltid())) {
                        bestillingskø.leggTilBestilling(best);
                        Thread.sleep(200); // Gir spesialkokker en liten fordel
                        continue;
                    }
                }    

                // Hvis det er riktig kokk for retten
                if (spesialisering == null || best.getMåltid() == spesialisering) { 
                    erOpptatt = true;
    
                    // ⏳ Under arbeid
                    String underArbeid = kokkNavn + " ⏳ lager " + best.getMåltid() + " for kunde " + best.getKundeId();
                    LoggerUtil.loggTilFil("[Kokk " + kokkNavn + "] Tilbereder " + best.getMåltid() + " for kunde " + best.getKundeId());
                    App.appendBestillingsinfo(underArbeid);
                    tilberedningstid += best.getMåltid().getTilberedningstid();
                    Thread.sleep(tilberedningstid); // Lager mat

                    // ✅ Ferdig
                    String ferdig = kokkNavn + " ✅ ferdig for kunde " + best.getKundeId();
                    LoggerUtil.loggTilFil("[Kokk " + kokkNavn + "] Ferdig med bestilling for kunde " + best.getKundeId());
                    App.appendLog("[Kokk " + kokkNavn + "] Ferdig med bestilling for kunde " + best.getKundeId());
                    App.appendBestillingsinfo(ferdig);
                    hentekø.leggTilBestillingHentekø(best);


                    // Kokk tar en liten pause etter retten
                    Thread.sleep(5000);
                    
                    erOpptatt = false;
                } else {
                    // Hvis bestillingen ikke samsvarer med kokkens spesialitet, legg den tilbake i køen
                    bestillingskø.leggTilBestilling(best);
                    Thread.sleep(200); // Unngå busy waiting
                }

            } catch (InterruptedException e) {
                App.appendLog("[Kokk " + kokkNavn + "] Avbrutt. Avslutter kokketråden.");
                LoggerUtil.loggTilFil("[Kokk " + kokkNavn + "] Avbrutt. Avslutter kokketråden.");
                break;
            }
        }
    }



    public Måltider getSpesialisering() {
        return spesialisering;
    }

    public String getKokkNavn() {
        return kokkNavn;
    }

    public boolean erLedig() {
        return !erOpptatt;
    }

    public boolean pause() throws InterruptedException {
        boolean pause = true;
        long tid = 5000;
        String melding;
        if (pause) {
            Thread.sleep(tid);
            melding = kokkNavn + " tar en pause på 5 sekunder.";
            tilberedningstid += tid;
        }
        return pause;
    }
}