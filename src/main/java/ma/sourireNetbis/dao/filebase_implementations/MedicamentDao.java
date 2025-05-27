package ma.sourireNetbis.dao.filebase_implementations;

import ma.sourireNetbis.dao.api.IMedicamentDao;
import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.model.entities.Acte;
import ma.sourireNetbis.model.entities.Medicament;
import ma.sourireNetbis.model.enums.Assurance;
import ma.sourireNetbis.model.enums.CategorieActe;
import ma.sourireNetbis.model.enums.Sexe;
import ma.sourireNetbis.model.enums.StatutMedicament;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicamentDao implements IMedicamentDao {
    private static final File medFile = new File("myFileBase/meds.txt");

    // Méthode pour convertir un objet Medicament en ligne texte
    private String mapToLine(Medicament medicament) {

        String statut = (medicament.getStatut() == null) ? "null" : String.valueOf(medicament.getStatut());
        return medicament.getId() + "|" +
                medicament.getNom() + "|" +
                statut + "|" +
                medicament.getQteenstock();
    }

    private Medicament mapToActe(String fileLine) throws DaoException {
        // ID|NOM|STATUTMEDICAMENT|QTESTOCK
        try {
            String[] values = fileLine.split("\\|"); // Séparer avec le délimiteur '|'

            Integer id = Integer.parseInt(values[0].trim());
            String nom = values[1].trim();
            StatutMedicament statut = values[2].equalsIgnoreCase("Interne") ? StatutMedicament.Interne :
                    values[2].equalsIgnoreCase("Externe") ? StatutMedicament.Externe : null;
            Integer qte = Integer.parseInt(values[3].trim());
            return new Medicament(id, statut, qte, nom);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new DaoException("Erreur lors de la conversion de la ligne en Médicament: " + e.getMessage());
        }
    }

    @Override
    public List<Medicament> findAll() throws DaoException {
        List<Medicament> meds = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(medFile.toPath());
            lines.remove(0); //Supprimer la ligne entete
            for (String line : lines) {
                meds.add(mapToActe(line));
            }
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        return meds;
    }

    @Override
    public Medicament findById(Integer identity) throws DaoException {
        return findAll().stream()
                .filter(med -> med.getId().equals(identity))
                .findFirst()
                .orElseThrow(() -> new DaoException("Medicament avec ID " + identity + " introuvable"));
    }

    @Override
    public Medicament save(Medicament newElement) throws DaoException {
        try (BufferedWriter writer = new BufferedWriter((new FileWriter(medFile, true)))) {
            Integer maxId = findAll().stream().mapToInt(Medicament::getId)
                    .max().orElse(0);
            newElement.setId(++maxId);
            writer.write(mapToLine(newElement));
            writer.newLine();
            return newElement;
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void update(Medicament newValuesElement) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(medFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(medFile));
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
        if(!medFile.delete()) throw new DaoException("Problème Technique dans la modification");
        if(!tempFile.renameTo(medFile)) throw new DaoException("Problème Technique dans la modification");
    }

    @Override
    public void deleteById(Integer identity) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(medFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(medFile));
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
        if(!medFile.delete()) throw new DaoException("Problème Technique dans la suppression");
        if(!tempFile.renameTo(medFile)) throw new DaoException("Problème Technique dans la suppression");
    }

    public static void main(String[] args) {
        System.out.println(new MedicamentDao().findById(605));
        new MedicamentDao().deleteById(606);
    }
}
