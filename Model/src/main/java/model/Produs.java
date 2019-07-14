package model;

import java.io.Serializable;

public class Produs implements Serializable, Identity<Integer> {
    private Integer id;
    private String denumire;
    private String descriere;
    private double pret;

    public Produs(String denumire, String descriere, Double pret) {
        this.denumire = denumire;
        this.descriere = descriere;
        this.pret = pret;
    }

    public Produs() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer integer) {
        this.id = integer;
    }
}
