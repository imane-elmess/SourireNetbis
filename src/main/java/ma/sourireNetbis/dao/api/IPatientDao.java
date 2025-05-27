package ma.sourireNetbis.dao.api;

import ma.sourireNetbis.dao.ICRUDDAO;
import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.model.entities.Patient;

import java.util.List;

public interface IPatientDao extends ICRUDDAO<Patient, Integer> {
    Patient findPatientByCINLike(String motCle) throws DaoException;

}
