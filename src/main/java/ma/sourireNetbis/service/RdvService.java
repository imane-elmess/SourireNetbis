package ma.sourireNetbis.service;

import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.dao.filebase_implementations.*;
import ma.sourireNetbis.model.entities.Rdv;
import ma.sourireNetbis.service.exceptions.ServiceException;
import ma.sourireNetbis.service.interfaces.IService;

import java.util.List;

public class RdvService implements IService<Rdv> {
    private RdvDao rdvDao;

    public RdvService(RdvDao rdvDao) {
        this.rdvDao = rdvDao;
    }

    @Override
    public void create(Rdv rdv) throws ServiceException {
        try {
            rdvDao.save(rdv);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(Rdv rdv) throws ServiceException {
        try {
            rdvDao.update(rdv);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            rdvDao.deleteById(id);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Rdv findById(Integer id) throws ServiceException {
        try {
            return rdvDao.findById(id);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Rdv> listAll() throws ServiceException {
        try {
            return rdvDao.findAll();
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(new RdvService(new RdvDao(new ConsultationDao(new OrdonnanceDao(new MedicamentDao()),
                new InterventionDao(new ActeDao())))).listAll());
        System.out.println(new RdvService(new RdvDao(new ConsultationDao(new OrdonnanceDao
                (new MedicamentDao()), new InterventionDao(new ActeDao())))).findById(301));
    }
}
