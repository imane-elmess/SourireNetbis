package ma.sourireNetbis.dao.filebase_implementations;

import ma.sourireNetbis.dao.api.*;
import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.model.entities.*;
import ma.sourireNetbis.model.enums.StatutPaiement;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FicheDao implements IFicheDao {
    private IPatientDao patientDao;
    private IConsultationDao consultationDao;
    private IRdvDao rdvDao;
    private IOrdonnanceDao ordonnanceDao;

    public FicheDao(IPatientDao patientDao, IConsultationDao consultationDao, IRdvDao rdvDao, IOrdonnanceDao ordonnanceDao) {
        this.patientDao = patientDao;
        this.consultationDao = consultationDao;
        this.rdvDao = rdvDao;
        this.ordonnanceDao = ordonnanceDao;
    }

    public IConsultationDao getConsultationDao() {
        return consultationDao;
    }

    public void setConsultationDao(IConsultationDao consultationDao) {
        this.consultationDao = consultationDao;
    }

    public IRdvDao getRdvDao() {
        return rdvDao;
    }

    public void setRdvDao(IRdvDao rdvDao) {
        this.rdvDao = rdvDao;
    }

    public IOrdonnanceDao getOrdonnanceDao() {
        return ordonnanceDao;
    }

    public void setOrdonnanceDao(IOrdonnanceDao ordonnanceDao) {
        this.ordonnanceDao = ordonnanceDao;
    }

    public IPatientDao getPatientDao() {
        return patientDao;
    }

    public void setPatientDao(IPatientDao patientDao) {
        this.patientDao = patientDao;
    }

    private static final File ficheFile = new File("myFileBase/fiches.txt");

    private String mapToLine(FicheMedicale fiche){
       // ID|ID_PATIENT|DENTISTE|STATUTPAIEMENT|CONSULTATION_IDS|ORDONNANCE_IDS|RDV_IDS
        String id = fiche.getId().toString();
        String id_patient = fiche.getPatient().getId().toString();
        String dentiste = fiche.getDentiste();
        String statut = fiche.getStatutPaiement()!=null ? fiche.getStatutPaiement().toString(): "null";
        // Liste des consultations associées
        StringBuilder consultationIds = new StringBuilder();

        for (Consultation cons : fiche.getConsultations()) {
            if (consultationIds.length() > 0) {
                consultationIds.append(",");
            }
            consultationIds.append(cons.getId());
        }
        // Liste des ordonnances associées
        StringBuilder ordonnanceIds = new StringBuilder();

        for (Ordonnance ord : fiche.getOrdonnances()) {
            if (ordonnanceIds.length() > 0) {
                ordonnanceIds.append(",");
            }
            ordonnanceIds.append(ord.getId());
        }
        // Liste des rdvs associées
        StringBuilder rdvIds = new StringBuilder();

        for (Rdv rdv : fiche.getRdvs()) {
            if (rdvIds.length() > 0) {
                rdvIds.append(",");
            }
            rdvIds.append(rdv.getId());
        }

        // ID|ID_PATIENT|DENTISTE|STATUTPAIEMENT|CONSULTATION_IDS|ORDONNANCE_IDS|RDV_IDS
        return id + "|" + id_patient + "|" + dentiste + "|" + statut + "|" + consultationIds + "|" + ordonnanceIds + "|" + rdvIds;

    }
    private FicheMedicale mapToFiche(String fileLine) throws DaoException {

// ID|ID_PATIENT|DENTISTE|STATUTPAIEMENT|CONSULTATION_IDS|ORDONNANCE_IDS|RDV_IDS
        try {
            String[] values = fileLine.split("\\|"); // Séparer avec le délimiteur '|'
            // Extraction des champs
            Integer id = Integer.parseInt(values[0].trim());
            Patient patient = patientDao.findById(Integer.parseInt(values[1].trim()));
            String dentiste = values[2].trim();
            StatutPaiement statutPaiement = values[3].equalsIgnoreCase("Payé") ? StatutPaiement.Payé :
                    values[3].equalsIgnoreCase("En_attente") ? StatutPaiement.En_attente :
                            values[3].equalsIgnoreCase("non_Payé") ? StatutPaiement.non_Payé : null;

            String[] consIds = values[4].split(","); // Liste des consultations
            List<Consultation> cons = new ArrayList<>();
            // Ajouter les consultations à la liste
            for (String conId : consIds) {
                if (!conId.equals("null")) {
                    cons.add(consultationDao.findById(Integer.parseInt(conId)));
                }
            }
            String[]ordIds = values[5].split(","); // Liste des consultations
            List<Ordonnance>ords = new ArrayList<>();
            // Ajouter les ordonnances à la liste
            for (String ordId :ordIds) {
                if (!ordId.equals("null")) {
                   ords.add(ordonnanceDao.findById(Integer.parseInt(ordId)));
                }
            }
            String[] rdvIds = values[6].split(","); // Liste des consultations
            List<Rdv> rdvs = new ArrayList<>();
            // Ajouter les consultations à la liste
            for (String rdvId : rdvIds) {
                if (!rdvId.equals("null")) {
                    rdvs.add(rdvDao.findById(Integer.parseInt(rdvId)));
                }
            }
                FicheMedicale fiche = new FicheMedicale(id,patient,dentiste,statutPaiement,cons,ords,rdvs);
            patient.setFiche(fiche);
            return fiche;

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new DaoException("Erreur lors de la conversion de la ligne en FicheMedicale: " + e.getMessage());
        }

    }
    @Override
    public FicheMedicale findFichePatientByCin(String CIN) throws DaoException {
        return findAll().stream().filter(d -> d.getPatient().getCin().contains(CIN))
                .findFirst()
                .orElseThrow(()->new DaoException("Aucune finche n'est lié au patient n° "+CIN));
    }

    @Override
    public List<FicheMedicale> findAll() throws DaoException {
        List<FicheMedicale>fiches = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(ficheFile.toPath());
            lines.remove(0); //Supprimer la ligne entete
            for(String line: lines){
                fiches.add(mapToFiche(line));
            }
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        return fiches;
    }

    @Override
    public FicheMedicale findById(Integer identity) throws DaoException {
        return findAll().stream()
                .filter(a -> a.getId().equals(identity))
                .findFirst()
                .orElseThrow(() -> new DaoException("Fiche avec ID " + identity + " introuvable"));
    }

    @Override
    public FicheMedicale save(FicheMedicale newElement) throws DaoException {
        try(BufferedWriter writer = new BufferedWriter((new FileWriter(ficheFile,true)))){
            Integer maxId = findAll().stream().mapToInt(FicheMedicale::getId)
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
    public void update(FicheMedicale newValuesElement) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(ficheFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(ficheFile));
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
        if(!ficheFile.delete()) throw new DaoException("Problème Technique dans la modification");
        if(!tempFile.renameTo(ficheFile)) throw new DaoException("Problème Technique dans la modification");
    }

    @Override
    public void deleteById(Integer identity) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(ficheFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(ficheFile));
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
        if(!ficheFile.delete()) throw new DaoException("Problème Technique dans la suppression");
        if(!tempFile.renameTo(ficheFile)) throw new DaoException("Problème Technique dans la suppression");
    }

    public static void main(String[] args) {
        PatientDao patientDao1 = new PatientDao();
        ConsultationDao consultationDao1 = new ConsultationDao(new OrdonnanceDao(new MedicamentDao()),
                new InterventionDao(new ActeDao()));
        OrdonnanceDao ordonnanceDao1 = new OrdonnanceDao(new MedicamentDao());
        RdvDao rdvDao1 = new RdvDao(new ConsultationDao(new OrdonnanceDao(new MedicamentDao()),
                new InterventionDao(new ActeDao())));

        System.out.println(new FicheDao(patientDao1,consultationDao1,rdvDao1,ordonnanceDao1).findById(1));
    }
}
