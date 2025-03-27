package com.restaurantsim;

import java.util.concurrent.BlockingQueue;

// Kokk-klassen er en tråd som tilbereder måltider for kunder. Kokken har en spesialisering som
// bestemmer hvilke måltider han/hun kan tilberede. Kokken henter bestillinger fra en kø og tilbereder dem.
public class Kokk extends Thread {
    // Instansvariabler
    private String navn;
    private String spesialisering;
    private BlockingQueue<?> bestillingskø; // Changed to a generic type for now
    private int tilberedningstid;

    // Konstruktør, setter opp en kokk med navn, spesialisering, bestillingskø og tilberedningstid
    // Kokken er en tråd, så konstruktøren tar også imot en BlockingQueue som representerer bestillingskøen
    // Kokken har en tilberedningstid som bestemmer hvor lang tid det tar å tilberede et måltid
    public Kokk(String navn, String spesialisering, BlockingQueue<?> bestillingskø, int tilberedningstid) {
        this.navn = navn;
        this.spesialisering = spesialisering;
        this.bestillingskø = bestillingskø;
        this.tilberedningstid = tilberedningstid;
    }

    // Run-metoden til tråden
    // Kokken henter en bestilling fra køen, tilbereder måltidet og gir det til kunden.
    @Override
    public void run() {
        while (true) {
            try {
                // Simulate waiting for a task to avoid busy-waiting
                // Wait for a bestilling to become available in the queue
                Object bestilling = bestillingskø.take();

                // Bestilling bestilling = null;

                // 1. Først prøv å finne en bestilling som passer kokkens spesialisering
                /*
                for (Bestilling b : bestillingskø) {
                    if (b.getMåltid().getType().equalsIgnoreCase(spesialisering)) {
                        bestilling = b;
                        break;
                    }
                }
                */

                // 2. Hvis ingen spesialisert bestilling finnes, ta en hvilken som helst bestilling
                /*
                if (bestilling == null) {
                    bestilling = bestillingskø.poll(); // Tar en tilfeldig bestilling hvis mulig
                } else {
                    bestillingskø.remove(bestilling); // Fjern fra køen hvis en spesialisert bestilling ble funnet
                }
                */

                // 3. Hvis det fortsatt ikke er noen bestilling, vent litt og prøv igjen
                /*
                if (bestilling == null) {
                    Thread.sleep(500);
                    continue;
                }
                */

                // 4. Tilbered måltidet
                /*
                System.out.println(navn + " lager " + bestilling.getMåltid().getType() + " for " + bestilling.getKunde().getNavn());

                Thread.sleep(tilberedningstid * 1000);

                System.out.println(navn + " har ferdig måltidet for " + bestilling.getKunde().getNavn());
                bestilling.getKunde().mottaMåltid();
                */

            } catch (InterruptedException e) {
                System.out.println(navn + " er ferdig for dagen.");
                break;
            }
        }
    }

    // Gettere og settere
    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getSpesialisering() {
        return spesialisering;
    }

    public void setSpesialisering(String spesialisering) {
        this.spesialisering = spesialisering;
    }

    public BlockingQueue<?> getBestillingskø() {
        return bestillingskø;
    }

    public void setBestillingskø(BlockingQueue<?> bestillingskø) {
        this.bestillingskø = bestillingskø;
    }

    public int getTilberedningstid() {
        return tilberedningstid;
    }

    public void setTilberedningstid(int tilberedningstid) {
        this.tilberedningstid = tilberedningstid;
    }

    // equals-metode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Kokk kokk = (Kokk) obj;
        return tilberedningstid == kokk.tilberedningstid &&
                navn.equals(kokk.navn) &&
                spesialisering.equals(kokk.spesialisering);
    }

    // toString-metode
    @Override
    public String toString() {
        return "Kokk{" +
                "navn='" + navn + '\'' +
                ", spesialisering='" + spesialisering + '\'' +
                '}';
    }

  
    
    
}
