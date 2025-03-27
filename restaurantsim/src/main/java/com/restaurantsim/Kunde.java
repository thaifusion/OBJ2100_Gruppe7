package com.restaurantsim;


public class Kunde extends Thread {
    private int id;
    private int maksVentetid;
    private String status;
    private Bestilling bestilling;

    public Kunde(int id, int maksVentetid, Bestilling bestilling) {
        setKundeId(id);
        setMaksVentetid(maksVentetid);
        setBestilling(bestilling);
        setStatus(status);
    }

    public void setKundeId(int id) {
        this.id = id;
    }

    public int getKundeId() {
        return id;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setMaksVentetid(int maksVentetid) {
        this.maksVentetid = maksVentetid;
    }

    public int getMaksVentetid() {
        return maksVentetid;
    }

    public void setBestilling(Bestilling bestilling) {
        this.bestilling = bestilling;
    }

    public Bestilling getBestilling() {
        return bestilling;
    }

    @Override
    public String toString() {
        return "";
    }

    /*
     *  Metoder for funksjonalitet for Kunde
     */

     
}
