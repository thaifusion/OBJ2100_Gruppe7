package com.restaurantsim;

 

public class Kunde implements Runnable {

    private final int kundeId;
    private final Måltider ønsketMåltid;
    private final Bestillingskø bestillingsKø;
    private final RestaurantSimulation simulasjon;
    private boolean erFornøyd = false;

    public Kunde(int kundeId, Måltider ønsketMåltid, Bestillingskø bestillingsKø, RestaurantSimulation simulasjon) {
        this.kundeId = kundeId;
        this.ønsketMåltid = ønsketMåltid;
        this.bestillingsKø = bestillingsKø;
        this.simulasjon = simulasjon;
    }
    
    @Override
public void run() {
    try {
        Bestilling best = new Bestilling(kundeId, ønsketMåltid, this);
        App.appendLog("Kunde " + kundeId + " la inn bestilling på " + ønsketMåltid);
        App.appendBestillingsinfo("Kunde " + kundeId + " la inn bestilling på " + ønsketMåltid);

        bestillingsKø.leggTilBestilling(best);

        // Kunden venter maks 5000 ms på mat
        synchronized (this) {
            wait(5000);
        }

        if (erFornøyd) {
            simulasjon.incrementHappy();
            App.appendLog("Kunde " + kundeId + " fikk maten og er fornøyd 😊");
        } else {
            simulasjon.incrementAngry();
            App.appendLog("Kunde " + kundeId + " ble utålmodig og gikk 😠");
        }

    } catch (InterruptedException e) {
        System.err.println("Kunde " + kundeId + " avbrutt: " + e.getMessage());
        Thread.currentThread().interrupt();
    }
    }

    public synchronized void bestillingKlar() {
        erFornøyd = true;
        notify();
    }
    
    public int getKundeId() {
        return kundeId;
    }

}
