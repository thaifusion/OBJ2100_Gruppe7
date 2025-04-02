package com.restaurantsim;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Kokk extends Thread {
    private String navn;
    private Restaurant restaurant;
    private boolean ledig = true;
    private List<Maltider> spesialitet;
    private int forsinkelseMellomOrdre;
    private Random random = new Random();

    public Kokk(String navn, Restaurant restaurant) {
        this.navn = navn;
        this.restaurant = restaurant;
        this.forsinkelseMellomOrdre = 1000 +  random.nextInt(2000);

        Maltider[] alleMaltider = Maltider.values();
        int spesialitetIdx = 2 + random.nextInt(2);
        this.spesialitet = Arrays.asList(alleMaltider).subList(0, spesialitetIdx);
    }
    @Override
    public void run() {
        while (true) {
            try {
                Ordre ordre = restaurant.getNesteOrdre(this);
                ordre.begynnTilberedning();
                Thread.sleep(ordre.getMaltider().getTilberedningstid() + forsinkelseMellomOrdre);
                ordre.ferdigTilberedning();
                restaurant.meldFraFerdigOrdre(ordre);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    public void tilberedMaltid(Ordre ordre) throws InterruptedException {
        ledig = false;
        long tilberedningstid = ordre.getMaltider().getTilberedningstid();
        Thread.sleep(tilberedningstid);
        restaurant.meldFraFerdigOrdre(ordre);
        ledig = true;
    }

    public boolean erSpesialisert(Maltider maltid) {
        return spesialitet.contains(maltid);
    }

    public void taPause() throws InterruptedException {
        Thread.sleep(forsinkelseMellomOrdre);
    }

    public boolean erLedig() {
        return ledig;
    }

    public String getNavn() {
        return navn;
    }

    public List<Maltider> getSpesialitet() {
        return spesialitet;
    }
}
