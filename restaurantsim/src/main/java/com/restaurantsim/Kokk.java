package com.restaurantsim;

public class Kokk implements Runnable {

    private final String kokkNavn;
    private final Bestillingskø bestillingsKø;
    private final Måltider spesialisering;
    private final Hentekø hentekø;
    private final RestaurantSimulation simulation;

    // Opprinnelige konstruktører beholdes
    public Kokk(String kokkNavn, Bestillingskø bestillingsKø) {
        this(kokkNavn, null, bestillingsKø, null, null);
    }

    public Kokk(String kokkNavn, Måltider spesialisering, Bestillingskø bestillingsKø) {
        this(kokkNavn, spesialisering, bestillingsKø, null, null);
    }

    // Ny utvidet konstruktør
    public Kokk(String kokkNavn, Måltider spesialisering, Bestillingskø bestillingsKø, Hentekø hentekø, RestaurantSimulation simulation) {
        this.kokkNavn = kokkNavn;
        this.spesialisering = spesialisering;
        this.bestillingsKø = bestillingsKø;
        this.hentekø = hentekø;
        this.simulation = simulation;
    }

    private volatile boolean aktiv = true;

public void stop() {
    aktiv = false;
}

    @Override
    public void run() {
        while (true) {
            try {
                // Hent neste bestilling fra køen
                Bestilling best = bestillingsKø.hentBestilling();

                App.appendLog("[Kokk] " + kokkNavn + " henter bestilling fra kunde " + best.getKundeId());
                App.appendBestillingsinfo("Kokk " + kokkNavn + " tilbereder " + best.getMåltid() +
                        " for kunde " + best.getKundeId());

                System.out.println("[Kokk] " + kokkNavn + " tilbereder: " + best);

                // Simuler tilberedningstid
                Thread.sleep(2000);

                App.appendLog("[Kokk] " + kokkNavn + " er ferdig med bestilling for kunde " + best.getKundeId());
                App.appendBestillingsinfo("Kokk " + kokkNavn + " ferdigstilt " + best.getMåltid() +
                        " for kunde " + best.getKundeId());

                System.out.println("[Kokk] " + kokkNavn + " ferdig med bestilling for kunde " + best.getKundeId());

                // Fjern kunden fra GUI etter ferdig servering
                App.removeKundeFraListe("Kunde " + best.getKundeId() + " ønsker " + best.getMåltid());

                // Oppdater happy-counter
                if (simulation != null) {
                    simulation.incrementHappy();
                }

            } catch (InterruptedException e) {
                App.appendLog("[Kokk] " + kokkNavn + " avbrutt og avslutter kokketråden.");
                System.out.println("[Kokk] " + kokkNavn + " avbrutt og avslutter.");
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
}