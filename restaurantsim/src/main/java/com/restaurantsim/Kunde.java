package com.restaurantsim;

 

public class Kunde {
    private String fornavn;
    private String etternavn;

    public Kunde() {
        setFornavn(fornavn);
        setEtternavn(etternavn);
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getFornavn() {
        return fornavn;
    }

    public String getEtternavn() {        
        return etternavn;
    }

    public boolean equals(Kunde kunde) {
        return fornavn.equals(kunde.getFornavn()) && etternavn.equals(kunde.getEtternavn());
    }

    public String toString() {
        return fornavn + " " + etternavn;
    }

  
    
}
