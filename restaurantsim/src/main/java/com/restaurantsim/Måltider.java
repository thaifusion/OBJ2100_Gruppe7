package com.restaurantsim;

public enum Måltider {
    PIZZA(12000),
    PASTA(9000),
    SALAT(5000),
    BIFF(15000),
    BURGER(8000);

    private final int tilberedningstid; // Tid i millisekunder

    Måltider(int tilberedningstid) {
        this.tilberedningstid = tilberedningstid;
    }   

    public int getTilberedningstid() {
        return tilberedningstid;
    }

}
