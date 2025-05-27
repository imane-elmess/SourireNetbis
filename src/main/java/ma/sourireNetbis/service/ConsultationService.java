package ma.sourireNetbis.service;

import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.dao.filebase_implementations.*;
import ma.sourireNetbis.model.entities.Consultation;
import ma.sourireNetbis.service.exceptions.ServiceException;
import ma.sourireNetbis.service.interfaces.IService;

import java.util.List;

public class ConsultationService implements IService<Consultation> {
    private ConsultationDao consultationDao;

    public ConsultationService(ConsultationDao consultationDao) {
        this.consultationDao = consultationDao;
    }

    @Override
    public void create(Consultation consultation) throws ServiceException {
        try {

           consultationDao.save(consultation);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(Consultation consultation) throws ServiceException {
        try {

            consultationDao.update(consultation);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {

           consultationDao.deleteById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Consultation findById(Integer id) throws ServiceException {
        try {

           return consultationDao.findById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Consultation> listAll() throws ServiceException {
        try {

            return consultationDao.findAll();

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(new ConsultationService(new ConsultationDao(new OrdonnanceDao(new MedicamentDao()),
                new InterventionDao(new ActeDao()))).findById(101));
    }
}
