package model;

import java.io.Serializable;
import java.util.Objects;

public class Stoc implements Serializable, Identity<Integer> {
    private Integer id;
    private Produs produs;
    private int cantitate;

    public Stoc(Produs produs, int cantitate) {
        this.produs = produs;
        this.cantitate = cantitate;
    }

    public Stoc() {
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produs getProdus() {
        return produs;
    }

    public Produs getProd(){
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
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
