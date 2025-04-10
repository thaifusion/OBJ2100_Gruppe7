package com.restaurantsim;

public class Kunde implements Runnable {

    private final int kundeId;
    private final Måltider ønsketMåltid;
    private final Bestillingskø bestillingsKø;
    private final long maksimalVentetid;
    private final long bestillingstid;
    private final Hentekø henteKø;
    private final RestaurantSimulation simulation;

    public Kunde(int kundeId, Måltider ønsketMåltid, long bestillingstid,
                 Bestillingskø bestillingsKø, Hentekø henteKø, RestaurantSimulation simulation, long maksimalVentetid) {
        this.kundeId = kundeId;
        this.ønsketMåltid = ønsketMåltid;
        this.bestillingsKø = bestillingsKø;
        this.bestillingstid = bestillingstid;
        this.henteKø = henteKø;
        this.simulation = simulation;
        this.maksimalVentetid = (long)(Math.random() * (15000 - 5000) + 5000);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted() && simulation.kjører()) {
                

                Bestilling best = new Bestilling(kundeId, ønsketMåltid, System.currentTimeMillis());

                String melding = "Kunde #" + kundeId + " ønsker \"" + ønsketMåltid + "\"";
                App.appendLog(melding);

                bestillingsKø.leggTilBestilling(best);
                App.appendLog("Kunde #" + kundeId + " venter på " + ønsketMåltid);

                // Vente på at kokken legger ferdig bestilling i henteKø
                while (true) {
                    Bestilling ferdigBestilling = henteKø.kundeHentBestilling();
                    if (best.getKundeId() == kundeId) {
                        App.appendLog("Kunde #" + kundeId + " mottok sin bestilling: " + best.getMåltid());
                        App.removeKundeFraListe("Kunde " + kundeId + " ønsker " + ønsketMåltid);

                        long nåTid = System.currentTimeMillis();
                        long ventetid = nåTid - best.getBestillingstid();
                    
                        // 😊 eller 😠 basert på ventetid
                        if (ventetid <= maksimalVentetid) {
                            App.appendLog("Kunde " + best.getKundeId() + " er 😊 fornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                            LoggerUtil.loggTilFil("Kunde " + best.getKundeId() + " er 😊 fornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                            App.simulation.incrementHappy(); 
                        } else {
                            App.appendLog("Kunde " + best.getKundeId() + " er 😠 misfornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                            LoggerUtil.loggTilFil("Kunde " + best.getKundeId() + " er 😠 misfornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                            App.simulation.incrementAngry(); 
                        }
                    }
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            App.appendLog("Kunde #" + kundeId + " ble avbrutt.");
            Thread.currentThread().interrupt();
        }
    }



    public void interrupt() {
        Thread.currentThread().interrupt();
    }

    public long getMaksimalVentetid() {
        return maksimalVentetid;
    }
}