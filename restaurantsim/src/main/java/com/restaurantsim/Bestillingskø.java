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

    public Bestillingskø(int kapasitet) {
        this.kapasitet = kapasitet;
        this.ko = new LinkedBlockingQueue<>(kapasitet);
    }

    public void leggTilBestilling(Bestilling bestilling) throws InterruptedException {
        ko.put(bestilling);
        String melding = " Kunde " + bestilling.getKundeId() + 
                         " har bestilt " + bestilling.getMåltid();
        App.appendLog(melding);
        System.out.println(melding);
    }

    public Bestilling hentBestilling() throws InterruptedException {
        Bestilling bestilling = ko.take();
        String melding = " Hentet bestilling for kunde " + bestilling.getKundeId() + 
                         ": " + bestilling.getMåltid();
        App.appendLog(melding);
        System.out.println(melding);
        return bestilling;
    }

    public Bestilling forsokHentBestillingMedTimeout(int timeoutSekunder) throws InterruptedException {
        Bestilling bestilling = ko.poll(timeoutSekunder, TimeUnit.SECONDS);
        if (bestilling != null) {
            String melding = "⏳ Hentet (timeout) bestilling for kunde " + bestilling.getKundeId() + 
                             ": " + bestilling.getMåltid();
            App.appendLog(melding);
            System.out.println(melding);
        } else {
            App.appendLog(" Ingen bestillinger tilgjengelig (timeout)");
            System.out.println("[Bestillingskø] Ingen bestilling tilgjengelig (timeout)...");
        }
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