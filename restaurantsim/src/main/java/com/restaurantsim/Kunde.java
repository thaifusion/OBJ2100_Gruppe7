package com.restaurantsim;

 

public class Kunde {
    private int id;

    public Kunde(int id, int maksVentetid, Bestilling bestilling) {
        setKundeId(id);
        setMaksVentetid();
        setBestilling();
    }

    public void setKundeId(int id) {
        this.id = id;
    }

    public void setMaksVentetid() {
        this.maksVentetid = maksVentetid;
    }

    public void setBestilling() {
        this.bestilling = bestilling;
    }

    @Override
    public String toString() {
        return "";
    }

  
    
}
