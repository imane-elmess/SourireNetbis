package ma.sourireNetbis.service.interfaces;

import ma.sourireNetbis.service.exceptions.ServiceException;

import java.util.List;

public interface IService<T> {
    void create(T t) throws ServiceException;
    void update(T t) throws ServiceException;
    void delete(Integer id) throws ServiceException;
    T findById(Integer id) throws ServiceException;
    List<T> listAll() throws ServiceException;
}
