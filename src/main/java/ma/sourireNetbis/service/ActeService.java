package ma.sourireNetbis.service;

import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.dao.filebase_implementations.ActeDao;
import ma.sourireNetbis.model.entities.Acte;
import ma.sourireNetbis.service.exceptions.ServiceException;
import ma.sourireNetbis.service.interfaces.IService;

import java.util.List;

public class ActeService implements IService<Acte> {
    private ActeDao acteDao;

    public ActeService(ActeDao acteDao) {
        this.acteDao = acteDao;
    }

    public ActeDao getActeDao() {
        return acteDao;
    }

    public void setActeDao(ActeDao acteDao) {
        this.acteDao = acteDao;
    }

    @Override
    public String toString() {
        return "ActeService{" +
                "acteDao=" + acteDao +
                '}';
    }

    @Override
    public void create(Acte acte) throws ServiceException {
        try {

            acteDao.save(acte);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(Acte acte) throws ServiceException {
        try {

            acteDao.update(acte);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {

            acteDao.deleteById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Acte findById(Integer id) throws ServiceException {
        try {

            return acteDao.findById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Acte> listAll() throws ServiceException {
        try {

            return acteDao.findAll();

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(new ActeService(new ActeDao()).findById(501));
    }
}
