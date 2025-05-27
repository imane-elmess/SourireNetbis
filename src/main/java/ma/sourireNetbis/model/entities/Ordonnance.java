package ma.sourireNetbis.model.entities;

import java.util.List;

public class Ordonnance {
    private Integer id;
    private List<Medicament> listemedicaments;
    private String description;

    public Ordonnance(){}

    public Ordonnance(Integer id, List<Medicament> listemedicaments, String description) {
        this.id = id;
        this.listemedicaments = listemedicaments;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Medicament> getListeMedicaments() {
        return listemedicaments;
    }

    public void setListeMedicaments(List<Medicament> listeMedicaments) {
        this.listemedicaments = listeMedicaments;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Medicament> getListemedicaments() {
        return listemedicaments;
    }

    public void setListemedicaments(List<Medicament> listemedicaments) {
        this.listemedicaments = listemedicaments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Ordonnance{" +
                "id=" + id +
                ", listemedicaments=" + listemedicaments +
                ", description='" + description + '\'' +
                '}';
    }
}
