package com.restaurantsim;

public class Ordre {
    private int nextId = 1;
    private final int id;
    private final Kunde kunde;
    private final Maltider maltider;
    private final long ordreTid;
    private boolean erFerdig = false;
    private boolean underTilberedning = false;

    public Ordre(Kunde kunde, Maltider maltider) {
        this.id = nextId++;
        this.kunde = kunde;
        this.maltider = maltider;
        this.ordreTid = System.currentTimeMillis();
    }

    public void begynnTilberedning() {
        this.underTilberedning = true;
    }

    public void ferdigTilberedning() {
        this.underTilberedning = false;
        this.erFerdig = true;
    }

    public boolean ordreUtgatt() {
        return !erFerdig && (System.currentTimeMillis() - ordreTid) > kunde.getOrdre().getMaxVentetid();
    }

    public Kunde getKunde() {
        return kunde;
    }

    public Maltider getMaltider() {
        return maltider;
    }

    public long getOrdreTid() {
        return ordreTid;
    }

    public boolean erFerdig() {
        return erFerdig;
    }

    public int getMaxVentetid() {
        return kunde.getOrdre().getMaxVentetid();
    }
}
