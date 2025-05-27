package ma.sourireNetbis.service.interfaces;

import ma.sourireNetbis.model.entities.Patient;
import ma.sourireNetbis.service.exceptions.ServiceException;

import java.util.List;

public interface IPatientService extends IService<Patient> {
    // Recherche de patients contenant un mot-clé avec CIN
    Patient findPatientByCin(String keyword) throws ServiceException;
}
