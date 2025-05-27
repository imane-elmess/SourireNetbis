package ma.sourireNetbis.dao.filebase_implementations;

import ma.sourireNetbis.dao.api.IPatientDao;
import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.model.entities.Patient;
import ma.sourireNetbis.model.enums.Assurance;
import ma.sourireNetbis.model.enums.Sexe;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class PatientDao implements IPatientDao {
    //private static final: pour ne pas etre changer, elle reste une constante
    private static final File patientFile = new File("myFileBase/patients.txt");

    //on prend les éléments d'un patient un par un pour save et delete
    private String mapToLine(Patient patient){
        String id = patient.getId().toString();
        String cin = patient.getCin();
        String nom = patient.getNom();
        String prenom = patient.getPrenom();
        String age = patient.getAge().toString();
        String sexe = patient.getSexe()!=null ? patient.getSexe().toString(): "null";
        String assurance = patient.getAssurance()!=null ? patient.getAssurance().toString(): "null";
        //ID|CIN|NOM|PRENOM|AGE|SEXE|ASSURANCE
        return id + "|" +cin+ "|" +nom +"|"+ prenom +"|"+age +"|"+sexe+"|"+assurance;
    }
    private Patient mapToPatient(String fileLine) throws DaoException {
        //ID|CIN|NOM|PRENOM|AGE|SEXE|ASSURANCE
        try {
            String[] values = fileLine.split("\\|"); // Séparer avec le délimiteur '|'

            Integer id = Integer.parseInt(values[0].trim());
            String cin = values[1].trim();
            String nom = values[2].trim();
            String prenom = values[3].trim();
            Integer age =Integer.parseInt(values[4].trim()) ;
            Sexe sexe = values[5].equalsIgnoreCase("Homme") ? Sexe.Homme :
                        values[5].equalsIgnoreCase("Femme") ? Sexe.Femme : null;
            Assurance assurance =       values[6].equalsIgnoreCase("FAR") ? Assurance.FAR :
                                        values[6].equalsIgnoreCase("CNOPS") ? Assurance.CNOPS :
                                        values[6].equalsIgnoreCase("CNSS") ? Assurance.CNSS :
                                        values[6].equalsIgnoreCase("AUTRES") ? Assurance.AUTRES : null;
            return new Patient(id,cin,nom,prenom,age,sexe,assurance);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new DaoException("Erreur lors de la conversion de la ligne en Patient: " + e.getMessage());
        }
    }
    @Override
    public List<Patient> findAll() throws DaoException {
        List<Patient> patients = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(patientFile.toPath());
            lines.remove(0); //Supprimer la ligne entete
            for(String line: lines){
                patients.add(mapToPatient(line));
            }
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        return patients;
    }

    @Override
    public Patient findById(Integer identity) throws DaoException {
        return findAll().stream().filter(patient -> patient.getId()==identity)
                .findFirst()  // Récupère le premier résultat
                .orElse(null);  // Retourne null si aucun patient n'a été trouvé
    }
    @Override
    public Patient findPatientByCINLike(String motCle) throws DaoException {
        return findAll()
                .stream()
                .filter(patient -> patient.getCin().contains(motCle))
                .findFirst()  // Récupère le premier résultat
                .orElse(null);  // Retourne null si aucun patient n'a été trouvé
    }

    @Override
    public Patient save(Patient newElement) throws DaoException {
        try(BufferedWriter writer = new BufferedWriter((new FileWriter(patientFile,true)))){
            Integer maxId = findAll().stream().mapToInt(Patient::getId)
                    .max().orElse(0);
            newElement.setId(++maxId);
            writer.write(mapToLine(newElement));
            writer.newLine();
            return newElement;
        } catch (IOException e) {
            throw new DaoException(e.getMessage());}
    }

    @Override
    public void update(Patient newValuesElement) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(patientFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(patientFile));
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
        if(!patientFile.delete()) throw new DaoException("Problème Technique dans la modification");
        if(!tempFile.renameTo(patientFile)) throw new DaoException("Problème Technique dans la modification");
    }

    @Override
    public void deleteById(Integer identity) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(patientFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(patientFile));
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
        if(!patientFile.delete()) throw new DaoException("Problème Technique dans la suppression");
        if(!tempFile.renameTo(patientFile)) throw new DaoException("Problème Technique dans la suppression");
    }

    public static void main(String[] args) {
       //new PatientDao().update(new Patient(5,"tzsee123","AAKJZ","KAKAJA",12,Sexe.Homme,Assurance.CNSS));
    }
}
