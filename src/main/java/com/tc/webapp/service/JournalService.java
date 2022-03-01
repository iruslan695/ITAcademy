package com.tc.webapp.service;

import java.util.ArrayList;
import java.util.HashSet;

public interface JournalService {

    boolean setJournalSubjects(ArrayList<String> subjects, String titleName) throws ServiceException;

    ArrayList<String> getSubjects() throws ServiceException;

    HashSet<String> getJournals(String subjectName) throws ServiceException;

    boolean subscribe(String login, String journalName, int subDuration) throws ServiceException;

    boolean unsubscribe(String login, String journalName) throws ServiceException;

    int getSubscriptionId(String login, String journalName) throws ServiceException;

    boolean calculateSubscription(int balance, int subDurationInMonths, String journalName, String login) throws ServiceException;

    void addJournalDescription(String uploadPath, String journalDescription) throws ServiceException;

    String getJournalDescription(String journalName) throws ServiceException;

    int getJournalPrice(String journalName) throws ServiceException;

    String isSubbed(String login, String journalName) throws ServiceException;

    ArrayList<Integer> getJournalReleases(String journalName) throws ServiceException;

    String getPublisherName(String journalName) throws ServiceException;
}
