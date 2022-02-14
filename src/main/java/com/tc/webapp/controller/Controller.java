package com.tc.webapp.controller;

import com.tc.webapp.dao.connection.ConnectionPool;
import com.tc.webapp.dao.connection.ConnectionPoolException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CommandProvider provider = new CommandProvider();
    public static final String COMMAND = "command";

    public Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandName = request.getParameter(COMMAND);

        Command command = provider.getCommand(commandName);
        command.execute(request, response);
    }

    public void init() {
        ConnectionPool cp = ConnectionPool.getInstance();
    }

    public void destroy() {
        try {
            ConnectionPool.getInstance().dispose();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("Closing connection pool error", e);
        }
    }
}
