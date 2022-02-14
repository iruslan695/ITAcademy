package com.tc.webapp.dao;

public class DAOException extends Exception {

    public DAOException(Exception e) {
        super(e);
    }

    public DAOException(String message, Exception e) {
        super(message, e);
    }
}
