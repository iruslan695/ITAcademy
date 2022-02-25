package com.tc.webapp.dao;


import com.tc.webapp.entity.bean.Person;
import com.tc.webapp.entity.bean.User;

import java.util.ArrayList;
import java.util.HashSet;

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

    int getPublisherID(String pubName) throws DAOException;

    boolean addPublisherInfo(String pubInfo/*, String login*/, int pubRoyalty) throws DAOException;

    String getPublisherName(String userName) throws DAOException;

    boolean setPublisherPresenter(int pubId, int userId) throws DAOException;

    String getPublisherName(int pubId) throws DAOException;

    ArrayList<String> getSubjects() throws DAOException;

    HashSet<String> getJournalSetBySubject(String subject) throws DAOException;

    boolean setBalance(int value, int userId) throws DAOException;
}
