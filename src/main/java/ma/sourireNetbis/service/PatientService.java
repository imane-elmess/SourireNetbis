package ma.sourireNetbis.service;

import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.dao.filebase_implementations.PatientDao;
import ma.sourireNetbis.model.entities.Patient;
import ma.sourireNetbis.service.exceptions.ServiceException;
import ma.sourireNetbis.service.interfaces.IPatientService;

import java.util.List;

public class PatientService implements IPatientService {
    private PatientDao patientDao;

    public PatientService(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public PatientDao getPatientDao() {
        return patientDao;
    }

    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    public String toString() {
        return "PatientService{" +
                "patientDao=" + patientDao +
                '}';
    }

    @Override
    public Patient findPatientByCin(String keyword) throws ServiceException {
        try {

            return patientDao.findPatientByCINLike(keyword);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void create(Patient patient) throws ServiceException {
        try {

            patientDao.save(patient);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(Patient patient) throws ServiceException {
        try {

            patientDao.update(patient);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {

            patientDao.deleteById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Patient findById(Integer id) throws ServiceException {
        try {

            return patientDao.findById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Patient> listAll() throws ServiceException {
        try {

            return patientDao.findAll();

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public static void main(String[] args) {
        System.out.println(new PatientService(new PatientDao()).findById(1));
    }
}
