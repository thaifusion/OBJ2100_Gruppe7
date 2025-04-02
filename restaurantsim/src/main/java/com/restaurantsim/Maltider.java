package com.restaurantsim;

import java.util.Random;

public enum Maltider {
    PIZZA(8000, "Pizza"),
    BURGER(5000, "Burger"),
    PASTA(7000, "Pasta"),
    SALAT(3000, "Salat"),
    STEK(10000, "Stek");

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

    public static Maltider getRandomMaltider() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    public String toString() {
        return maltidNavn;
    }
}
