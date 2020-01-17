package de.unidue.inf.is.domain;

import java.util.Date;

public final class Kommentar {

    private Integer id;
    private String text;
    private Date datum;
    private String sichtbarkeit;

    public Kommentar(Integer id, String text,
                Date datum, String sichtbarkeit)
    {
        this.id = id;
        this.text = text;
        this.datum = datum;
        this.sichtbarkeit = sichtbarkeit;
    }
    public Integer getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public Date getDatum(){
        return datum;
    }
    public String getSichtbarkeit()
    {
        return sichtbarkeit;
    }
}