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

    public void pauseSimulering() {
        if (kjører && !pausert) {
            pausert = true;
            App.appendLog("Simuleringen pausert.");
        }
    }

    public void fortsettSimulering() {
        if (kjører && pausert) {
            pausert = false;
            synchronized (pauseLock) {
                pauseLock.notifyAll();
            }
            App.appendLog("Simuleringen fortsetter.");
        }
    }

    public void stopSimulering() {
        if (kjører) {
            kjører = false;
            pausert = false;

            for (Kokk kokk : kokker) {
                kokk.stop(); // kaller stop()-metode i Kokk
            }

            App.appendLog("Simuleringen stoppet.");
        }
    }

    public void startKokk(Kokk kokk) {
        kokker.add(kokk);
        new Thread(kokk, kokk.getKokkNavn()).start();
    }

    public void startKunder() {
        new Thread(() -> {
            Random random = new Random();
            int kundeId = 1;
            while (kjører && kundeId <=5) {
                try {
                    sjekkPause();

                    Måltider randomRett = Måltider.values()[random.nextInt(Måltider.values().length)];
                    long bestillingstid = System.currentTimeMillis();

                    Kunde kunde = new Kunde(kundeId, randomRett, bestillingstid, bestillingsKø, hentekø, this);
                    new Thread(kunde, "Kunde-" + kundeId).start();
                    // App.addKundeTilListe("Kunde " + kundeId + " ønsker " + randomRett);
                    kundeId++;

                    Thread.sleep(1000 + random.nextInt(5000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    private void sjekkPause() {
        synchronized (pauseLock) {
            while (pausert) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public boolean kjører() {
        return kjører;
    }

    public boolean pausert() {
        return pausert;
    }

    public Bestillingskø getBestillingsKø() {
        return bestillingsKø;
    }

    public Hentekø getHentekø() {
        return hentekø;
    }

    public boolean finnesLedigSpesialist(Måltider måltid) {
        for (Kokk kokk : kokker) {
            if (kokk.getSpesialisering() == måltid && kokk.erLedig()) {
                return true;
            }
        }
        return false;
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

    public synchronized void resetCounts() {
        happyCount = 0;
        angryCount = 0;
    }
}