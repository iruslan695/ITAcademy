package com.tc.webapp.service.impl;

import com.tc.webapp.dao.DAOException;
import com.tc.webapp.dao.DAOFactory;
import com.tc.webapp.dao.JournalDAO;
import com.tc.webapp.dao.UserDAO;
import com.tc.webapp.entity.bean.Person;
import com.tc.webapp.entity.bean.User;
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

    @Override ////
    public boolean changeUserStatus(String login, String status, String pubName, int pubRoyalty) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        boolean result;
        try {
            int pubId = userDAO.getPublisherID(pubName);
            if (pubId == 0) {
                result = userDAO.changeUserStatus(login, status) && userDAO.addPublisherInfo(pubName/*, login*/, pubRoyalty);
                int userId = userDAO.getUserID(login);
                result = result && userDAO.setPublisherPresenter(pubId, userId);
            } else {
                int userId = userDAO.getUserID(login);
                result = userDAO.changeUserStatus(login, status) && userDAO.setPublisherPresenter(pubId, userId);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean addJournal(String pubLogin, String titleName, int titlePrice, int releaseNumber) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        JournalDAO journalDAO = daoFactory.getPublisherDAO();
        UserDAO userDAO = daoFactory.getUserDAO();
        boolean result;
        try {
            System.out.println(pubLogin);
            int userId = userDAO.getUserID(pubLogin);
            System.out.println(userId);
            int pubId = userDAO.getPublisherID(userId);
            int journalId = journalDAO.getTitleID(titleName);
            System.out.println(journalId);
            if (journalId == 0) {
                result = journalDAO.addJournal(pubId, titleName, titlePrice, releaseNumber);
                int newId = journalDAO.getTitleID(titleName);
                result = result && journalDAO.addJournalRelease(releaseNumber, newId);
            } else {
                result = journalDAO.addJournalRelease(releaseNumber, journalId);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public String getPublisherName(String login) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        int userId;
        int pubId;
        String pubName;
        try {
            userId = userDAO.getUserID(login);
            System.out.println("user id " + userId);
            pubId = userDAO.getPublisherID(userId);
            System.out.println("pubId " + pubId);
            pubName = userDAO.getPublisherName(pubId);
            System.out.println("pubName " + pubName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return pubName;
    }
}
