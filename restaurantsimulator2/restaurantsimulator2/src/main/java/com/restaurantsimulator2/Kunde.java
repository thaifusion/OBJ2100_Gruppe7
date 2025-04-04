package com.restaurantsimulator2;

public class Kunde implements Runnable{

    private int kundeId;
    private int idTeller = kundeId++;
    private Maltider ønsketMaltid;

    public Kunde() {
        this.kundeId = idTeller;
        this.ønsketMaltid = Maltider.getRandomMaltid();
    }

    public void run() {
        
    }

    public Ordre leggTilOrdre(Ordre ordre) {
        return ordre;
    }

}
