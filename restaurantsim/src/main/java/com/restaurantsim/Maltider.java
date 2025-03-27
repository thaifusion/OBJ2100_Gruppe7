package com.restaurantsim;

public class Maltider {
    // Instansvariabler
    private String navn;
    private int tilberedningstid; // Tilberedning i minutter
    
    // Konstruktører
    public Maltider(String navn, int tilberedningstid) {
        this.navn = navn;
        this.tilberedningstid = tilberedningstid;
    }

    // Gettere
    public String getNavn() {
        return navn;
    }

    public int getTilberedningstid() {
        return tilberedningstid;
    }

    // Settere
   public void setNavn(String navn) {
    this.navn = navn;
   }

   public void setTilberedningstid(int tilberedningstid) {
    this.tilberedningstid = tilberedningstid;
   }

   // toString-metode for å skrive ut informasjon om måltidet
   @Override
    public String toString() {
        return "Måltid{" +
                "navn='" + navn + '\'' +
                ", tilberedningstid=" + tilberedningstid + " minutter" +
                '}';
    }

    // En statisk metode for å vise menyen med tilberedningstid
    public static String visMeny() {
        StringBuilder meny = new StringBuilder("Meny:\n");
        meny.append(new Maltider("Pizza", 30).toString()).append("\n");
        meny.append(new Maltider("Vegetarburger", 15).toString()).append("\n");
        meny.append(new Maltider("Kyllingsalat", 15).toString()).append("\n");
        meny.append(new Maltider("Biff med poteter", 20).toString()).append("\n");
        return meny.toString();

    }
}
