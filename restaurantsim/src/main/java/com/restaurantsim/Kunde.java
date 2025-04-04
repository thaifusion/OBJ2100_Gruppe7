package com.restaurantsim;

 

public class Kunde implements Runnable {

    private final int kundeId;
    private final Måltider ønsketMåltid;
    private final Bestillingskø bestillingsKø;
    private final Hentekø henteKø;
    private final RestaurantSimulation simulation;

    public Kunde(int kundeId, Måltider ønsketMåltid, Bestillingskø bestillingsKø, Hentekø henteKø, RestaurantSimulation simulation) {
        this.kundeId = kundeId;
        this.ønsketMåltid = ønsketMåltid;
        this.bestillingsKø = bestillingsKø;
        this.henteKø = henteKø;
        this.simulation = simulation;
    }
    
    

    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted() && simulation.kjører()) {
                while (simulation.pausert()) {
                    Thread.sleep(500);
                }
                Bestilling best = new Bestilling(kundeId, ønsketMåltid);
                App.appendLog("Kunde " + kundeId + " la inn bestilling på " + ønsketMåltid);
                App.appendBestillingsinfo("Kunde " + kundeId + " la inn bestilling på " + ønsketMåltid);
                bestillingsKø.leggTilBestilling(best);
                App.appendLog("Kunde " + kundeId + " venter på " + ønsketMåltid);
                while (true) {
                    Bestilling ferdigBestilling = henteKø.kundeHentBestilling();
                    if (ferdigBestilling.getKundeId() == kundeId) {
                        App.appendLog("Kunde " + kundeId + " mottokk sin bestilling: " + ferdigBestilling);
                        break;
                    }
                    Thread.sleep(100);
                }
                App.removeKundeFraListe("Kunde " + kundeId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            App.removeKundeFraListe("Kunde " + kundeId);
        }
    }

    public void interrupt() {
        Thread.currentThread().interrupt();
    }
}
