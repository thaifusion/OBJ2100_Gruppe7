package com.restaurantsim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RestaurantSimulation {
    private volatile boolean kjører = false;
    private volatile boolean pausert = false;
    private final Object pauseLock = new Object();

    private int happyCount = 0;
    private int angryCount = 0;

    private final List<Kokk> kokker = new ArrayList<>();
    private final Bestillingskø bestillingsKø;
    private final Hentekø hentekø;

    public RestaurantSimulation(int køKapasitet) {
        this.bestillingsKø = new Bestillingskø(køKapasitet);
        this.hentekø = new Hentekø(køKapasitet);
    }

    public void startSimulering() {
        if (!kjører) {
            kjører = true;
            pausert = false;
            startKunder();
            App.appendLog("Simuleringen startet.");
        }
    }

    public void startKokk(Kokk kokk) {
        kokker.add(kokk);
        Thread t = new Thread(kokk);
        t.start();
    }

    public void stopSimulering() {
        if (kjører) {
            kjører = false;
            pausert = false;

            // Stop alle kokker
            for (Kokk kokk : kokker) {
                kokk.stop(); // bruker vår stop-metode
            }

            App.appendLog("Simuleringen stoppet.");
        }
    }

    public void startKunder() {
        new Thread(() -> {
            Random rand = new Random();
            int kundeId = 1;
            while (kjører) {
                try {
                    Måltider måltid = Måltider.values()[rand.nextInt(Måltider.values().length)];
                    long bestillingstid = System.currentTimeMillis();
                    Kunde kunde = new Kunde(kundeId, måltid, bestillingstid, bestillingsKø, hentekø, this);
                    new Thread(kunde).start();
                    App.addKundeTilListe("Kunde " + kundeId + " ønsker " + måltid);
                    kundeId++;
                    Thread.sleep(1000 + rand.nextInt(2000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    public boolean kjører() {
        return kjører;
    }

    public Bestillingskø getBestillingsKø() {
        return bestillingsKø;
    }

    public Hentekø getHentekø() {
        return hentekø;
    }

    public synchronized void incrementHappy() {
        happyCount++;
    }

    public synchronized void incrementAngry() {
        angryCount++;
    }

    public synchronized int getHappyCount() {
        return happyCount;
    }

    public synchronized int getAngryCount() {
        return angryCount;
    }
}