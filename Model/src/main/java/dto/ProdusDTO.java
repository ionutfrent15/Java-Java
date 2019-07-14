package dto;

import java.io.Serializable;
import java.util.Objects;

public class ProdusDTO implements Serializable {
    private Integer id;
    private String denumire;
    private double pret;
    private int cantitate;

    public ProdusDTO(Integer id, String denumire, double pret, int cantitate) {
        this.id = id;
        this.denumire = denumire;
        this.pret = pret;
        this.cantitate = cantitate;
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

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdusDTO produsDTO = (ProdusDTO) o;
        return this.id.equals(produsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, denumire, pret, cantitate);
    }
}
