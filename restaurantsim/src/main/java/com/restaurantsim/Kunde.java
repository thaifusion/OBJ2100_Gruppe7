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
            // Opprett bestilling
            Bestilling best = new Bestilling(kundeId, ønsketMåltid, bestillingstid);

            String melding = "🍽️ Kunde #" + kundeId + " ønsker \"" + ønsketMåltid + "\"";
            App.appendLog(melding);
            App.appendBestillingsinfo(melding);

            bestillingsKø.leggTilBestilling(best);

            System.out.println(melding + " – lagt i køen");

            // Simuler at kunden venter
            Thread.sleep(2000);
            App.appendLog("⏳ Kunde #" + kundeId + " har ventet og er fortsatt i restauranten...");

        } catch (InterruptedException e) {
            App.appendLog("Kunde #" + kundeId + " ble avbrutt: " + e.getMessage());
            System.err.println("Kunde #" + kundeId + " avbrutt: " + e.getMessage());
        }
    }
    
    public void interrupt() {
        Thread.currentThread().interrupt();  // Merk: Denne "avbryter" bare nåværende tråd, bør brukes med kontroll
    }
}