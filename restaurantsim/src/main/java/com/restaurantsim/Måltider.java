package com.restaurantsim;

public enum Måltider {
   PIZZA(3000),
   PASTA(2000),
   SALAT(1500),
   BURGER(2500);
   
   private final int tilberedningstid;

   Måltider(int tilberedningstid) {
       this.tilberedningstid = tilberedningstid;
   }

   public int getTilberedningstid() {
       return tilberedningstid;
   }
}
