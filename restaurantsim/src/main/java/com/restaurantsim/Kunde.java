package com.restaurantsim;

 

public class Kunde implements Runnable {

    private final int kundeId;
    private final Måltider ønsketMåltid;
    private final Bestillingskø bestillingsKø;

    public Kunde(int kundeId, Måltider ønsketMåltid, Bestillingskø bestillingsKø) {
        this.kundeId = kundeId;
        this.ønsketMåltid = ønsketMåltid;
        this.bestillingsKø = bestillingsKø;
    }
    
    

    @Override
    public void run() {
        try {
            // Huske når kunden legger inn bestillingen
            long bestillingstid = System.currentTimeMillis(); // Tid i miilisekunder

            // Opprett en bestilling
            Bestilling best = new Bestilling(kundeId, ønsketMåltid, bestillingstid);

            // Vis i GUI
            App.appendLog("Kunde " + kundeId + " la inn bestilling på " + ønsketMåltid);
            App.appendBestillingsinfo("Kunde " + kundeId + " la inn bestilling på " + ønsketMåltid);

            // Legg bestillingen i køen (blokkert om køen er full).
            bestillingsKø.leggTilBestilling(best);

            // Eksempel: Her kan du "vente" på at maten blir ferdig,
            // eller sjekke en tilbakemelding. Foreløpig bare en print.
            System.out.println("Kunde " + kundeId + " la inn bestilling på " + ønsketMåltid);

            // Sov litt for å simulere at kunden venter.
             Thread.sleep(2000);
            App.appendLog("Kunde " + kundeId + " har ventet en stund og forlater/venter videre...");

            // Evt. sjekk om bestillingen ble hentet/ferdig i tide
            // (i en mer avansert implementasjon).
        } catch (InterruptedException e) {
            System.err.println("Kunde " + kundeId + " avbrutt: " + e.getMessage());
        }
    }
}
