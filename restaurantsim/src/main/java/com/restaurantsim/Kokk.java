package com.restaurantsim;

public class Kokk implements Runnable {

    private final String kokkNavn;
    private final Bestillingskø bestillingsKø;
    private Måltider spesialisering;

    // Konstruktør for å opprette en Kokk med navn og bestillingskø.
    public Kokk(String kokkNavn, Bestillingskø bestillingsKø) {
        this.kokkNavn = kokkNavn;
        this.bestillingsKø = bestillingsKø;
    }

    // Konstruktør for å opprette en Kokk med navn, spesialisering og bestillingskø.
    public Kokk(String kokkNavn, Måltider spesialisering, Bestillingskø bestillingsKø) {
        this.kokkNavn = kokkNavn;
        this.spesialisering = spesialisering;
        this.bestillingsKø = bestillingsKø;
    }

    // Henter neste bestilling fra køen og tilbereder den.
    // Kokken simulerer tilberedningstid og gir beskjed til kunden når maten er klar.
    @Override
    public void run() {
        while (true) {
            try {
                // Hent neste bestilling (blokkerer om køen er tom).
                Bestilling best = bestillingsKø.hentBestilling();
                App.appendLog(kokkNavn + " tilbereder: " + best);
               App.appendBestillingsinfo("Kokk " + kokkNavn + " henter bestilling for kunde " 
    + best.getKundeId() + " (" + best.getMåltid() + ")");

                
                System.out.println(kokkNavn + " tilbereder: " + best);
                 
                // Simuler tilberedningstid (f.eks. 2 sek).
                Thread.sleep(2000);
                App.appendLog(kokkNavn + " er ferdig med bestilling for kunde " + best.getKundeId());
                App.appendBestillingsinfo("Kokk " + kokkNavn + " er ferdig med bestilling for kunde " 
    + best.getKundeId());


                System.out.println(kokkNavn + " er ferdig med bestilling for kunde " + best.getKundeId());
                // Deretter er kokken klar for neste bestilling.

                // Gi beskjed til kunden om at bestillingen er klar.
                // Assuming best.getKundeId() should return a Kunde object instead of int
                Kunde kunde = best.getKunde();
                kunde.bestillingKlar();

            } catch (InterruptedException e) {
                // Avbryter kokkens arbeid (f.eks. når restauranten stenger).
                App.appendLog(kokkNavn + " avbrutt. Avslutter kokketråden.");
                System.out.println(kokkNavn + " avbrutt og avslutter.");
                break;
            }
        }
    }

    public Måltider getSpesialisering() {
    return spesialisering;
}

    // Removed getKundeId method as kundeId is not defined in the class.

}
