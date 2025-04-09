package com.restaurantsim;


public class Kokk implements Runnable {

    private final String kokkNavn;
    private final Bestillingskø bestillingsKø;
    private final Hentekø henteKø;
    private Måltider spesialisering;
    private final RestaurantSimulation simulation;

    final int TÅLEGRENSE = 15000; // 15 sekunder

    public Kokk(String kokkNavn, Bestillingskø bestillingsKø, Hentekø henteKø, RestaurantSimulation simulation) {
        this.kokkNavn = kokkNavn;
        this.bestillingsKø = bestillingsKø;
        this.henteKø = henteKø;
        this.simulation = simulation;
    }
    
    public Kokk(String kokkNavn, Bestillingskø bestillingsKø, Hentekø henteKø, Måltider spesialisering, RestaurantSimulation simulation) {
        this.kokkNavn = kokkNavn;
        this.bestillingsKø = bestillingsKø;
        this.henteKø = henteKø;
        this.spesialisering = spesialisering;
        this.simulation = simulation;
    }
           
    @Override 
    public void run() {
        while (!Thread.currentThread().isInterrupted() && simulation.kjører()) {
            try {
                Bestilling best = bestillingsKø.hentBestilling();
    
                // ⏳ Under arbeid
                String underArbeid = kokkNavn + " ⏳ lager " + best.getMåltid() + " for kunde " + best.getKundeId();
                App.appendLog("[Kokk " + kokkNavn + "] Tilbereder " + best.getMåltid() + " for kunde " + best.getKundeId());
                LoggerUtil.loggTilFil("[Kokk " + kokkNavn + "] Tilbereder " + best.getMåltid() + " for kunde " + best.getKundeId());
                App.appendBestillingsinfo(underArbeid);
    
                Thread.sleep(10000); // Simuler tilberedning
    
                // ✅ Ferdig
                String ferdig = kokkNavn + " ✅ ferdig for kunde " + best.getKundeId();
                App.appendLog("[Kokk " + kokkNavn + "] Ferdig med bestilling for kunde " + best.getKundeId());
                App.appendBestillingsinfo(ferdig);
                henteKø.leggTilHenteKø(best);
    
                long nåTid = System.currentTimeMillis();
                long ventetid = nåTid - best.getBestillingstid();
    
                // 😊 eller 😠 basert på ventetid
                if (ventetid <= 12000) {
                    App.appendLog("Kunde " + best.getKundeId() + " er 😊 fornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                    App.simulation.incrementHappy(); 
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

    public String getKokkNavn() {
        return kokkNavn;
    }

    public void interrupt() {
        Thread.currentThread().interrupt();
    }
}
