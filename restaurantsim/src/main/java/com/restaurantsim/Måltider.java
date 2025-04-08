package com.restaurantsim;

public enum Måltider {
    PIZZA("Pizza", 299, "Gluten"),
    PASTA("Pasta", 269, "Gluten"),
    SALAT("Salat", 199, "Nøtter"),
    BURGER("Burger", 249, "Gluten");

    private final String visningsnavn;
    private final int pris;
    private final String allergener;

    Måltider(String visningsnavn, int pris, String allergener) {
        this.visningsnavn = visningsnavn;
        this.pris = pris;
        this.allergener = allergener;
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
