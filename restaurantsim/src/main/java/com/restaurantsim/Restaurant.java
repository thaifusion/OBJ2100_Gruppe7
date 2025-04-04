package com.restaurantsim;

import java.util.ArrayList;

public class Restaurant {

    private Ordrelinje ordrelinje;
    private ArrayList<Kokk> kokker;
    private ArrayList<Kunde> kunder;
    
    public Restaurant(int antallKokker, int maxOrdreKø) {
        this.ordrelinje = new Ordrelinje(maxOrdreKø);
        this.kokker = new ArrayList<>();
        this.kunder = new ArrayList<>();

        for (int i = 0; i < antallKokker; i++) {
            kokker.add(new Kokk("Kokk " + i, this));
        }
    }

    public synchronized void leggTilKunde(Kunde kunde) {
        new Thread(kunde).start();
    }

    public synchronized void leggTilOrdre(Ordre ordre) throws InterruptedException {
        ordrelinje.leggTilOrdre(ordre);
    }

    public Ordre getNesteOrdre(Kokk kokk) throws InterruptedException {
        return ordrelinje.getNesteOrdre(kokk);
    }

    public void meldFraFerdigOrdre(Ordre ordre) throws InterruptedException {
        Kunde kunde = ordre.getKunde();
        if (kunde != null) {
            kunde.maltidMottatt();
        }   
    }

    public void startSimulering() {
        for (Kokk kokk : kokker) {
            kokk.start();
        }
        
        synchronized(kunder) {
            for (Kunde kunde : kunder) {
                kunde.start();
            }
        }
    }
}
