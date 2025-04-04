package com.restaurantsim;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Ordrelinje {

    private final BlockingQueue<Ordre> ordreKø;
    private final int maxOrdreKø;

    public Ordrelinje(int maxOrdreKø) {
        this.maxOrdreKø = maxOrdreKø;
        this.ordreKø = new ArrayBlockingQueue<>(maxOrdreKø);
    }

    public void leggTilOrdre(Ordre ordre) throws InterruptedException{
        if (ordreKø.size() < maxOrdreKø) {
            ordreKø.put(ordre);
        }
    }

    public Ordre getNesteOrdre(Kokk kokk) throws InterruptedException {
        Ordre ordre = ordreKø.poll(1, TimeUnit.SECONDS);
        if (ordre == null) {
            ordre = ordreKø.take();
        }
        return ordre;
    }

    public boolean erFull() {
        return ordreKø.remainingCapacity() == 0;
    }

    public boolean erTom() {
        return ordreKø.isEmpty();
    }

    public int maxOrdreKø() {
        return ordreKø.size();
    }

    public int getOrdreKø() {
        return ordreKø.size();
    }
}
