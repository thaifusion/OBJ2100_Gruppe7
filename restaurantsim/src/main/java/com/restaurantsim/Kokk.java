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
            simulation.sjekkPause(); // Sjekk om simuleringen er pausert
            Bestilling best = bestillingsKø.forsokHentBestillingMedTimeout(1); // vent maks 1 sek

            if (best == null) {
                continue; // Ingenting i køen akkurat nå – prøv igjen
            }

            // Hvis kokken har en spesialisering og den ikke matcher, hopp over
            if (spesialisering != null && best.getMåltid() != spesialisering) {
                // Legg tilbake i køen for andre kokker (bare hvis du absolutt vil)
                bestillingsKø.leggTilBestilling(best); // ← eller bare ikke gjør noe, den ligger jo fortsatt der
                Thread.sleep(500); // Kort pause før ny sjekk
                continue;
            }

            // --- Kokken kan lage retten ---
            App.appendLog("[Kokk " + kokkNavn + "] Tilbereder " + best.getMåltid() + " for kunde " + best.getKundeId());
            LoggerUtil.loggTilFil("[Kokk " + kokkNavn + "] Tilbereder " + best.getMåltid() + " for kunde " + best.getKundeId());
            App.appendBestillingsinfo(kokkNavn + " → lager " + best.getMåltid() + " for kunde " + best.getKundeId());

            Thread.sleep(best.getMåltid().getTilberedningstid()); // Tilberedningstid

            App.appendLog("[Kokk " + kokkNavn + "] Ferdig med bestilling for kunde " + best.getKundeId());
            App.appendBestillingsinfo(kokkNavn + " ✓ ferdig for kunde " + best.getKundeId());
            henteKø.leggTilHenteKø(best);

            long nåTid = System.currentTimeMillis();
            long ventetid = nåTid - best.getBestillingstid();

            if (ventetid <= TÅLEGRENSE) {
                App.appendLog("Kunde " + best.getKundeId() + " er 😊 fornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                App.simulation.incrementHappy();
            } else {
                App.appendLog("Kunde " + best.getKundeId() + " er 😠 misfornøyd! (Ventet " + (ventetid / 1000) + " sek)");
                App.simulation.incrementAngry();
            }

        } catch (InterruptedException e) {
            App.appendLog("[Kokk " + kokkNavn + "] Avbrutt. Avslutter kokketråden.");
            Thread.currentThread().interrupt(); // Avbryt tråden
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
