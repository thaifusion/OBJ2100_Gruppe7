package com.restaurantsim;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Klassen Bestillingskø håndterer innkommende bestillinger fra kunder
 * og gir kokker (eller en RestaurantManager) mulighet til å hente bestillinger
 * i en tråd-sikker FIFO-rekkefølge.
 */
public class Bestillingskø {

    // Maks antall bestillinger i køen.
    private final int kapasitet;

    /**
     * En tråd-sikker kø for bestillinger.
     * LinkedBlockingQueue sørger for at vi kan sette maks antall elementer
     * og unngå løpsforhold.
     */
    private final BlockingQueue<Bestilling> ko;

    /**
     * Oppretter en ny Bestillingskø med gitt kapasitet.
     *
     * @param kapasitet Maks antall bestillinger køen kan holde.
     */
    public Bestillingskø(int kapasitet) {
        this.kapasitet = kapasitet;
        // Opprett en LinkedBlockingQueue med en øvre grense (kapasitet).
        this.ko = new LinkedBlockingQueue<>(kapasitet);
    }

    /**
     * Forsøker å legge til en ny bestilling i køen.
     * Metoden vil blokkere (vente) dersom køen er full,
     * til det er plass.
     *
     * @param bestilling Bestillingsobjektet som skal legges til.
     * @throws InterruptedException hvis tråden avbrytes mens den venter.
     */
    public void leggTilBestilling(Bestilling bestilling) throws InterruptedException {
        // put() vil blokkere hvis køen er full,
        // inntil det er ledig plass.
        ko.put(bestilling);
        System.out.println("[Bestillingskø] La til bestilling: " + bestilling);
    }

    /**
     * Henter neste bestilling fra køen (FIFO-rekkefølge).
     * Metoden blokkerer (venter) dersom køen er tom,
     * inntil en bestilling er tilgjengelig.
     *
     * @return Bestillingsobjektet som ble hentet.
     * @throws InterruptedException hvis tråden avbrytes mens den venter.
     */
    public Bestilling hentBestilling() throws InterruptedException {
        // take() blokkerer hvis køen er tom,
        // inntil det dukker opp en bestilling.
        Bestilling bestilling = ko.take();
        System.out.println("[Bestillingskø] Hentet bestilling: " + bestilling);
        return bestilling;
    }

    /**
     * Ikke-blokkerende variant av hentBestilling,
     * med en tidsgrense for hvor lenge man venter.
     *
     * @param timeout Maks ventetid i sekunder.
     * @return Bestilling hvis tilgjengelig i løpet av ventetiden, ellers null.
     * @throws InterruptedException hvis tråden avbrytes mens den venter.
     */
    public Bestilling forsokHentBestillingMedTimeout(int timeout) throws InterruptedException {
        // poll() venter i gitt tid. Returnerer null hvis ingen bestilling dukker opp.
        Bestilling bestilling = ko.poll(timeout, TimeUnit.SECONDS);
        if (bestilling != null) {
            System.out.println("[Bestillingskø] Hentet bestilling (med timeout): " + bestilling);
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

    /**
     * Valgfri toString()-metode som gir enkel info om køens status.
     */
    @Override
    public String toString() {
        return "Bestillingskø{" +
               "kapasitet=" + kapasitet +
               ", antallNå=" + ko.size() +
               '}';
    }
}
