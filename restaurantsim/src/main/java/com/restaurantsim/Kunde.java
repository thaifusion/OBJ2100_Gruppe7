package com.restaurantsim;

public class Kunde implements Runnable {

    private final int kundeId;
    private final Måltider ønsketMåltid;
    private final Bestillingskø bestillingsKø;
    private final long bestillingstid;
    private final Hentekø henteKø;
    private final RestaurantSimulation simulation;

    public Kunde(int kundeId, Måltider ønsketMåltid, long bestillingstid,
                 Bestillingskø bestillingsKø, Hentekø henteKø, RestaurantSimulation simulation) {
        this.kundeId = kundeId;
        this.ønsketMåltid = ønsketMåltid;
        this.bestillingsKø = bestillingsKø;
        this.bestillingstid = bestillingstid;
        this.henteKø = henteKø;
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

                String melding = "Kunde #" + kundeId + " ønsker \"" + ønsketMåltid + "\"";
                App.appendLog(melding);
                App.appendBestillingsinfo(melding);

                bestillingsKø.leggTilBestilling(best);
                App.appendLog("Kunde #" + kundeId + " venter på " + ønsketMåltid);

                // Vente på at kokken legger ferdig bestilling i henteKø
                while (true) {
                    Bestilling ferdigBestilling = henteKø.kundeHentBestilling();
                    if (ferdigBestilling.getKundeId() == kundeId) {
                        App.appendLog("Kunde #" + kundeId + " mottok sin bestilling: " + ferdigBestilling.getMåltid());
                        App.removeKundeFraListe("Kunde " + kundeId + " ønsker " + ønsketMåltid);
                        
                        break;
                    }
                    Thread.sleep(500);
                }

                break; // Ferdig med løkken etter henting
            }
        } catch (InterruptedException e) {
            App.appendLog("Kunde #" + kundeId + " ble avbrutt.");
            Thread.currentThread().interrupt();
        }
    }

    public void interrupt() {
        Thread.currentThread().interrupt();
    }
}