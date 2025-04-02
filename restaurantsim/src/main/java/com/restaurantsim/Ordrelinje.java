package com.restaurantsim;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Ordrelinje {

    private final BlockingQueue<Ordre> ordreKø;
    private final BlockingQueue<Ordre> vipOrdreKø;
    private final int maxOrdreKø;

    public Ordrelinje(int maxOrdreKø) {
        this.maxOrdreKø = maxOrdreKø;
        this.ordreKø = new ArrayBlockingQueue<>(maxOrdreKø);
        this.vipOrdreKø = new ArrayBlockingQueue<>(maxOrdreKø);
    }

    public void leggTilOrdre(Ordre ordre) throws InterruptedException{
        if (ordre.getKunde().erVip()) {
            vipOrdreKø.put(ordre);
        } else {
            ordreKø.put(ordre);
        }
    }

    public Ordre getNesteOrdre(Kokk kokk) throws InterruptedException {
        Ordre ordre = vipOrdreKø.poll(100, TimeUnit.MILLISECONDS);
        if (ordre == null) {
            ordre = ordreKø.take();
        }
        return ordre;
    }

    public boolean erFull() {
        return (vipOrdreKø.remainingCapacity() + ordreKø.remainingCapacity()) == 0;
    }

    public boolean erTom() {
        return vipOrdreKø.isEmpty() && ordreKø.isEmpty();
    }

    public int maxOrdreKø() {
        return ordreKø.size() + vipOrdreKø.size();
    }

    public int getVipOrdreKø() {
        return vipOrdreKø.size();
    }

    public int getOrdreKø() {
        return ordreKø.size();
    }
}
