// Kokk.java
package com.restaurantsim;

public class Kokk implements Runnable {

    private final String kokkNavn;
    private final Bestillingskø bestillingsKø;
    private final Måltider spesialisering;
    private final Hentekø hentekø;
    private final RestaurantSimulation simulation;

    private volatile boolean aktiv = true;

    public Kokk(String kokkNavn, Bestillingskø bestillingsKø) {
        this(kokkNavn, null, bestillingsKø, null, null);
    }

    public Kokk(String kokkNavn, Måltider spesialisering, Bestillingskø bestillingsKø) {
        this(kokkNavn, spesialisering, bestillingsKø, null, null);
    }

    public Kokk(String kokkNavn, Måltider spesialisering, Bestillingskø bestillingsKø, Hentekø hentekø, RestaurantSimulation simulation) {
        this.kokkNavn = kokkNavn;
        this.spesialisering = spesialisering;
        this.bestillingsKø = bestillingsKø;
        this.hentekø = hentekø;
        this.simulation = simulation;
    }

    public Kokk(String kokkNavn, Bestillingskø bestillingsKø, Hentekø hentekø, RestaurantSimulation simulation) {
        this(kokkNavn, null, bestillingsKø, hentekø, simulation);
    }

    public void stop() {
        aktiv = false;
    }

    @Override
    public void run() {
        while (aktiv) {
            try {
                
                if (bestillingsKø.erTom()) {
                    Bestilling best = bestillingsKø.hentBestilling();
                    // ⏳ Under arbeid
                    String underArbeid = kokkNavn + " ⏳ lager " + best.getMåltid() + " for kunde " + best.getKundeId();
                    LoggerUtil.loggTilFil("[Kokk " + kokkNavn + "] Tilbereder " + best.getMåltid() + " for kunde " + best.getKundeId());
                    App.appendBestillingsinfo(underArbeid);
        
                    Thread.sleep(best.getMåltid().getTilberedningstid()); // Simuler tilberedning
        
                    // ✅ Ferdig
                    String ferdig = kokkNavn + " ✅ ferdig for kunde " + best.getKundeId();
                    App.appendLog("[Kokk " + kokkNavn + "] Ferdig med bestilling for kunde " + best.getKundeId());
                    LoggerUtil.loggTilFil("[Kokk " + kokkNavn + "] Ferdig med bestilling for kunde " + best.getKundeId());
                    App.appendBestillingsinfo(ferdig);
                    hentekø.leggTilHenteKø(best);
                } else {
                    App.appendLog("[Kokk " + kokkNavn + "] Køen er tom. Venter.");
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
}