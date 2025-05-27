package ma.sourireNetbis.service;

import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.dao.filebase_implementations.*;
import ma.sourireNetbis.model.entities.FicheMedicale;
import ma.sourireNetbis.service.exceptions.ServiceException;
import ma.sourireNetbis.service.interfaces.IFicheService;

import java.util.List;

public class FicheService implements IFicheService {
    private FicheDao ficheDao;

    public FicheService(FicheDao ficheDao) {
        this.ficheDao = ficheDao;
    }

    @Override
    public FicheMedicale findPatientFicheByCin(String CIN) throws ServiceException {
        try {
            return ficheDao.findFichePatientByCin(CIN);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void create(FicheMedicale ficheMedicale) throws ServiceException {
        try {
             ficheDao.save(ficheMedicale);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(FicheMedicale ficheMedicale) throws ServiceException {
        try {
            ficheDao.update(ficheMedicale);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            ficheDao.deleteById(id);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public FicheMedicale findById(Integer id) throws ServiceException {
        try {
            return ficheDao.findById(id);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<FicheMedicale> listAll() throws ServiceException {
        try {
            return ficheDao.findAll();
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        PatientDao patientDao1 = new PatientDao();
        ConsultationDao consultationDao1 = new ConsultationDao(new OrdonnanceDao(new MedicamentDao()),
                new InterventionDao(new ActeDao()));
        OrdonnanceDao ordonnanceDao1 = new OrdonnanceDao(new MedicamentDao());
        RdvDao rdvDao1 = new RdvDao(new ConsultationDao(new OrdonnanceDao(new MedicamentDao()),
                new InterventionDao(new ActeDao())));
        System.out.println(new FicheService(new FicheDao(patientDao1,consultationDao1,rdvDao1,ordonnanceDao1)).findPatientFicheByCin("AB123456"));
    }
}
