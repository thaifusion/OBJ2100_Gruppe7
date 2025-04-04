package com.restaurantsim;

public class Kunde implements Runnable {
    private final String navn;
    private Ordre ordre;
    private final Restaurant restaurant;
    private volatile boolean ordreMottatt = false;
    private volatile boolean erSint = false;
    private final long maxVentetid;
    private final long ankomstTid;
    private final Object monitor = new Object();

    public Kunde(String navn, Ordre ordre, Restaurant restaurant, long maxVentetid, long ankomstTid) {
        this.navn = navn;
        this.ordre = ordre;
        this.restaurant = restaurant;  // Fixed: Now properly assigned
        this.maxVentetid = maxVentetid;
        this.ankomstTid = ankomstTid != 0 ? ankomstTid : System.currentTimeMillis();
    }

    @Override
    public void run() {
        try {
            leggInnOrdre();
            long startVentetid = System.currentTimeMillis();
            
            synchronized (monitor) {
                while (!ordreMottatt && !ordreUtgatt(startVentetid)) {
                    long remainingWait = maxVentetid - (System.currentTimeMillis() - startVentetid);
                    if (remainingWait <= 0) break;
                    monitor.wait(remainingWait);
                }
            }

            if (!ordreMottatt) {
                erSint = true;
                System.out.println(navn + " er sint og går hjem! Ventet for lenge.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(navn + " ble avbrutt mens de ventet");
        } catch (Exception e) {
            System.err.println("Feil i kunde " + navn + ": " + e.getMessage());
        }
    }

    private void leggInnOrdre() throws InterruptedException {
        if (ordre == null) {
            this.ordre = new Ordre(this, Maltider.getRandomMaltider());
        }
        restaurant.leggTilOrdre(ordre);
        System.out.println(navn + " la inn en ordre: " + ordre);
    }

    public void maltidMottatt() {
        synchronized (monitor) {
            ordreMottatt = true;
            monitor.notifyAll();
        }
        System.out.println(navn + " mottok måltidet sitt: " + ordre);
    }

    private boolean ordreUtgatt(long startVentetid) {
        return (System.currentTimeMillis() - startVentetid) > maxVentetid;
    }

    // Getters
    public String getNavn() { return navn; }
    public Ordre getOrdre() { return ordre; }
    public boolean erSint() { return erSint; }
    public long getMaxVentetid() { return maxVentetid; }
    public long getAnkomstTid() { return ankomstTid; }
}