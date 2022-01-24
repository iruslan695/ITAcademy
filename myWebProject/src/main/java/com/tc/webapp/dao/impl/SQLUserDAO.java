package com.tc.webapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tc.webapp.connection.ConnectionPool;
import com.tc.webapp.connection.ConnectionPoolException;
import com.tc.webapp.dao.DAOException;
import com.tc.webapp.dao.UserDAO;
import com.tc.webapp.entity.User;

public class SQLUserDAO implements UserDAO {

	@Override
	public String authorisation(String login, String password) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			Connection connection = connectionPool.takeConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT name,surname FROM subscribtion.users WHERE login=" + login + " AND password="
					+ password + ';';
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + ": " + resultSet.getString(2));
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return "admin";
	}

	@Override
	public void registration(User user) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			Connection connection = connectionPool.takeConnection();
			String query = "INSERT INTO subscribtion.users (login, password, name, surname, male,roles_id,age) VALUES (?,?,?,?,?,?,?);";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getName());
			preparedStatement.setString(4, user.getSurname());
			preparedStatement.setString(5, user.getMale());
			int readerId = 1;
			preparedStatement.setInt(6, readerId);
			preparedStatement.setInt(7, user.getAge());
			preparedStatement.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
