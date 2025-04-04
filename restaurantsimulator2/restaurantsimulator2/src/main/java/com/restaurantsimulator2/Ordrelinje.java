package com.restaurantsimulator2;

public class Ordrelinje {
    private final Retter rett;
    private Status status;
    private final int forberedelsestid;

    public enum Status {
        MOTTATT,
        UNDER_TILBEREDNING,
        KLAR,
        LEVERT,
        KANSELERT
    }

    public Ordrelinje(Retter rett) {
        this(rett, Status.MOTTATT, 0);
    }

    public Ordrelinje(Retter rett, Status status, int forberedelsestid) {
        if (rett == null) {
            throw new IllegalArgumentException("Rett kan ikke være null.");
        }

        this.rett = rett;
        this.status = status;
        this.forberedelsestid = forberedelsestid;
    }

    public void oppdaterStatus(Status nyStatus) {
        this.status = nyStatus;
    }

    public Retter getRett() {
        return rett;
    }

    public Status getStatus() {
        return status;
    }

    public int getForberedelsestid() {
        return forberedelsestid;
    }

    @Override
    public String toString() {
        return "Ordrelinje{" +
                "rett=" + rett +
                ", status=" + status +
                ", forberedelsestid=" + forberedelsestid +
                '}';
    }
}
