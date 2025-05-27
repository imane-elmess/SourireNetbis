package ma.sourireNetbis.model.entities;

import ma.sourireNetbis.model.enums.CategorieActe;

import java.util.List;

public class Acte {

    private Integer id;
    private String libelle;
    private Integer prixbase;
    private CategorieActe categorieacte;

    public Acte(){}

    public Acte(Integer id, String libelle, Integer prixbase, CategorieActe categorieacte) {
        this.id = id;
        this.libelle = libelle;
        this.prixbase = prixbase;
        this.categorieacte = categorieacte;
    }

    @Override
    public String toString() {
        return "Acte{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", prixbase=" + prixbase +
                ", categorieacte=" + categorieacte +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getPrixbase() {
        return prixbase;
    }

    public void setPrixbase(Integer prixbase) {
        this.prixbase = prixbase;
    }

    public CategorieActe getCategorieacte() {
        return categorieacte;
    }

    public void setCategorieacte(CategorieActe categorieacte) {
        this.categorieacte = categorieacte;
    }
}
