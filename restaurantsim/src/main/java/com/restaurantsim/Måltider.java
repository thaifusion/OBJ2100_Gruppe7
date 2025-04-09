package com.restaurantsim;

public enum Måltider {
    PIZZA(9000),
    PASTA(12000),
    SALAT(8000),
    BURGER(5000);

    private final int tilberedningstid; // Tid i millisekunder

    Måltider(int tid) {
        this.tilberedningstid = tid;
    }

    public int getTilberedningstid() {
        return tilberedningstid;
    }
   
}
