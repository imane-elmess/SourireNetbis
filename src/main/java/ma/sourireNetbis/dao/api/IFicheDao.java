package ma.sourireNetbis.dao.api;

import ma.sourireNetbis.dao.ICRUDDAO;
import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.model.entities.FicheMedicale;

public interface IFicheDao extends ICRUDDAO <FicheMedicale,Integer> {

    FicheMedicale findFichePatientByCin(String CIN) throws DaoException;

}
