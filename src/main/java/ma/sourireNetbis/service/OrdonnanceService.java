package ma.sourireNetbis.service;

import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.dao.filebase_implementations.MedicamentDao;
import ma.sourireNetbis.dao.filebase_implementations.OrdonnanceDao;
import ma.sourireNetbis.model.entities.Ordonnance;
import ma.sourireNetbis.service.exceptions.ServiceException;
import ma.sourireNetbis.service.interfaces.IService;

import java.util.List;

public class OrdonnanceService implements IService<Ordonnance> {
    private OrdonnanceDao ordonnanceDao;

    public OrdonnanceService(OrdonnanceDao ordonnanceDao) {
        this.ordonnanceDao = ordonnanceDao;
    }



    @Override
    public void create(Ordonnance ordonnance) throws ServiceException {
        try {

            ordonnanceDao.save(ordonnance);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void update(Ordonnance ordonnance) throws ServiceException {
        try {

            ordonnanceDao.update(ordonnance);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {

        try {

            ordonnanceDao.deleteById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public Ordonnance findById(Integer id) throws ServiceException {
        try {

            return ordonnanceDao.findById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Ordonnance> listAll() throws ServiceException {
        try {

            return ordonnanceDao.findAll();

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(new OrdonnanceService(new OrdonnanceDao(new MedicamentDao())).listAll());
    }
}
