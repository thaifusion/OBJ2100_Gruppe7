package com.restaurantsim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RestaurantSimulation {
    private int happyCount = 0;
    private int angryCount = 0;

    // F.eks. lag en liste for å holde rede på kokker
    private final List<Kokk> kokker = new ArrayList<>();
    // Alternativt en HashMap for spesialisering (valgfritt)
    private final Map<Måltider, Kokk> chefMap = new HashMap<>();
    private final Bestillingskø kø;

    public RestaurantSimulation(int køKapasitet) {
        this.kø = new Bestillingskø(køKapasitet);
    }
    

    /**
     * Registrerer en kokk og starter tråden for denne kokken.
     * @param kokk
     */
    public void registrerKokk(Kokk kokk) {
        kokker.add(kokk);
        new Thread(kokk).start();
        // Hvis kokken har en spesialisering, kan du legge den i chefMap
       // if (kokk.getSpesialisering() != null) {
       //     chefMap.put((Måltider) kokk.getSpesialisering(), kokk);
       // }
    }

    public Bestillingskø getBestillingsKø() {
        return kø;
    }

    /**
     * Starter kundetråder (eksempelmetode).
     */
    public void startKunder() {
        Random random = new Random();

        for (int i = 1; i <= 5; i++) {
            Måltider randomRett = Måltider.values()[random.nextInt(Måltider.values().length)];
            Kunde kunde = new Kunde(i, randomRett, kø);
            
            // Legger tekst i GUI-listen (f.eks. “Kunde 3 ønsker PASTA”)
            App.addKundeTilListe("Kunde " + i + " ønsker " + randomRett);
            new Thread(kunde, "Kunde-" + i).start();
        }
    }
    
// Metoder for å inkrementere:
    public synchronized void incrementHappy() {
        happyCount++;
    }
    
    public synchronized void incrementAngry() {
        angryCount++;
    }
    
    public synchronized int getHappyCount() {
        return happyCount;
    }
    
    public synchronized int getAngryCount() {
        return angryCount;
    }
    
}

