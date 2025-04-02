package com.restaurantsim;


public class Kunde implements Runnable {

    private String navn;
    private Ordre ordre;
    private Restaurant restaurant;
    private boolean vip;
    private boolean ordreMottatt = false;
    private boolean erSint = false;
    private long maxVentetid;
    private long ankomstTid;

    public Kunde(String navn, Ordre ordre, boolean vip, Restaurant restaurant, long maxVentetid, long ankomstTid) {
        this.navn = navn;
        this.ordre = ordre;
        this.vip = vip;
        this.maxVentetid = maxVentetid;
        this.ankomstTid = System.currentTimeMillis();
    }

    @Override
    public void run() {
        try {
            leggInnOrdre();
            long startVentetid = System.currentTimeMillis();
            while (!ordreMottatt && !ordreUtgatt(startVentetid)) {
                wait(maxVentetid);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private void leggInnOrdre() throws InterruptedException {
        this.ordre = new Ordre(this, Maltider.getRandomMaltider());
        restaurant.leggTilOrdre(ordre);   
    }

    public synchronized void maltidMottatt() {
        ordreMottatt = true;
        notifyAll();
    }

    public boolean ordreUtgatt(long startVentetid) {
        return (System.currentTimeMillis() - startVentetid) > maxVentetid;
    }

    public String getNavn() {
        return navn;
    }
    
    public Ordre getOrdre() {
        return ordre;
    }

    public boolean erVip() {
        return vip;
    }

    public boolean erSint() {
        return erSint;
    }

    public long getMaxVentetid() {
        return maxVentetid;
    }
    
}
