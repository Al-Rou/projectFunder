package de.unidue.inf.is.domain;

public final class Projekt
{
    private int kennung;
    private String titel;
    private String beschreibung;
    private long finanzierungslimit;
    private boolean abgeschlossen;
    private String ersteller;
    private int vorgaenger;
    private int kategorie;

    public Projekt(int kennung, String titel,
                   String beschreibung,
                   long finanzierungslimit,
                   boolean abgeschlossen, String ersteller,
                   int vorgaenger, int kategorie)
    {
        this.kennung = kennung;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.finanzierungslimit = finanzierungslimit;
        this.abgeschlossen = abgeschlossen;
        this.ersteller = ersteller;
        this.vorgaenger = vorgaenger;
        this.kategorie = kategorie;
    }

    public int getKennung() {
        return kennung;
    }

    public String getTitel() {
        return titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public long getFinanzierungslimit() {
        return finanzierungslimit;
    }

    public boolean getAbgeschlossen() {
        return abgeschlossen;
    }

    public String getErsteller() {
        return ersteller;
    }

    public int getVorgaenger() {
        return vorgaenger;
    }

    public int getKategorie() {
        return kategorie;
    }
}
