package ma.sourireNetbis.dao.filebase_implementations;

import ma.sourireNetbis.dao.api.IActeDao;
import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.model.entities.Acte;
import ma.sourireNetbis.model.enums.CategorieActe;


import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActeDao implements IActeDao {

    private static final File acteFile = new File("myFileBase/actes.txt");

    // Convertir un objet Acte en ligne texte
    private String mapToLine(Acte acte) {
        String categorie = (acte.getCategorieacte() == null) ? "null" : acte.getCategorieacte().name();
        return acte.getId() + "|" +
                acte.getLibelle() + "|" +
                acte.getPrixbase() + "|" +
                categorie;
    }
    private Acte mapToActe(String fileLine) throws DaoException {
        //ID|LIBELLE|PRIXBASE|CATEGORIEACTE
        try {
            String[] values = fileLine.split("\\|"); // Séparer avec le délimiteur '|'

            Integer id = Integer.parseInt(values[0].trim());
            String libelle = values[1].trim();
            Integer prixbase = Integer.parseInt(values[2].trim());
            // Mapping de CATEGORIEACTE à l'énumération CategorieActe
            CategorieActe categorieActe = Arrays.stream(CategorieActe.values())
                    .filter(categorie -> categorie.name().equalsIgnoreCase(values[3].trim()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("CategorieActe invalide : " + values[3]));
            return new Acte(id,libelle,prixbase,categorieActe);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new DaoException("Erreur lors de la conversion de la ligne en Patient: " + e.getMessage());
        }
    }

    @Override
    public List<Acte> findAll() throws DaoException {
        List<Acte>actes = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(acteFile.toPath());
            lines.remove(0); //Supprimer la ligne entete
            for(String line: lines){
                actes.add(mapToActe(line));
            }
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        return actes;
    }

    @Override
    public Acte findById(Integer identity) throws DaoException {
        return findAll().stream()
                .filter(acte -> acte.getId().equals(identity))
                .findFirst()
                .orElseThrow(() -> new DaoException("Acte avec ID " + identity + " introuvable"));
    }

    @Override
    public Acte save(Acte newElement) throws DaoException {
        try(BufferedWriter writer = new BufferedWriter((new FileWriter(acteFile,true)))){
            Integer maxId = findAll().stream().mapToInt(Acte::getId)
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
    public void update(Acte newValuesElement) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(acteFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(acteFile));
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
        if(!acteFile.delete()) throw new DaoException("Problème Technique dans la modification");
        if(!tempFile.renameTo(acteFile)) throw new DaoException("Problème Technique dans la modification");
    }

    @Override
    public void deleteById(Integer identity) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(acteFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(acteFile));
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
        if(!acteFile.delete()) throw new DaoException("Problème Technique dans la suppression");
        if(!tempFile.renameTo(acteFile)) throw new DaoException("Problème Technique dans la suppression");
    }

    public static void main(String[] args) {
        System.out.println(new ActeDao().findById(502));
        //new ActeDao().save(new Acte(null,"test",233,CategorieActe.soins_caries));
        new ActeDao().deleteById(505);
    }

}
