package ma.sourireNetbis.model.entities;

import ma.sourireNetbis.model.enums.StatutPaiement;

import java.util.List;

public class FicheMedicale {
    private Integer id;
    private Patient patient;
    private String dentiste;
    private StatutPaiement statutPaiement;
    private List<Consultation> consultations;
    private List<Ordonnance> ordonnances;
    private List<Rdv> rdvs;


    public FicheMedicale(){}

    public FicheMedicale(Integer id, Patient patient, String dentiste, StatutPaiement statutPaiement, List<Consultation> consultations, List<Ordonnance> ordonnances, List<Rdv> rdvs) {
        this.id = id;
        this.patient = patient;
        this.dentiste = dentiste;
        this.statutPaiement = statutPaiement;
        this.consultations = consultations;
        this.ordonnances = ordonnances;
        this.rdvs = rdvs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDentiste() {
        return dentiste;
    }

    public void setDentiste(String dentiste) {
        this.dentiste = dentiste;
    }

    public StatutPaiement getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(StatutPaiement statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public List<Ordonnance> getOrdonnances() {
        return ordonnances;
    }

    public void setOrdonnances(List<Ordonnance> ordonnances) {
        this.ordonnances = ordonnances;
    }

    public List<Rdv> getRdvs() {
        return rdvs;
    }

    public void setRdvs(List<Rdv> rdvs) {
        this.rdvs = rdvs;
    }

    @Override
    public String toString() {
        return "FicheMedicale{" +
                "id=" + id +
                ", patient=" + patient +
                ", dentiste='" + dentiste + '\'' +
                ", statutPaiement=" + statutPaiement +
                ", consultations=" + consultations +
                ", ordonnances=" + ordonnances +
                ", rdvs=" + rdvs +
                '}';
    }
}
