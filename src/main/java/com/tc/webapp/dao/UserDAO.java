package com.tc.webapp.dao;


import com.tc.webapp.entity.Person;
import com.tc.webapp.entity.User;

public interface UserDAO {

    User authorisation(String login, String password) throws DAOException;

    boolean registration(Person user, String password) throws DAOException;

    boolean changeUserStatus(String login, String role) throws DAOException;

    void setUserInterests(int id, String[] interests) throws DAOException;

    int getSubjectID(String subjectName) throws DAOException;

    int getUserID(String login) throws DAOException;

    String[] getUserInterests(int id) throws DAOException;

    int getRoleID(String role) throws DAOException;

    int getPublisherID(int userId) throws DAOException;

    boolean addPublisherInfo(String pubInfo, String login, int pubRoyalty) throws DAOException;

    boolean addJournal(int pubId, String titleName, int titlePrice, int releaseNumber) throws DAOException;

    int getTitleID(String titleName) throws DAOException;

    boolean addJournalRelease(int releaseNumber, int titleId) throws DAOException;

    String getTitleName(int titleId) throws DAOException;
}
