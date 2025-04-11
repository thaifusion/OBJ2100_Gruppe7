package com.restaurantsim;

public class Kunde implements Runnable {

    private final int kundeId;
    private final Måltider ønsketMåltid;
    private final Bestillingskø bestillingskø;
    private final Bestillingskø hentekø;
    private final long bestillingstid;
    private final RestaurantSimulation simulation;

    public Kunde(int kundeId, Måltider ønsketMåltid, long bestillingstid,
                 Bestillingskø bestillingskø, Bestillingskø hentekø, RestaurantSimulation simulation) {
        this.kundeId = kundeId;
        this.ønsketMåltid = ønsketMåltid;
        this.bestillingskø = bestillingskø;
        this.hentekø = hentekø;
        this.bestillingstid = bestillingstid;
        this.simulation = simulation;
    }

    @Override
    public void run() {
        try {
            // Sjekker om simuleringen kjører og ikke avbrutt
            while (!Thread.currentThread().isInterrupted() && simulation.kjører()) {

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
                bestillingskø.leggTilBestilling(best);
                App.appendLog("Kunde " + kundeId + " venter på " + ønsketMåltid);
                long ventetid = ønsketMåltid.getTilberedningstid();
                Thread.sleep(ventetid);


                // Vente på at kokken legger ferdig bestilling i henteKø
                while (true) {
                    Bestilling ferdigBestilling = hentekø.hentBestillingHentekø();

                    if (ferdigBestilling.getKundeId() != kundeId) {
                        bestillingskø.leggTilBestilling(ferdigBestilling);
            
                        // 😊 eller 😠 basert på ventetid
                        if (ventetid <= 16000) {
                            App.appendLog("Kunde " + best.getKundeId() + " er 😊 fornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                            LoggerUtil.loggTilFil("Kunde " + best.getKundeId() + " er 😊 fornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                            App.appendLog("Kunde " + kundeId + " mottok sin bestilling: " + ferdigBestilling);
                            App.simulation.incrementHappy(); 
                        } else {
                            App.appendLog("Kunde " + best.getKundeId() + " er 😠 misfornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                            LoggerUtil.loggTilFil("Kunde " + best.getKundeId() + " er 😠 misfornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                            App.simulation.incrementAngry(); 
                        }
                        Thread.sleep(100);
                        continue;
                    }

                    // Hvis det er min mat!
                    
                    App.removeKundeFraListe(kundeTekst);
                    System.out.println("? Fjerner fra liste: " + kundeTekst);
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