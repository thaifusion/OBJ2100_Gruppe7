package com.restaurantsim;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Klassen Bestillingskø håndterer innkommende bestillinger fra kunder
 * i en tråd-sikker FIFO-rekkefølge.
 */
public class Bestillingskø {

    private final int kapasitet;
    private final BlockingQueue<Bestilling> ko;
    private final BlockingQueue<Bestilling> hentekø;

    public Bestillingskø(int kapasitet) {
        this.kapasitet = kapasitet;
        this.ko = new LinkedBlockingQueue<>(kapasitet);
        this.hentekø = new LinkedBlockingQueue<>(kapasitet);
    }

    public void leggTilBestilling(Bestilling bestilling) throws InterruptedException {
        if (ko.size() == kapasitet) {
            App.appendBestillingsinfo("Bestillingskøen er full!"); 
        } else {
            ko.put(bestilling);
            String melding = "[Bestillingskø] Kunde " + bestilling.getKundeId() + 
                             " har bestilt " + bestilling.getMåltid();
            // App.appendLog(melding);
            System.out.println(melding);
        }

    }

    public void leggTilBestillingHentekø(Bestilling bestilling) throws InterruptedException {
        hentekø.put(bestilling);
    }

    public Bestilling hentBestillingHentekø() throws InterruptedException {
        Bestilling bestillingFerdig = hentekø.take();
        return bestillingFerdig;
    }

    public Bestilling hentBestilling() throws InterruptedException {
        Bestilling bestilling = ko.take();
        String melding = "[Bestillingskø] Hentet bestilling for kunde " + bestilling.getKundeId() + 
                         ": " + bestilling.getMåltid();
        // App.appendLog(melding);
        System.out.println(melding);
        return bestilling;
    }

    public boolean erTom() {
        return ko.isEmpty();
    }

    public boolean erFull() {
        return ko.size() == kapasitet;
    }

    public int antallBestillinger() {
        return ko.size();
    }

    public int getKapasitet() {
        return kapasitet;
    }

    @Override
    public String toString() {
        return "Bestillingskø{kapasitet=" + kapasitet + ", antallNå=" + ko.size() + "}";
    }
}