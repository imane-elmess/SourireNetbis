package ma.sourireNetbis.dao.filebase_implementations;

import ma.sourireNetbis.dao.api.IActeDao;
import ma.sourireNetbis.dao.api.IIterventionDao;
import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.model.entities.Acte;
import ma.sourireNetbis.model.entities.Intervention;


import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

import java.util.List;

public class InterventionDao implements IIterventionDao {
    private IActeDao acteDao;

    public InterventionDao(){}
    public InterventionDao(IActeDao acteDao) {
        this.acteDao = acteDao;
    }

    public IActeDao getActeDao() {
        return acteDao;
    }

    public void setActeDao(IActeDao acteDao) {
        this.acteDao = acteDao;
    }


    private static final File interventionFile = new File("myFileBase/interventions.txt");

    private String mapToLine(Intervention intervention) {
        //ID|NOTE|ACTE_ID|CONSULTATION|PRIXPATIENT
        var id = intervention.getId();
        var note = intervention.getNote();
        var idActe = intervention.getActe().getId();
        var prixPatient = intervention.getPrixpatient();

        return intervention.getId() + "|" +
                (intervention.getNote() == null ? "null" : intervention.getNote()) + "|" +
                (intervention.getActe() == null ? "null" : intervention.getActe().getId()) + "|" +
                (intervention.getPrixpatient() == null ? "null" : intervention.getPrixpatient());
    }

    private Intervention mapToIntervention(String fileLine) throws DaoException {
        //ID|NOTE|ACTE_ID|PRIXPATIENT
        try {
            String[] values = fileLine.split("\\|"); // Séparer avec le délimiteur '|'
            Integer id = Integer.parseInt(values[0].trim());
            String note = values[1].trim();
            //partie objet
            Acte acte = acteDao.findById(Integer.valueOf(values[2].trim()));
            Integer prixpatient = Integer.parseInt(values[3].trim());

            Intervention intervention = new Intervention(id,note,acte,prixpatient);
            return intervention;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new DaoException("Erreur lors de la conversion de la ligne en Intervention: " + e.getMessage());
        }
    }

    @Override
    public List<Intervention> findAll() throws DaoException {
        List<Intervention>interventions = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(interventionFile.toPath());
            lines.remove(0); //Supprimer la ligne entete
            for(String line: lines){
                interventions.add(mapToIntervention(line));
            }
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        return interventions;
    }

    @Override
    public Intervention findById(Integer identity) throws DaoException {
        return findAll().stream()
                .filter(intervention -> intervention.getId().equals(identity))
                .findFirst()
                .orElseThrow(() -> new DaoException("Intervention avec ID " + identity + " introuvable"));
    }

    @Override
    public Intervention save(Intervention newElement) throws DaoException {
        try(BufferedWriter writer = new BufferedWriter((new FileWriter(interventionFile,true)))){
            Integer maxId = findAll().stream().mapToInt(Intervention::getId)
                    .max().orElse(0);
            newElement.setId(++maxId);
            writer.write(mapToLine(newElement));
            writer.newLine();
            return newElement;
        } catch (IOException e) {
            throw new DaoException(e.getMessage());}
    }

    @Override
    public void update(Intervention newValuesElement) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(interventionFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(interventionFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        )
        {
            String line;
            while ((line = reader.readLine())!=null){
                String currentID = line.split("\\|")[0];
                if(currentID.equals(newValuesElement.getId().toString()))//je compare etant un String
                {
                    writer.write(mapToLine(newValuesElement));
                    isUpdated=true;
                }
                else{
                    writer.write(line);
                }
                writer.newLine();
            }
            if(!isUpdated) throw new DaoException("Aucune ligne n'a été modifiée!");

        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        if(!interventionFile.delete()) throw new DaoException("Problème Technique dans la modification");
        if(!tempFile.renameTo(interventionFile)) throw new DaoException("Problème Technique dans la modification");
    }

    @Override
    public void deleteById(Integer identity) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(interventionFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(interventionFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        )
        {
            String line;
            while ((line = reader.readLine())!=null){
                String currentID = line.split("\\|")[0];
                if(currentID.equals(identity.toString()))//je compare etant un String
                {
                    isDeleted=true;
                    continue;//on ne l'écrit pas cette ligne
                }
                else{
                    writer.write(line);
                }
                writer.newLine();
            }
            if(!isDeleted) throw new DaoException("Aucune ligne n'a été supprimée!");

        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        if(!interventionFile.delete()) throw new DaoException("Problème Technique dans la suppression");
        if(!tempFile.renameTo(interventionFile)) throw new DaoException("Problème Technique dans la suppression");
    }


    public static void main(String[] args) {
        Acte a = new ActeDao().findById(501);
        //Intervention intervention = new Intervention(null,"test",a,344);
        //new InterventionDao(new ActeDao()).save(intervention);
        System.out.println(new InterventionDao(new ActeDao()).findAll());
        new InterventionDao(new ActeDao()).deleteById(405);
    }
}
