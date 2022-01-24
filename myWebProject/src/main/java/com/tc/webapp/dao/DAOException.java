package com.tc.webapp.dao;

public class DAOException extends Exception {
	
	public DAOException(Exception e) {
		super(e);
	}
	
	public DAOException(Exception e, String message) {
		super(message,e);
	}
}
