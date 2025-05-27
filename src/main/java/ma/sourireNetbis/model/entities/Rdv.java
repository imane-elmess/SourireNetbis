package ma.sourireNetbis.model.entities;

import ma.sourireNetbis.model.enums.TypeRdv;

public class Rdv {
    private Integer id;
    private TypeRdv typeRdv;
    private String date;
    private Consultation consultation;
    public Rdv(){}

    public Rdv(Integer id, TypeRdv typeRdv, String date, Consultation consultation) {
        this.id = id;
        this.typeRdv = typeRdv;
        this.date = date;
        this.consultation = consultation;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeRdv getTypeRdv() {
        return typeRdv;
    }

    public void setTypeRdv(TypeRdv typeRdv) {
        this.typeRdv = typeRdv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }


    @Override
    public String toString() {
        return "Rdv{" +
                "id=" + id +
                ", typeRdv=" + typeRdv +
                ", date='" + date + '\'' +
                ", consultation=" + consultation +
                '}';
    }
}
