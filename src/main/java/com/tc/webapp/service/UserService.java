package com.tc.webapp.service;

import com.tc.webapp.entity.Person;
import com.tc.webapp.entity.User;

public interface UserService {

    User authorisation(String login, String password) throws ServiceException;

    User registration(Person user, String password) throws ServiceException;

    void subscribe(String journalName);

    void unsubscribe(String journalName);

    boolean changeUserStatus(String login, String status) throws ServiceException;

    boolean changeUserStatus(String login, String status, String pubName, int pubRoyalty) throws ServiceException;

    boolean addJournal(String pubLogin, String titleName, int titlePrice, int releaseNumber, String fileUrl) throws ServiceException;

}
