package ma.sourireNetbis.dao.filebase_implementations;

import ma.sourireNetbis.dao.api.IConsultationDao;
import ma.sourireNetbis.dao.api.IRdvDao;
import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.model.entities.Consultation;
import ma.sourireNetbis.model.entities.Rdv;
import ma.sourireNetbis.model.enums.TypeRdv;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class RdvDao implements IRdvDao {
    IConsultationDao consultationDao;
    private static final File rdvFile = new File("myFileBase/rdvs.txt");

    public RdvDao(IConsultationDao consultationDao) {
        this.consultationDao = consultationDao;
    }

    public IConsultationDao getConsultationDao() {
        return consultationDao;
    }

    public void setConsultationDao(IConsultationDao consultationDao) {
        this.consultationDao = consultationDao;
    }

    @Override
    public String toString() {
        return "RdvDao{" +
                "consultationDao=" + consultationDao +
                '}';
    }
    private String mapToLine(Rdv rdv) {
        //ID|TYPERDV|DATE|CONSULTATION_ID
        String typeRdv = (rdv.getTypeRdv() == null) ? "null" : rdv.getTypeRdv().name();
        return rdv.getId() + "|" + typeRdv + "|" + rdv.getDate() + "|" + rdv.getConsultation().getId();
    }
    private Rdv mapToRdv(String fileLine) throws DaoException {
        //ID|TYPERDV|DATE|CONSULTATION_ID
        try {

            String[] values = fileLine.split("\\|");
            Integer id = Integer.parseInt(values[0].trim());
            TypeRdv typeRdv  = values[1].equalsIgnoreCase("Confirmé") ? TypeRdv.Confirmé :
                             values[1].equalsIgnoreCase("En_cours_de_confirmation") ? TypeRdv.En_cours_de_confirmation :
                            values[1].equalsIgnoreCase("Annulé") ? TypeRdv.Annulé: null;
            String date = values[2].trim();
            Consultation consultation = consultationDao.findById(Integer.valueOf(values[3].trim()));
            return new Rdv(id, typeRdv, date, consultation);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new DaoException("Erreur lors de la conversion de la ligne en Rdv: " + e.getMessage());
        }
    }
    @Override
    public List<Rdv> findAll() throws DaoException {
        List<Rdv> rdvs = new ArrayList<>();
        try {
            if (!rdvFile.exists()) {
                throw new DaoException("Fichier non trouvé : " + rdvFile.getAbsolutePath());
            }
            List<String> lines = Files.readAllLines(rdvFile.toPath());
            lines.remove(0); // Suppression de l'en-tête
            for (String line : lines) {
                rdvs.add(mapToRdv(line));
            }
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        return rdvs;
    }


    @Override
    public Rdv findById(Integer identity) throws DaoException {
        return findAll().stream().
                filter(rdv -> rdv.getId().equals(identity))
                .findFirst()  // Récupère le premier résultat
                .orElse(null);  // Retourne null si aucun patient n'a été trouvé
    }

    @Override
    public Rdv save(Rdv newElement) throws DaoException {
        try(BufferedWriter writer = new BufferedWriter((new FileWriter(rdvFile,true)))){
            Integer maxId = findAll().stream().mapToInt(Rdv::getId)
                    .max().orElse(0);
            newElement.setId(++maxId);
            writer.write(mapToLine(newElement));
            writer.newLine();
            return newElement;
        } catch (IOException e) {
            throw new DaoException(e.getMessage());}
    }

    @Override
    public void update(Rdv newValuesElement) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(rdvFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(rdvFile));
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
        if(!rdvFile.delete()) throw new DaoException("Problème Technique dans la modification");
        if(!tempFile.renameTo(rdvFile)) throw new DaoException("Problème Technique dans la modification");
    }

    @Override
    public void deleteById(Integer identity) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(rdvFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(rdvFile));
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
        if(!rdvFile.delete()) throw new DaoException("Problème Technique dans la suppression");
        if(!tempFile.renameTo(rdvFile)) throw new DaoException("Problème Technique dans la suppression");

    }

    public static void main(String[] args) {
        System.out.println(new RdvDao(new ConsultationDao(new OrdonnanceDao(new MedicamentDao()),
                new InterventionDao(new ActeDao()))).findById(301));

    }
}
