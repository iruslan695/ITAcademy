package com.tc.webapp.dao;

import java.util.ArrayList;

public interface JournalDAO {

    boolean addJournal(int pubId, String titleName, int titlePrice, int releaseNumber) throws DAOException;

    int getTitleID(String titleName) throws DAOException;

    boolean addJournalRelease(int releaseNumber, int titleId) throws DAOException;

    String getTitleName(int titleId) throws DAOException;

    boolean setJournalSubjects(ArrayList<String> journalSubjects, int journalId) throws DAOException;

    int getSubjectID(String subjectName) throws DAOException;

    boolean subscribe(int journalId, int userId, String startDate, String endDate) throws DAOException;

    int getSubscriptionId(int journalId, int userId) throws DAOException;

    boolean deleteSubscriptionId(int subId) throws DAOException;

    int getJournalPrice(String journalName) throws DAOException;

    String getPublisherName(String journalName) throws DAOException;

    boolean isSubbed(String login, String journalName) throws DAOException;

}
