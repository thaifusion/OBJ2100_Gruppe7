package com.restaurantsim;

public enum Måltider {
    PIZZA("Pizza", 299, "Gluten", 8000),
    PASTA("Pasta", 269, "Gluten", 10000),
    SALAT("Salat", 199, "Nøtter", 4000),
    BURGER("Burger", 249, "Gluten", 7000);

    private final String visningsnavn;
    private final int pris;
    private final String allergener;
    private final long tilberedningstid;

    Måltider(String visningsnavn, int pris, String allergener, long tilberedningstid) {
        this.visningsnavn = visningsnavn;
        this.pris = pris;
        this.allergener = allergener;
        this.tilberedningstid = tilberedningstid;
    }

    public long getTilberedningstid() {
        return tilberedningstid;
    }

    public String getVisningsnavn() {
        return visningsnavn;
    }

    public int getPris() {
        return pris;
    }

    public String getAllergener() {
        return allergener;
    }

    @Override
    public String toString() {
        return visningsnavn + " (" + pris + " kr, Allergener: " + allergener + ")";
    }
} 
