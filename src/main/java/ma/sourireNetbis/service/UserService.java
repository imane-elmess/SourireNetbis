package ma.sourireNetbis.service;

import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.dao.filebase_implementations.UserDao;
import ma.sourireNetbis.model.entities.User;
import ma.sourireNetbis.service.exceptions.ServiceException;
import ma.sourireNetbis.service.interfaces.IService;

import java.util.List;

public class UserService implements IService<User> {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "userDao=" + userDao +
                '}';
    }

    @Override
    public void create(User user) throws ServiceException {
        try {

            userDao.save(user);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void update(User user) throws ServiceException {
        try {

            userDao.update(user);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {

            userDao.deleteById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User findById(Integer id) throws ServiceException {
        try {

            return userDao.findById(id);

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> listAll() throws ServiceException {
        try {

            return userDao.findAll();

        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(new UserService(new UserDao()).listAll());
    }
}
