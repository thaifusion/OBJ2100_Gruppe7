package com.restaurantsim;

import java.util.Random;

public enum Måltider {
    PIZZA(8000, "Pizza"),
    BURGER(5000, "Burger"),
    PASTA(7000, "Pasta"),
    SALAT(3000, "Salat"),
    STEK(10000, "Stek");

   private final int tilberedningstid;
   private final String måltidNavn;

   Måltider(int tilberedningstid, String måltidNavn) {
       this.tilberedningstid = tilberedningstid;
       this.måltidNavn = måltidNavn;
   }
   
   public int getTilberedningstid() {
       return tilberedningstid;
   }

   public String getMåltidNavn() {
       return måltidNavn;
   }

   public static Måltider getRandomMaltider() {
       Random random = new Random();
       return values()[random.nextInt(values().length)];
   }

   public String toString() {
       return måltidNavn;
   }
}
