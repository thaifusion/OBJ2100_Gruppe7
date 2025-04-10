package com.restaurantsim;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Hentekø {
    private final int kapasitet;
    private final BlockingQueue<Bestilling> henteKø;

    public Hentekø(int kapasitet) {
        this.kapasitet = kapasitet;
        this.henteKø = new LinkedBlockingQueue<>(kapasitet);
    }

    public void leggTilHenteKø(Bestilling bestilling) throws InterruptedException {
        henteKø.put(bestilling);
        // App.appendLog("[Hentekø] Klar for henting: " + bestilling);
    }

    public Bestilling kundeHentBestilling() throws InterruptedException {
        Bestilling bestilling = henteKø.take();
        // App.appendLog("[Hentekø] Hentet bestilling: " + bestilling);
        return bestilling;
    }

    public boolean erTom() {
        return henteKø.isEmpty();
    }

    public boolean erFull() {
        return henteKø.size() == kapasitet;
    }
}
