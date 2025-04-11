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
            // Sjekker om simuleringen kjører og ikke avbrutt
            while (!Thread.currentThread().isInterrupted() && simulation.kjører()) {

                // Pausehåndtering
                while (simulation.pausert()) {
                    Thread.sleep(500);
                }

                // Lager bestillingen
                Bestilling best = new Bestilling(kundeId, ønsketMåltid, bestillingstid);

                // Legg til GUI-liste (en gang)
                String kundeTekst = "Kunde " + kundeId + " ønsker " + ønsketMåltid;
                App.addKundeTilListe(kundeTekst);
                System.out.println("? Legger til i liste: " + kundeTekst);

                // Loggfør Bestilling
                App.appendLog(kundeTekst);
                App.appendBestillingsinfo(kundeTekst);

                // Legg bestilling i bestillingskøen
                bestillingsKø.leggTilBestilling(best);
                App.appendLog("Kunde " + kundeId + " venter på " + ønsketMåltid);

                // Vente på at kokken legger ferdig bestilling i henteKø
                while (true) {
                    Bestilling ferdigBestilling = henteKø.kundeHentBestilling();

                    if (ferdigBestilling.getKundeId() != kundeId) {
                        henteKø.leggTilHenteKø(ferdigBestilling);
                        Thread.sleep(100);
                        continue;
                    }

                     // Hvis det er min mat!
                     App.appendLog("Kunde " + kundeId + " mottok sin bestilling: " + ferdigBestilling.getMåltid());
                     App.removeKundeFraListe(kundeTekst);
                     System.out.println("? Fjerner fra liste: " + kundeTekst);
                     
                     // Beregner ventetid
                     long ventetid = System.currentTimeMillis() - ferdigBestilling.getBestillingstid();
                     
                     // :) eller :( basert på ventetid)
                     if (ventetid <= 16000) {
                        App.appendLog("Kunde " + kundeId + " er 😊 fornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                        LoggerUtil.loggTilFil("Kunde " + kundeId + " er 😊 fornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                        App.simulation.incrementHappy(); 
                    } else {
                        App.appendLog("Kunde " + kundeId + " er 😠 misfornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                        LoggerUtil.loggTilFil("Kunde " + kundeId + " er 😠 misfornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                        App.simulation.incrementAngry(); 
                    }
                     break;
                 }
                    break;
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