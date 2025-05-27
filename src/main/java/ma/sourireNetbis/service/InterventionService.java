package ma.sourireNetbis.service;

import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.dao.filebase_implementations.ActeDao;
import ma.sourireNetbis.dao.filebase_implementations.InterventionDao;
import ma.sourireNetbis.model.entities.Intervention;
import ma.sourireNetbis.service.exceptions.ServiceException;
import ma.sourireNetbis.service.interfaces.IService;

import java.util.List;

public class InterventionService implements IService<Intervention> {
    private InterventionDao interventionDao;

    public InterventionService(InterventionDao interventionDao) {
        this.interventionDao = interventionDao;
    }

    public InterventionDao getInterventionDao() {
        return interventionDao;
    }

    public void setInterventionDao(InterventionDao interventionDao) {
        this.interventionDao = interventionDao;
    }

    @Override
    public String toString() {
        return "InterventionService{" +
                "interventionDao=" + interventionDao +
                '}';
    }

    @Override
    public void create(Intervention intervention) throws ServiceException {
        try {
            interventionDao.save(intervention);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(Intervention intervention) throws ServiceException {
        try {
            interventionDao.update(intervention);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            interventionDao.deleteById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Intervention findById(Integer id) throws ServiceException {
        try {

            return interventionDao.findById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Intervention> listAll() throws ServiceException {
        try {
            return interventionDao.findAll();

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(new InterventionService(new InterventionDao(new ActeDao())).listAll());
    }
}
