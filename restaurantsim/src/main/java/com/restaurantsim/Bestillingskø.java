package com.restaurantsim;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Klassen Bestillingskø håndterer innkommende bestillinger fra kunder
 * i en tråd-sikker FIFO-rekkefølge.
 */
public class Bestillingskø {

    // Maks antall bestillinger i køen.
    private final int kapasitet;

    // En tråd-sikker kø for bestillinger.
    private final BlockingQueue<Bestilling> ko;

    /**
     * Oppretter en ny Bestillingskø med gitt kapasitet.
     *
     * @param kapasitet Maks antall bestillinger køen kan holde.
     */
    public Bestillingskø(int kapasitet) {
        this.kapasitet = kapasitet;
        this.ko = new LinkedBlockingQueue<>(kapasitet);
    }

    /**
     * Forsøker å legge til en ny bestilling i køen (blokkerer hvis full).
     * @param bestilling
     * @throws java.lang.InterruptedException
     */
    public void leggTilBestilling(Bestilling bestilling) throws InterruptedException {
        ko.put(bestilling);
        App.appendLog("[Bestillingskø] La til bestilling: " + bestilling);
        System.out.println("[Bestillingskø] La til bestilling: " + bestilling);
    }

    /**
     * Henter neste bestilling fra køen (blokkerer hvis tom).
     * @return 
     * @throws java.lang.InterruptedException
     */
    public Bestilling hentBestilling() throws InterruptedException {
        Bestilling bestilling = ko.take();
        App.appendLog("[Bestillingskø] Hentet bestilling: " + bestilling);
        System.out.println("[Bestillingskø] Hentet bestilling: " + bestilling);
        return bestilling;
    }

    /**
     * Ikke-blokkerende variant av hentBestilling med timeout.
     * @param timeoutSekunder
     * @return 
     * @throws java.lang.InterruptedException
     */
    public Bestilling forsokHentBestillingMedTimeout(int timeoutSekunder) throws InterruptedException {
        Bestilling bestilling = ko.poll(timeoutSekunder, TimeUnit.SECONDS);
        if (bestilling != null) {
            System.out.println("[Bestillingskø] Hentet (timeout): " + bestilling);
        } else {
            System.out.println("[Bestillingskø] Ingen bestilling tilgjengelig (timeout)...");
        }
        return bestilling;
    }

    /**
     * @return true hvis køen er tom, false ellers.
     */
    public boolean erTom() {
        return ko.isEmpty();
    }

    /**
     * @return true hvis køen er full, false ellers.
     */
    public boolean erFull() {
        return ko.size() == kapasitet;
    }

    /**
     * @return Nåværende antall bestillinger i køen.
     */
    public int antallBestillinger() {
        return ko.size();
    }

    /**
     * @return Maks kapasitet for køen.
     */
    public int getKapasitet() {
        return kapasitet;
    }

    @Override
    public String toString() {
        return "Bestillingskø{kapasitet=" + kapasitet + ", antallNå=" + ko.size() + "}";
    }
}

