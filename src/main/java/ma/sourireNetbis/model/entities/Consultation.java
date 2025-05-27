package ma.sourireNetbis.model.entities;

import ma.sourireNetbis.model.enums.TypeConsultation;

import java.util.List;

public class Consultation {
    private Integer id;
    private TypeConsultation typeConsultation;
    private Ordonnance ordonnance;
    private List<Intervention> interventions;
    private String date;
    private Integer paiement;

    public Consultation(){}

    public Consultation(Integer id, TypeConsultation typeConsultation, Ordonnance ordonnance, List<Intervention> interventions, String date, Integer paiement) {
        this.id = id;
        this.typeConsultation = typeConsultation;
        this.ordonnance = ordonnance;
        this.interventions = interventions;
        this.date = date;
        this.paiement = paiement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeConsultation getTypeConsultation() {
        return typeConsultation;
    }

    public void setTypeConsultation(TypeConsultation typeConsultation) {
        this.typeConsultation = typeConsultation;
    }

    public Ordonnance getOrdonnance() {
        return ordonnance;
    }

    public void setOrdonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
    }

    public List<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(List<Intervention> interventions) {
        this.interventions = interventions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPaiement() {
        return paiement;
    }

    public void setPaiement(Integer paiement) {
        this.paiement = paiement;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", typeConsultation=" + typeConsultation +
                ", ordonnance=" + ordonnance +
                ", interventions=" + interventions +
                ", date='" + date + '\'' +
                ", paiement=" + paiement +
                '}';
    }
}
