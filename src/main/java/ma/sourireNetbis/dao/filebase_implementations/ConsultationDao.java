package ma.sourireNetbis.dao.filebase_implementations;

import ma.sourireNetbis.dao.api.IConsultationDao;
import ma.sourireNetbis.dao.api.IIterventionDao;
import ma.sourireNetbis.dao.api.IOrdonnanceDao;
import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.model.entities.*;
import ma.sourireNetbis.model.enums.TypeConsultation;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsultationDao implements IConsultationDao {
private IOrdonnanceDao ordonnanceDao;
private IIterventionDao interventionDao;

public ConsultationDao(){}

    public ConsultationDao(IOrdonnanceDao ordonnanceDao, IIterventionDao interventionDao) {
        this.ordonnanceDao = ordonnanceDao;
        this.interventionDao = interventionDao;
    }

    public IOrdonnanceDao getOrdonnanceDao() {
        return ordonnanceDao;
    }

    public void setOrdonnanceDao(IOrdonnanceDao ordonnanceDao) {
        this.ordonnanceDao = ordonnanceDao;
    }

    public IIterventionDao getIterventionDao() {
        return interventionDao;
    }

    public void setIterventionDao(IIterventionDao iterventionDao) {
        this.interventionDao = iterventionDao;
    }
    private static final File consFile = new File("myFileBase/consultations.txt");

    private String mapToLine(Consultation cons) throws DaoException {
        // ID|TYPECONSULTATION|ORDONNANCE_ID|INTERVENTION_IDS|DATE|PAIEMENT
        var id = cons.getId();
        String type = (cons.getTypeConsultation() == null) ? "null" : cons.getTypeConsultation().name();
        var ordonnance = cons.getOrdonnance().getId();
        // Liste des interventionss associés
        StringBuilder interventionsIds = new StringBuilder();

        for (Intervention interv : cons.getInterventions()) {
            if (interventionsIds.length() > 0) {
                interventionsIds.append(",");
            }
            interventionsIds.append(interv.getId()); // On prend l'ID de chaque intervention
        }
        var date = cons.getDate();
        var paiement = cons.getPaiement();
        // Construction de la ligne de texte à retourner
        return cons.getId() + "|" + type +"|"+ordonnance.toString() +"|"+ interventionsIds.toString() + "|" +
                date + "|"+ paiement;
    }

    private Consultation mapToConsultation(String fileLine) throws DaoException {
        // ID|TYPECONSULTATION|ORDONNANCE_ID|INTERVENTION_IDS|DATE|PAIEMENT
        try {
            String[] values = fileLine.split("\\|"); // Séparer avec le délimiteur '|'

            Integer id = Integer.parseInt(values[0].trim());
            TypeConsultation type = Arrays.stream(TypeConsultation.values())
                    .filter(c -> c.name().equalsIgnoreCase(values[1].trim()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Type de consultation invalide : " + values[1]));
            Ordonnance ordonnance = ordonnanceDao.findById(Integer.parseInt(values[2].trim()));
            String[] interventionsIds = values[3].split(","); // Liste des interventions
            List<Intervention> interventions = new ArrayList<>();
            // Ajouter les interventions à la liste
            for (String interId : interventionsIds) {
                if (!interId.equals("null")) {
                    interventions.add(interventionDao.findById(Integer.parseInt(interId)));
                }
            }
                String date = values[4].equals("null") ? null : values[4];
            Integer paiement = Integer.parseInt(values[5].trim());

            return new Consultation(id,type,ordonnance,interventions,date,paiement);
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la conversion de la ligne en Consultation: " + e.getMessage());
        }
    }




    @Override
    public List<Consultation> findAll() throws DaoException {
        List<Consultation>consultations = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(consFile.toPath());
            lines.remove(0); //Supprimer la ligne entete
            for(String line: lines){
                consultations.add(mapToConsultation(line));
            }
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        return consultations;
    }

    @Override
    public Consultation findById(Integer identity) throws DaoException {
        return findAll().stream()
                .filter(a -> a.getId().equals(identity))
                .findFirst()
                .orElseThrow(() -> new DaoException("Consultation avec ID " + identity + " introuvable"));
    }

    @Override
    public Consultation save(Consultation newElement) throws DaoException {
        try(BufferedWriter writer = new BufferedWriter((new FileWriter(consFile,true)))){
            Integer maxId = findAll().stream().mapToInt(Consultation::getId)
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
    public void update(Consultation newValuesElement) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(consFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(consFile));
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
        if(!consFile.delete()) throw new DaoException("Problème Technique dans la modification");
        if(!tempFile.renameTo(consFile)) throw new DaoException("Problème Technique dans la modification");
    }

    @Override
    public void deleteById(Integer identity) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(consFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(consFile));
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
        if(!consFile.delete()) throw new DaoException("Problème Technique dans la suppression");
        if(!tempFile.renameTo(consFile)) throw new DaoException("Problème Technique dans la suppression");
    }

    public static void main(String[] args) {
        //System.out.println(new ConsultationDao(new OrdonnanceDao(new MedicamentDao()), new InterventionDao(new ActeDao())).findAll());
        //System.out.println( new ConsultationDao(new OrdonnanceDao(new MedicamentDao()), new InterventionDao
                //(new ActeDao())).findById(101));
//        Ordonnance ord = new OrdonnanceDao(new MedicamentDao()).findById(201);
//        List<Intervention>inters = new ArrayList<>();
//        Intervention inter = new InterventionDao(new ActeDao()).findById(401);
//        inters.add(inter);
//        Consultation cons = new Consultation(null,TypeConsultation.suivi,ord,inters,"2025-01-22",900);
//        new ConsultationDao(new OrdonnanceDao(new MedicamentDao()), new InterventionDao
//                (new ActeDao())).save(cons);
//        new ConsultationDao(new OrdonnanceDao(new MedicamentDao()), new InterventionDao
//         (new ActeDao())).deleteById(105);

    }
}
