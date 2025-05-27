package ma.sourireNetbis.dao;

import ma.sourireNetbis.dao.exceptions.DaoException;

import java.util.List;

public interface ICRUDDAO<T,ID> {
    List<T> findAll() throws DaoException; //je crée ma propre exception
    T findById(ID identity) throws DaoException;
    T save(T newElement) throws DaoException;
    void update(T newValuesElement) throws DaoException;
    void deleteById(ID identity) throws DaoException;
}
