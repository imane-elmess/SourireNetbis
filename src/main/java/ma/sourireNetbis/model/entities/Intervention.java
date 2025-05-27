package ma.sourireNetbis.model.entities;


public class Intervention {
    private Integer id;
    private String note;
    private Acte acte;
    private Integer prixpatient;

    public Intervention(){}

    public Intervention(Integer id, String note, Acte acte, Integer prixpatient) {
        this.id = id;
        this.note = note;
        this.acte = acte;
        this.prixpatient = prixpatient;
    }


    @Override
    public String toString() {
        return "Intervention{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", acte=" + acte +
                ", prixpatient=" + prixpatient +
                '}';
    }

    public Acte getActe() {
        return acte;
    }

    public void setActe(Acte acte) {
        this.acte = acte;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getPrixpatient() {
        return prixpatient;
    }

    public void setPrixpatient(Integer prixpatient) {
        this.prixpatient = prixpatient;
    }
}
