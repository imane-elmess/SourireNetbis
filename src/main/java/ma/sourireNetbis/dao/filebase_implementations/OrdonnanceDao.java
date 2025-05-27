package ma.sourireNetbis.dao.filebase_implementations;

import ma.sourireNetbis.dao.api.IMedicamentDao;
import ma.sourireNetbis.dao.api.IOrdonnanceDao;
import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.model.entities.Medicament;
import ma.sourireNetbis.model.entities.Ordonnance;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class OrdonnanceDao implements IOrdonnanceDao {
    private IMedicamentDao medicamentDao;

    public OrdonnanceDao(){}

    public OrdonnanceDao(IMedicamentDao medicamentDao) {
        this.medicamentDao = medicamentDao;
    }

    public IMedicamentDao getMedicamentDao() {
        return medicamentDao;
    }

    public void setMedicamentDao(IMedicamentDao medicamentDao) {
        this.medicamentDao = medicamentDao;
    }

    private static final File ordFile = new File("myFileBase/ordonnances.txt");

    private String mapToLine(Ordonnance ord) throws DaoException {
        // ID|MEDICAMENTS_IDS|DESCRIPTION
        var id = ord.getId();
        // Liste des médicaments associés
        StringBuilder medicamentsIds = new StringBuilder();

        for (Medicament med : ord.getListeMedicaments()) {
            // On peut appeler medicamentDao pour récupérer l'id de chaque médicament
            if (medicamentsIds.length() > 0) {
                medicamentsIds.append(",");
            }
            medicamentsIds.append(med.getId()); // On prend l'ID de chaque médicament
        }

        var description = ord.getDescription();

        // Construction de la ligne de texte à retourner
        return ord.getId() + "|" + medicamentsIds.toString() + "|" + (description == null ? "null" : description);
    }
    private Ordonnance mapToOrdonnance(String fileLine) throws DaoException {
        //ID|MEDICAMENTS_IDS|DESCRIPTION
        try {
            String[] values = fileLine.split("\\|"); // Séparer avec le délimiteur '|'

            Integer id = Integer.parseInt(values[0].trim());
            String[] medicamentsIds = values[1].split(","); // Liste des médicaments
            List<Medicament> medicaments = new ArrayList<>();

            // Ajouter les médicaments à la liste
            for (String medicamentId : medicamentsIds) {
                if (!medicamentId.equals("null")) {
                    // Récupérer les médicaments à partir de leur ID
                    medicaments.add(medicamentDao.findById(Integer.parseInt(medicamentId)));
                }
            }
            String description = values[2].equals("null") ? null : values[2];

            return new Ordonnance(id, medicaments, description);
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la conversion de la ligne en Ordonnance: " + e.getMessage());
        }
    }
    @Override
    public List<Ordonnance> findAll() throws DaoException {
        List<Ordonnance>ordonnances = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(ordFile.toPath());
            lines.remove(0); //Supprimer la ligne entete
            for(String line: lines){
                ordonnances.add(mapToOrdonnance(line));
            }
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        return ordonnances;
    }

    @Override
    public Ordonnance findById(Integer identity) throws DaoException {
            return findAll().stream()
                .filter(ord -> ord.getId().equals(identity))
                .findFirst()
                .orElseThrow(() -> new DaoException("Ordonnance avec ID " + identity + " introuvable"));
        }

    @Override
    public Ordonnance save(Ordonnance newElement) throws DaoException {
        try(BufferedWriter writer = new BufferedWriter((new FileWriter(ordFile,true)))){
            Integer maxId = findAll().stream().mapToInt(Ordonnance::getId)
                    .max().orElse(0);
            newElement.setId(++maxId);
            writer.write(mapToLine(newElement));
            writer.newLine();
            return newElement;
        } catch (IOException e) {
            throw new DaoException(e.getMessage());}
    }

    @Override
    public void update(Ordonnance newValuesElement) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(ordFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(ordFile));
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
        if(!ordFile.delete()) throw new DaoException("Problème Technique dans la modification");
        if(!tempFile.renameTo(ordFile)) throw new DaoException("Problème Technique dans la modification");
    }

    @Override
    public void deleteById(Integer identity) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(ordFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(ordFile));
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
        if(!ordFile.delete()) throw new DaoException("Problème Technique dans la suppression");
        if(!tempFile.renameTo(ordFile)) throw new DaoException("Problème Technique dans la suppression");
    }

    public static void main(String[] args) {
        System.out.println(new OrdonnanceDao(new MedicamentDao()).findById(201));
    }
}
