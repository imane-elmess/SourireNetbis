package ma.sourireNetbis.model.entities;

import ma.sourireNetbis.model.enums.StatutMedicament;

public class Medicament {
    private Integer id;
    private StatutMedicament statut;
    private Integer qteenstock;
    private String nom;

    public Medicament(){}

    public Medicament(Integer id, StatutMedicament statut, Integer qteenstock, String nom) {
        this.id = id;
        this.statut = statut;
        this.qteenstock = qteenstock;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", statut=" + statut +
                ", qteenstock=" + qteenstock +
                ", nom='" + nom + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StatutMedicament getStatut() {
        return statut;
    }

    public void setStatut(StatutMedicament statut) {
        this.statut = statut;
    }

    public Integer getQteenstock() {
        return qteenstock;
    }

    public void setQteenstock(Integer qteenstock) {
        this.qteenstock = qteenstock;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
