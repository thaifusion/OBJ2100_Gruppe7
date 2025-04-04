package com.restaurantsimulator2;

import java.util.Random;

public enum Maltider {
    PIZZA(5000, "Pizza"),
    BURGER(5000, "Burger"),
    PASTA(8000, "Pasta"),
    SALAT(2000, "Salat"),
    STEK(6000, "Stek");

    private final int tilberedningstid;
    private final String maltidNavn;

    Maltider(int tilberedningstid, String maltidNavn) {
        this.tilberedningstid = tilberedningstid;
        this.maltidNavn = maltidNavn;
    }

    public int getTilberedningstid() {
        return tilberedningstid;
    }

    public String getMaltidNavn() {
        return maltidNavn;
    }

    public static Maltider getRandomMaltid() {
        return Maltider.values()[new Random().nextInt(Maltider.values().length)];
    }
}
