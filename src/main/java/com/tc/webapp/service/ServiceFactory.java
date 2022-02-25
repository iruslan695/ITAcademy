package com.tc.webapp.service;

import com.tc.webapp.service.impl.JournalServiceImpl;
import com.tc.webapp.service.impl.UserServiceImpl;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final UserService userService = new UserServiceImpl();
    private final JournalService journalService = new JournalServiceImpl();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public JournalService getJournalService() {
        return journalService;
    }
}
