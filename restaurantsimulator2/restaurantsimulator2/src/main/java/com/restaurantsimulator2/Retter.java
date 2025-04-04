package com.restaurantsimulator2;

public class Retter {
    private final Maltider maltid;
    private final String rettNavn;

    public Retter(Maltider maltid, String rettNavn) {
        this.maltid = maltid;
        this.rettNavn = rettNavn;
    }

    public Maltider getMaltid() {
        return maltid;
    }

    public String getRettNavn() {
        return rettNavn;
    }
}
