package com.restaurantsim;

public class Bestilling {
   
    private final int kundeId;
    private final Måltider måltid;

    public Bestilling(int kundeId, Måltider måltid) {
        this.kundeId = kundeId;
        this.måltid = måltid;
    }

    public int getKundeId() {
        return kundeId;
    }

    public Måltider getMåltid() {
        return måltid;
    }

    @Override
    public String toString() {
        return "Bestilling [Kundenummer " + kundeId + ", Måltid: " + måltid + "]";
    }
}
 

