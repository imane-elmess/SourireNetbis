package ma.sourireNetbis.service.interfaces;

import ma.sourireNetbis.model.entities.FicheMedicale;
import ma.sourireNetbis.service.exceptions.ServiceException;

public interface IFicheService extends IService<FicheMedicale>{

    FicheMedicale findPatientFicheByCin(String CIN) throws ServiceException;
}
