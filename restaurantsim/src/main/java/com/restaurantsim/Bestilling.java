package com.restaurantsim;

public class Bestilling {
   
    private final int kundeId;
    private final Måltider måltid;
    private final Kunde kunde;

   

    public Bestilling(int kundeId, Måltider måltid, Kunde kunde) {
        this.kundeId = kundeId;
        this.måltid = måltid;
        this.kunde = kunde;
    }

    public Kunde getKunde() {
            return kunde;
        }

    public int getKundeId() {
        return kundeId;
    }

    public Måltider getMåltid() {
        return måltid;
    }

    @Override
    public String toString() {
        return "Bestilling{kundeId=" + kundeId + ", måltid=" + måltid + "}";
    }
    
}
 

