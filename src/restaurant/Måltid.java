package restaurant;

public class Måltid {
    private String type;

    public Måltid(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}