package com.tc.webapp.service.impl;

import com.tc.webapp.controller.impl.publisher.DownloadJournalFilesPageCommand;
import com.tc.webapp.dao.DAOException;
import com.tc.webapp.dao.DAOFactory;
import com.tc.webapp.dao.JournalDAO;
import com.tc.webapp.dao.UserDAO;
import com.tc.webapp.service.JournalService;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class JournalServiceImpl implements JournalService {
    public static String DESCRIPTION_FILE = "description.txt";
    public static String DOWNLOAD_PATH = DownloadJournalFilesPageCommand.UPLOAD_PATH;

    @Override
    public boolean setJournalSubjects(ArrayList<String> subjects, String titleName) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        JournalDAO journalDAO = daoFactory.getPublisherDAO();
        boolean result;
        try {
            int titleID = journalDAO.getTitleID(titleName);
            if (titleID == 0) {
                result = journalDAO.setJournalSubjects(subjects, titleID);
            } else {
                result = true;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public ArrayList<String> getSubjects() throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        ArrayList<String> resultList;
        try {
            resultList = userDAO.getSubjects();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultList;
    }

    @Override
    public HashSet<String> getJournals(String subjectName) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        HashSet<String> set;
        try {
            set = userDAO.getJournalSetBySubject(subjectName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return set;
    }

    @Override
    public boolean subscribe(String login, String journalName, int subDuration) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        JournalDAO journalDAO = daoFactory.getPublisherDAO();
        boolean result;
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
            String currentDate = formatter.format(calendar.getTime());
            calendar.add(Calendar.MONTH, subDuration);
            String finishDate = formatter.format(calendar.getTime());
            int userId = userDAO.getUserID(login);
            int journalId = journalDAO.getTitleID(journalName);
            result = journalDAO.subscribe(journalId, userId, currentDate, finishDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean unsubscribe(String login, String journalName) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        JournalDAO journalDAO = daoFactory.getPublisherDAO();
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        JournalService journalService = serviceFactory.getJournalService();
        boolean result;
        try {
            int subId = journalService.getSubscriptionId(login, journalName);
            result = journalDAO.deleteSubscriptionId(subId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public int getSubscriptionId(String login, String journalName) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        JournalDAO journalDAO = daoFactory.getPublisherDAO();
        UserDAO userDAO = daoFactory.getUserDAO();
        int subscriptionId;
        try {
            int userId = userDAO.getUserID(login);
            int journalId = journalDAO.getTitleID(journalName);
            subscriptionId = journalDAO.getSubscriptionId(journalId, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return subscriptionId;
    }

    @Override
    public boolean calculateSubscription(int balance, int subDurationInMonths, String journalName, String login) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        JournalDAO journalDAO = daoFactory.getPublisherDAO();
        UserDAO userDAO = daoFactory.getUserDAO();
        boolean result;
        try {
            int journalPrice = journalDAO.getJournalPrice(journalName);
            if (balance < subDurationInMonths * journalPrice) {
                result = false;
            } else {
                int userId = userDAO.getUserID(login);
                result = userDAO.setBalance(balance - subDurationInMonths * journalPrice, userId);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public void addJournalDescription(String uploadPath, String journalDescription) throws ServiceException {
        try {
            File file = new File(uploadPath + DESCRIPTION_FILE);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(journalDescription);
            fileWriter.close();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String getJournalDescription(String journalName) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        JournalDAO journalDAO = daoFactory.getPublisherDAO();
        String pubName;
        String downloadPath;
        String description = null;
        try {
            pubName = journalDAO.getPublisherName(journalName);
            downloadPath = DOWNLOAD_PATH + File.separator + pubName + File.separator + journalName + File.separator + DESCRIPTION_FILE;
            File file = new File(downloadPath);
            FileReader fileReader = new FileReader(file);
            StringBuilder stringBuilder = new StringBuilder();
            int c;
            while ((c = fileReader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            fileReader.close();
            description = stringBuilder.toString();
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (FileNotFoundException e) {
            throw new ServiceException(e);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
        return description;
    }

    @Override
    public int getJournalPrice(String journalName) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        JournalDAO journalDAO = daoFactory.getPublisherDAO();
        int price = 0;
        try {
            price = journalDAO.getJournalPrice(journalName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return price;
    }

    @Override
    public boolean isSubbed(String login, String journalName) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        JournalDAO journalDAO = daoFactory.getPublisherDAO();
        boolean result;
        try {
            result = journalDAO.isSubbed(login, journalName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}


