package ma.sourireNetbis.service;

import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.dao.filebase_implementations.MedicamentDao;
import ma.sourireNetbis.model.entities.Medicament;
import ma.sourireNetbis.service.exceptions.ServiceException;
import ma.sourireNetbis.service.interfaces.IService;

import java.util.List;

public class MedicamentService implements IService<Medicament> {
    private MedicamentDao medicamentDao;

    public MedicamentService(MedicamentDao medicamentDao) {
        this.medicamentDao = medicamentDao;
    }
    @Override
    public void create(Medicament medicament) throws ServiceException {
        try {
            medicamentDao.save(medicament);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(Medicament medicament) throws ServiceException {
        try {
            medicamentDao.update(medicament);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            medicamentDao.deleteById(id);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Medicament findById(Integer id) throws ServiceException {
        try {
            return medicamentDao.findById(id);
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Medicament> listAll() throws ServiceException {
        try {
            return medicamentDao.findAll();
        }catch (DaoException e )
        {
            throw new ServiceException(e.getMessage());
        }
    }
    public static void main(String[] args) {
        System.out.println(new MedicamentService(new MedicamentDao()).findById(601));
    }
}
