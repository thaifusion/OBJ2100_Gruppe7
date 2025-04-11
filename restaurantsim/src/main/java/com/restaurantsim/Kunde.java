package com.restaurantsim;

public class Kunde implements Runnable {

    private final int kundeId;
    private final Måltider ønsketMåltid;
    private final Bestillingskø bestillingsKø;
    private final long bestillingstid;
    private final RestaurantSimulation simulation;

    public Kunde(int kundeId, Måltider ønsketMåltid, long bestillingstid,
                 Bestillingskø bestillingsKø, RestaurantSimulation simulation) {
        this.kundeId = kundeId;
        this.ønsketMåltid = ønsketMåltid;
        this.bestillingsKø = bestillingsKø;
        this.bestillingstid = bestillingstid;
        this.simulation = simulation;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted() && simulation.kjører()) {
                while (simulation.pausert()) {
                    Thread.sleep(500);
                }
                
                Bestilling best = new Bestilling(kundeId, ønsketMåltid, bestillingstid);

                String melding = "Kunde " + kundeId + " ønsker " + ønsketMåltid;
                App.appendLog(melding);
                App.appendBestillingsinfo(melding);
                if (bestillingsKø.erFull() == true) {
                    App.appendLog("Bestillingskø er full, kunde " + kundeId + " venter.");
                } else {
                    App.addKundeTilListe(melding);
                    bestillingsKø.leggTilBestilling(best);
                    App.appendLog("Kunde " + kundeId + " venter på " + ønsketMåltid);
                    System.out.println("Antall ordre i køen: " + bestillingsKø.antallBestillinger());
                }



                // Vente på at kokken legger ferdig bestilling tilbake i køen
                while (true) {
                    best = bestillingsKø.hentBestilling();
                    if (best.getKundeId() == kundeId) {
                        App.appendLog("Kunde " + kundeId + " mottok sin bestilling: " + best.getMåltid());
                        App.removeKundeFraListe(melding);
                        
                        break;
                    }
                    Thread.sleep(500);
                }

                break; // Ferdig med løkken etter henting
            }
        } catch (InterruptedException e) {
            App.appendLog("Kunde " + kundeId + " ble avbrutt.");
            Thread.currentThread().interrupt();
        }
    }

    public void interrupt() {
        Thread.currentThread().interrupt();
    }
}