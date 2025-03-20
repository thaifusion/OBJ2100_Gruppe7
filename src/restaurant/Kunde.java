package restaurant;

public class Kunde {
    private String navn;

    public Kunde(String navn) {
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }

    @Override
    public String toString() {
        return navn;
    }
}