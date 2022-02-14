package com.tc.webapp.dao.impl;


import com.tc.webapp.dao.connection.ConnectionPool;
import com.tc.webapp.dao.connection.ConnectionPoolException;
import com.tc.webapp.dao.DAOException;
import com.tc.webapp.dao.UserDAO;
import com.tc.webapp.entity.Person;
import com.tc.webapp.entity.SQLCharacteristic;
import com.tc.webapp.entity.User;

import java.sql.*;
import java.util.ArrayList;

public class SQLUserDAO implements UserDAO {
    private static final String AUTHORISATION_QUERY = "SELECT id,name,surname,age,male,login,balance,roles_id" +
            " FROM subscribtion.user WHERE login=? AND password=?;";
    private static final String REGISTRATION_QUERY = "INSERT INTO subscribtion.user" +
            " (login,password,name,surname,male,roles_id,age,balance) " +
            "VALUES (?,?,?,?,?,?,?,?)";

    private static final String CHANGE_USER_STATUS_QUERY = "UPDATE subscribtion.user SET roles_id = ? WHERE (login = ?)";
    private static final String GET_SUBJECT_ID = "SELECT id FROM subscribtion.subjects WHERE s_name=?";
    private static final String GET_USER_ID = "SELECT id FROM subscribtion.user WHERE login=?";
    private static final String GET_ROLE_ID = "SELECT id FROM subscribtion.roles WHERE role_name=?";
    private static final String SET_USER_INTERESTS = "INSERT INTO subscribtion.interest (subjects_id,user_id) VALUES (?,?)";
    private static final String SET_PUBLISHER = "INSERT INTO subscribtion.publisher (company_name,royalty,user_id) VALUES (?,?,?)";
    private static final String GET_USER_INTERESTS = "SELECT s_name FROM subscribtion.interest JOIN subscribtion.subjects ON subscribtion.interest.subjects_id = subscribtion.subjects.id WHERE user_id = ?";
    private static final String GET_PUBLISHER_ID = "SELECT publisher_id FROM subscribtion.publisher WHERE user_id=?";
    private static final String SET_JOURNAL = "INSERT INTO subscribtion.journal (name,price,publisher_id) VALUES (?,?,?)";
    private static final String GET_JOURNAL_ID = "SELECT id FROM subscribtion.journal WHERE name=?";
    private static final String SET_JOURNAL_RELEASE = "INSERT INTO subscribtion.specific_journal (number,journal_id) VALUES (?,?)";
    private static final String GET_TITLE_NAME = "SELECT name FROM subscribtion.journal WHERE id=?";


    @Override
    public User authorisation(String login, String password) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        int id = 0;
        String name = null;
        String surname = null;
        String male = null;
        int age = 0;
        String role = null;
        int roleId = 0;
        int balance = 0;
        String[] interests;
        User user = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(AUTHORISATION_QUERY);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt(SQLCharacteristic.ID);
                name = resultSet.getString(SQLCharacteristic.NAME);
                surname = resultSet.getString(SQLCharacteristic.SURNAME);
                male = resultSet.getString(SQLCharacteristic.MALE);
                age = resultSet.getInt(SQLCharacteristic.AGE);
                roleId = resultSet.getInt(SQLCharacteristic.ROLE_ID);
                role = (roleId == SQLCharacteristic.READER_ID ?
                        SQLCharacteristic.ROLE_READER :
                        (roleId == SQLCharacteristic.PUBLISHER_ID ? SQLCharacteristic.ROLE_PUBLISHER :
                                SQLCharacteristic.ROLE_ADMIN));
                balance = resultSet.getInt(SQLCharacteristic.BALANCE);
            }
            interests = getUserInterests(id);
            user = new User(id, role, balance, name, surname, male, interests, age, login);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPoolException", e);
        } catch (SQLException e) {
            throw new DAOException("SQLException", e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException("SQLException", e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException("SQLException", e);
            }
        }
        return user;
    }

    @Override
    public boolean registration(Person user, String password) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        boolean result;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(REGISTRATION_QUERY);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getMale());
            preparedStatement.setInt(6, SQLCharacteristic.READER_ID);
            preparedStatement.setInt(7, user.getAge());
            preparedStatement.setInt(8, SQLCharacteristic.START_BALANCE);
            preparedStatement.executeUpdate();
            setUserInterests(getUserID(user.getLogin()), user.getInterests());
            result = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                preparedStatement.close();

            } catch (SQLException e) {
                throw new DAOException("SQLException", e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException("SQLException", e);
            }
        }
        return result;
    }

    @Override
    public boolean changeUserStatus(String login, String role) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int newRoleId;
        boolean result = true;
        try {
            if (getUserID(login) == 0) {
                result = false;
            }
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CHANGE_USER_STATUS_QUERY);
            newRoleId = getRoleID(role);
            preparedStatement.setInt(1, newRoleId);
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
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
    public void setUserInterests(int userID, String[] interests) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int subjectIndex = 1;
        int userIndex = 2;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SET_USER_INTERESTS);
            for (int i = 0; i < interests.length; i++) {
                preparedStatement.setInt(subjectIndex, getSubjectID(interests[i]));
                preparedStatement.setInt(userIndex, userID);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
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
    public String[] getUserInterests(int id) throws DAOException {
        ArrayList<String> interestsList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        String[] interests = null;
        int index = 1;
        String interest;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_USER_INTERESTS);
            preparedStatement.setInt(index, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                interest = resultSet.getString(SQLCharacteristic.SUBJECT_NAME);
                interestsList.add(interest);
            }
            interests = interestsList.toArray(new String[0]);
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
        return interests;
    }

    @Override
    public int getRoleID(String role) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        int index = 1;
        int roleId = 0;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ROLE_ID);
            preparedStatement.setString(index, role);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                roleId = resultSet.getInt(SQLCharacteristic.ID);
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
        return roleId;
    }

    @Override
    public int getPublisherID(int userId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet;
        int index = 1;
        int pubId = 0;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_PUBLISHER_ID);
            preparedStatement.setInt(index, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pubId = resultSet.getInt(SQLCharacteristic.PUBLISHER_ID_STR);
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
        return pubId;
    }

    @Override
    public boolean addPublisherInfo(String pubInfo, String login, int pubRoyalty) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int pubInfoIndex = 1;
        int pubRoyaltyIndex = 2;
        int userIdIndex = 3;
        boolean result;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SET_PUBLISHER);
            preparedStatement.setString(pubInfoIndex, pubInfo);
            preparedStatement.setInt(pubRoyaltyIndex, pubRoyalty);
            preparedStatement.setInt(userIdIndex, getUserID(login));
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
}
