package ma.sourireNetbis.model.entities;

import ma.sourireNetbis.model.enums.Assurance;
import ma.sourireNetbis.model.enums.Sexe;

public class Patient {
    private Integer id;
    private String cin;
    private String nom;
    private String prenom;
    private Integer age;
    private Sexe sexe;
    private Assurance assurance;
    private FicheMedicale fiche;

    public Patient(){}

    public Patient(Integer id, String cin, String nom, String prenom, Integer age, Sexe sexe, Assurance assurance) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.sexe = sexe;
        this.assurance = assurance;
    }

    public Patient(Integer id, String cin, String nom, String prenom, Integer age, Sexe sexe, Assurance assurance, FicheMedicale fiche) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.sexe = sexe;
        this.assurance = assurance;
        this.fiche = fiche;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Assurance getAssurance() {
        return assurance;
    }

    public void setAssurance(Assurance assurance) {
        this.assurance = assurance;
    }

    public FicheMedicale getFiche() {
        return fiche;
    }

    public void setFiche(FicheMedicale fiche) {
        this.fiche = fiche;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", cin='" + cin + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", sexe=" + sexe +
                ", assurance=" + assurance+
                "}";
    }
}
