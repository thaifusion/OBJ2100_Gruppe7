package com.restaurantsim;

public class Kunde {
    private int id;

    public Kunde() {
        setKundeId(id);
        setMaksVentetid();
        setBestilling();
    }

    public void setKundeId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "";
    }
}
