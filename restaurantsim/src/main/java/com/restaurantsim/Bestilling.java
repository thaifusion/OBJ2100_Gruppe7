package com.restaurantsim;

public class Bestilling {
   
    private final int kundeId;
    private final Måltider måltid;
    private final long bestillingstid; // Tidspunkt da kunden bestilte

    public Bestilling(int kundeId, Måltider måltid, long bestillingstid) {
        this.kundeId = kundeId;
        this.måltid = måltid;
        this.bestillingstid = bestillingstid;
    }

    public int getKundeId() {
        return kundeId;
    }

    public Måltider getMåltid() {
        return måltid;
    }

    public long getBestillingstid() {
        return bestillingstid;
    }

    @Override
    public String toString() {
        return "Bestilling{kundeId=" + kundeId + ", måltid=" + måltid + "}";
    }
}
 

