package com.tc.webapp.service.impl;

import com.tc.webapp.dao.DAOException;
import com.tc.webapp.dao.DAOFactory;
import com.tc.webapp.dao.UserDAO;
import com.tc.webapp.entity.Person;
import com.tc.webapp.entity.User;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User authorisation(String login, String password) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        User user = null;
        try {
            user = userDAO.authorisation(login, password);
            System.out.println(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public User registration(Person user, String password) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        boolean regResult = false;
        User newUser = null;
        try {
            regResult = userDAO.registration(user, password);
            if (regResult) {
                newUser = userDAO.authorisation(user.getLogin(), password);
                System.out.println(newUser);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return newUser;
    }

    @Override
    public void subscribe(String journalName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unsubscribe(String journalName) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean changeUserStatus(String login, String status) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        boolean result;
        try {
            result = userDAO.changeUserStatus(login, status);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean changeUserStatus(String login, String status, String pubName, int pubRoyalty) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        boolean result;
        try {
            result = userDAO.changeUserStatus(login, status) && userDAO.addPublisherInfo(pubName, login, pubRoyalty);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean addJournal(String pubLogin, String titleName, int titlePrice, int releaseNumber, String fileUrl) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        boolean result;
        try {
            System.out.println(pubLogin);
            int userId = userDAO.getUserID(pubLogin);
            System.out.println(userId);
            int pubId = userDAO.getPublisherID(userId);
            System.out.println("pub id " + pubId);
            int journalId = userDAO.getTitleID(titleName);
            System.out.println("journal id " + journalId);
            String titleFormDB = userDAO.getTitleName(journalId);
            if (journalId == 0) {
                result = userDAO.addJournal(pubId, titleName, titlePrice, releaseNumber);
                result = result && userDAO.addJournalRelease(releaseNumber, journalId);
            } else {
                if (titleFormDB.equals(titleName)) {
                    result = userDAO.addJournalRelease(releaseNumber, journalId);
                } else {
                    result = userDAO.addJournal(pubId, titleName, titlePrice, releaseNumber);
                    result = result && userDAO.addJournalRelease(releaseNumber, journalId);
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
