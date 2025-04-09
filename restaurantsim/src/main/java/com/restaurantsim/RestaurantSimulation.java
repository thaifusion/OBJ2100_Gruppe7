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

    // F.eks. lag en liste for å holde rede på kokker
    private final List<Thread> kokketråder = new ArrayList<>();
    private final List<Kokk> kokker = new ArrayList<>();
    private final List<Kunde> kunder = new ArrayList<>();
    // Alternativt en HashMap for spesialisering (valgfritt)
    // private final Map<Måltider, Kokk> chefMap = new HashMap<>();
    private final Bestillingskø kø;
    private final Hentekø henteKø;

    public RestaurantSimulation(int køKapasitet) {
        this.kø = new Bestillingskø(køKapasitet);
        this.henteKø = new Hentekø(køKapasitet);
    }
    

    /**
     * Registrerer en kokk og starter tråden for denne kokken.
     * @param kokk
     */
    public void startKokk(Kokk kokk) {
        kokker.add(kokk);
        Thread t = new Thread(kokk, kokk.getKokkNavn());
        kokketråder.add(t);
        t.start();
    }

    public Bestillingskø getBestillingsKø() {
        return kø;
    }

    public Hentekø getHentekø() {
        return henteKø;
    }

    /**
     * Starter kundetråder (eksempelmetode).
     */
    public void startKunder() {
        new Thread(() -> {
            Random random = new Random();
            int kundeId = 1;
            while(kjører) {
                try {
                    sjekkPause();
                    if (!kjører) break;
                    Måltider randomRett = Måltider.values()[random.nextInt(Måltider.values().length)];
                    long bestillingstid = System.currentTimeMillis();
                    Kunde kunde = new Kunde(kundeId, randomRett, bestillingstid, kø, henteKø, this);
                    new Thread(kunde, "" + kundeId).start();
                    App.addKundeTilListe("Kunde " + kundeId + " ønsker " + randomRett);
                    kundeId++;
                    Thread.sleep(1000 + random.nextInt(2000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public void startSimulering() {
        if (!kjører) {
            kjører = true;
            pausert = false;
            startKunder();
            App.appendLog("Simuleringen startet");
        }
    }

    public void fortsettSimulering() {
        if (kjører && pausert) {
            pausert = false;
            synchronized (pauseLock) {
                pauseLock.notifyAll();
            }
            App.appendLog("Simuleringen fortsetter");
        }
    }

    public void stopSimulering() {
        if (kjører && !pausert) {
            kjører = false;
<<<<<<< Updated upstream
            pausert = true;
            kokker.forEach(kokker -> kokker.interrupt());
=======
            pausert = false;
            
            for (Thread t : kokketråder) {
                t.interrupt();
            }
>>>>>>> Stashed changes
            kunder.forEach(kunde -> kunde.interrupt());
            App.appendLog("Simuleringen stoppet");
        }
    }

    public boolean kjører() {
        return kjører;
    }

    public boolean pausert() {
        return pausert;
    }

    public void sjekkPause() throws InterruptedException {
        synchronized (pauseLock) {
            while (pausert) {
                pauseLock.wait();
            }
        }
    }
    
    // Metoder for å inkrementere:
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

