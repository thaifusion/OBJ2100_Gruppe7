package restaurant;

import java.time.LocalDateTime;
import java.util.Objects;

public class Bestilling {
    private Kunde kunde;
    private Måltid måltid;
    private LocalDateTime bestillingstid;
    private int tilberedningstid; // I sekunder eller minutter
    private String status; // "Pågående", "Ferdig", "Kansellert"

    // Konstruktør
    public Bestilling(Kunde kunde, Måltid måltid, int tilberedningstid) {
        this.kunde = kunde;
        this.måltid = måltid;
        this.bestillingstid = LocalDateTime.now();
        this.tilberedningstid = tilberedningstid;
        this.status = "Pågående";
    }

    // Get- og set-metoder
    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Måltid getMåltid() {
        return måltid;
    }

    public void setMåltid(Måltid måltid) {
        this.måltid = måltid;
    }

    public LocalDateTime getBestillingstid() {
        return bestillingstid;
    }

    public int getTilberedningstid() {
        return tilberedningstid;
    }

    public void setTilberedningstid(int tilberedningstid) {
        this.tilberedningstid = tilberedningstid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // equals-metode for å sammenligne bestillinger
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestilling that = (Bestilling) o;
        return tilberedningstid == that.tilberedningstid &&
               Objects.equals(kunde, that.kunde) &&
               Objects.equals(måltid, that.måltid) &&
               Objects.equals(bestillingstid, that.bestillingstid) &&
               Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kunde, måltid, bestillingstid, tilberedningstid, status);
    }

    // toString-metode for enkel utskrift
    @Override
    public String toString() {
        return "Bestilling{" +
                "kunde=" + kunde +
                ", måltid=" + måltid +
                ", bestillingstid=" + bestillingstid +
                ", tilberedningstid=" + tilberedningstid + " minutter" +
                ", status='" + status + '\'' +
                '}';
    }
}
