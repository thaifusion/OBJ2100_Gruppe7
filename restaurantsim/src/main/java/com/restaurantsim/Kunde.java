package com.restaurantsim;

 

public class Kunde implements Runnable {

    private final int kundeId;
    private final Måltider ønsketMåltid;
    private final Bestillingskø bestillingsKø;
    private final long bestillingstid;
    private final Hentekø henteKø;
    private final RestaurantSimulation simulation;

    public Kunde(int kundeId, Måltider ønsketMåltid, long bestillingstid, Bestillingskø bestillingsKø, Hentekø henteKø, RestaurantSimulation simulation) {
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
            while(!Thread.currentThread().isInterrupted() && simulation.kjører()) {
                while (simulation.pausert()) {
                    Thread.sleep(500);
                }
                Bestilling best = new Bestilling(kundeId, ønsketMåltid, bestillingstid);
                App.appendLog("Kunde " + kundeId + " la inn bestilling på " + ønsketMåltid);
                LoggerUtil.loggTilFil("Kunde " + kundeId + " la inn bestilling på " + ønsketMåltid);
                bestillingsKø.leggTilBestilling(best);
                App.appendLog("Kunde " + kundeId + " venter på " + ønsketMåltid);
                while (true) {
                    Bestilling ferdigBestilling = henteKø.kundeHentBestilling();
                    if (ferdigBestilling.getKundeId() == kundeId) {
                        App.appendLog("Kunde " + kundeId + " mottok sin bestilling: " + ferdigBestilling);
                        LoggerUtil.loggTilFil("Kunde " + kundeId + " mottok sin bestilling: " + ferdigBestilling);
                        App.removeKundeFraListe("kunde-" + kundeId);

                        break;
                    }
                    Thread.sleep(1000);
                }
                
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } 
    }

    public void interrupt() {
        Thread.currentThread().interrupt();
    }
}
