// Kokk.java
package com.restaurantsim;

public class Kokk implements Runnable {

    private final String kokkNavn;
    private final Bestillingskø bestillingsKø;
    private final Måltider spesialisering;
    private final Hentekø hentekø;
    private final RestaurantSimulation simulation;
    private volatile boolean erOpptatt = false;

    private volatile boolean aktiv = true;

    public Kokk(String kokkNavn, Måltider spesialisering, Bestillingskø bestillingsKø, Hentekø hentekø, RestaurantSimulation simulation) {
        this.kokkNavn = kokkNavn;
        this.spesialisering = spesialisering;
        this.bestillingsKø = bestillingsKø;
        this.hentekø = hentekø;
        this.simulation = simulation;
    }

    public void stop() {
        aktiv = false;
    }

    @Override
    public void run() {
        while (aktiv) {
            try {
                Bestilling best = bestillingsKø.hentBestilling();

                // Hvis dette er allround-kokk, sjekk om en spesialkokk kan ta bestillingen
                if (spesialisering == null) {
                    if (simulation.finnesLedigSpesialist(best.getMåltid())) {
                        bestillingsKø.leggTilBestilling(best);
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
    
                Thread.sleep(best.getMåltid().getTilberedningstid()); // Lager mat

                // ✅ Ferdig
                String ferdig = kokkNavn + " ✅ ferdig for kunde " + best.getKundeId();
                LoggerUtil.loggTilFil("[Kokk " + kokkNavn + "] Ferdig med bestilling for kunde " + best.getKundeId());
                App.appendLog("[Kokk " + kokkNavn + "] Ferdig med bestilling for kunde " + best.getKundeId());
                App.appendBestillingsinfo(ferdig);
                hentekø.leggTilHenteKø(best);
    
                long nåTid = System.currentTimeMillis();
                long ventetid = nåTid - best.getBestillingstid();
    
                // 😊 eller 😠 basert på ventetid
                if (ventetid <= 16000) {
                    App.appendLog("Kunde " + best.getKundeId() + " er 😊 fornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                    LoggerUtil.loggTilFil("Kunde " + best.getKundeId() + " er 😊 fornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                    App.simulation.incrementHappy(); 
                } else {
                    App.appendLog("Kunde " + best.getKundeId() + " er 😠 misfornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                    LoggerUtil.loggTilFil("Kunde " + best.getKundeId() + " er 😠 misfornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                    App.simulation.incrementAngry(); 
                }

                // Kokk tar en liten pause etter retten
                Thread.sleep(500);

                erOpptatt = false;

                
                } else {
                    // Hvis bestillingen ikke samsvarer med kokkens spesialitet, legg den tilbake i køen
                    bestillingsKø.leggTilBestilling(best);
                    Thread.sleep(1000); // Unngå busy waiting
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
}