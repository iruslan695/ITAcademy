package com.tc.webapp.dao.impl;

import com.tc.webapp.dao.DAOException;
import com.tc.webapp.dao.DAOFactory;
import com.tc.webapp.dao.JournalDAO;
import com.tc.webapp.dao.UserDAO;
import com.tc.webapp.dao.connection.ConnectionPool;
import com.tc.webapp.dao.connection.ConnectionPoolException;
import com.tc.webapp.entity.constant.SQLCharacteristic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JournalDAOImpl implements JournalDAO {

    private static final String SET_JOURNAL = "INSERT INTO subscribtion.journal (name,price,publisher_id) VALUES (?,?,?)";
    private static final String GET_JOURNAL_ID = "SELECT id FROM subscribtion.journal WHERE name=?";
    private static final String SET_JOURNAL_RELEASE = "INSERT INTO subscribtion.specific_journal (number,journal_id) VALUES (?,?)";
    private static final String SET_JOURNAL_SUBJECT = "INSERT INTO subscribtion.subjects_has_journal (subjects_id,journal_id) VALUES (?,?)";
    private static final String GET_SUBJECT_ID = "SELECT id FROM subscribtion.subjects WHERE s_name=?";
    private static final String SUBSCRIBE = "INSERT INTO subscribtion.subscribtion (journal_id,user_id,start,finish,is_activated) VALUES (?,?,?,?,'y')";
    private static final String GET_SUBSCRIPTION_ID = "SELECT sub_id FROM subscribtion.subscribtion WHERE journal_id=? AND user_id=?";
    private static final String DELETE_SUBSCRIPTION = "DELETE FROM subscribtion.subscribtion WHERE (sub_id = ?)";
    private static final String GET_JOURNAL_PRICE = "SELECT price FROM subscribtion.journal WHERE name=?";
    private static final String GET_PUBLISHER = "SELECT company_name FROM subscribtion.journal JOIN subscribtion.publisher USING(publisher_id)\n" +
            "WHERE name=?";
    private static final String IS_SUBBED = "SELECT is_activated FROM subscribtion.subscribtion WHERE sub_id=?";
    private static final String GET_USER_ID = "SELECT id FROM subscribtion.user WHERE login=?";
    private static final String GET_JOURNAL_RELEASES = "SELECT number FROM subscribtion.specific_journal AS sp\n" +
            " JOIN subscribtion.journal AS j ON(sp.journal_id = j.id) WHERE name =? ORDER BY number";

    @Override
    public boolean addJournal(int pubId, String titleName, int titlePrice, int releaseNumber) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int nameIndex = 1;
        int priceIndex = 2;
        int pubIdIndex = 3;
        boolean result;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SET_JOURNAL);
            preparedStatement.setString(nameIndex, titleName);
            preparedStatement.setInt(priceIndex, titlePrice);
            preparedStatement.setInt(pubIdIndex, pubId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    @Override
    public int getTitleID(String titleName) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        int index = 1;
        int journalId = 0;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_JOURNAL_ID);
            preparedStatement.setString(index, titleName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                journalId = resultSet.getInt(SQLCharacteristic.JOURNAL_ID);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return journalId;
    }

    @Override
    public boolean addJournalRelease(int releaseNumber, int titleId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int releaseIndex = 1;
        int titleIndex = 2;
        boolean result;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SET_JOURNAL_RELEASE);
            preparedStatement.setInt(releaseIndex, releaseNumber);
            preparedStatement.setInt(titleIndex, titleId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException("Release number exception", e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    @Override
    public String getTitleName(int titleId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        int index = 1;
        String titleName = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_JOURNAL_ID);
            preparedStatement.setInt(index, titleId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                titleName = resultSet.getString(SQLCharacteristic.TITLE_NAME);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return titleName;
    }

    @Override
    public boolean setJournalSubjects(ArrayList<String> journalSubjects, int journalId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int subjectIdIndex = 1;
        int journalIdIndex = 2;
        boolean result;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SET_JOURNAL_SUBJECT);
            for (int i = 0; i < journalSubjects.size(); i++) {
                preparedStatement.setInt(subjectIdIndex, getSubjectID(journalSubjects.get(i)));
                preparedStatement.setInt(journalIdIndex, journalId);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            result = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    @Override
    public int getSubjectID(String subjectName) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        int index = 1;
        int userId = 0;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_SUBJECT_ID);
            preparedStatement.setString(index, subjectName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getInt(SQLCharacteristic.ID);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return userId;
    }

    @Override
    public boolean subscribe(int journalId, int userId, String startDate, String endDate) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int journalIdIndex = 1;
        int userIdIndex = 2;
        int startDateId = 3;
        int endDateIdIndex = 4;
        boolean result;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SUBSCRIBE);
            preparedStatement.setInt(journalIdIndex, journalId);
            preparedStatement.setInt(userIdIndex, userId);
            preparedStatement.setString(startDateId, startDate);
            preparedStatement.setString(endDateIdIndex, endDate);
            preparedStatement.executeUpdate();
            result = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    @Override
    public int getSubscriptionId(int journalId, int userId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        int subId = 0;
        int journalIdIndex = 1;
        int userIdIndex = 2;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_SUBSCRIPTION_ID);
            preparedStatement.setInt(journalIdIndex, journalId);
            preparedStatement.setInt(userIdIndex, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subId = resultSet.getInt(SQLCharacteristic.SUB_ID);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return subId;
    }

    @Override
    public boolean deleteSubscriptionId(int subId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int index = 1;
        boolean result;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_SUBSCRIPTION);
            preparedStatement.setInt(index, subId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    @Override
    public int getJournalPrice(String journalName) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        int index = 1;
        int journalPrice = 0;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_JOURNAL_PRICE);
            preparedStatement.setString(index, journalName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                journalPrice = resultSet.getInt(SQLCharacteristic.PRICE);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return journalPrice;
    }

    @Override
    public String getPublisherName(String journalName) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        int index = 1;
        String pubName = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_PUBLISHER);
            preparedStatement.setString(index, journalName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pubName = resultSet.getString(SQLCharacteristic.PUBLISHER_NAME);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return pubName;
    }

    @Override
    public boolean isSubbed(String login, String journalName) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        int index = 1;
        boolean result = false;
        int subId;
        String isSubbed = SQLCharacteristic.UNSUBBED;
        try {
            int titleID = getTitleID(journalName);
            int userId = getUserID(login);
            subId = getSubscriptionId(titleID, userId);
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(IS_SUBBED);
            preparedStatement.setInt(index, subId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isSubbed = resultSet.getString(SQLCharacteristic.IS_SUBBED);
            }
            if (isSubbed.equals(SQLCharacteristic.SUBBED)) {
                result = true;
            } else {
                result = false;
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    @Override
    public int getUserID(String login) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        int index = 1;
        int userId = 0;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_USER_ID);
            preparedStatement.setString(index, login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getInt(SQLCharacteristic.ID);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();

            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return userId;
    }

    @Override
    public ArrayList<Integer> getJournalReleases(String journalName) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        ArrayList<Integer> list = new ArrayList<>();
        int release;
        int index = 1;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_JOURNAL_RELEASES);
            preparedStatement.setString(index, journalName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 release = resultSet.getInt(SQLCharacteristic.NUMBER);
                 list.add(release);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return list;
    }
}

