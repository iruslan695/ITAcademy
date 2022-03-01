package com.tc.webapp.controller.impl.general;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tc.webapp.controller.Command;
import com.tc.webapp.controller.FrontCommand;
import com.tc.webapp.entity.constant.ControllerConstant;
import com.tc.webapp.entity.constant.SubjectName;
import com.tc.webapp.service.JournalService;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;

public class CatalogPageCommand implements Command {
    private static String URL = "Controller?command=goToJournalPage&journalName=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subject = request.getParameter(SubjectName.SUBJECT);
        HashSet<String> journals;
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        JournalService journalService = serviceFactory.getJournalService();
        Map<String, String> journalMap = new HashMap<>();
        String journal;
        try {
            journals = journalService.getJournals(subject);
            ArrayList<String> journalList = new ArrayList<>(journals);
            for (int i = 0; i < journalList.size(); i++) {
                journal = journalList.get(i);
                journalMap.put(journal, URL + journal);
            }
            request.setAttribute(ControllerConstant.JOURNAL_SET, journalMap);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/subjectJournalsPage.jsp");
            dispatcher.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_ERROR_PAGE);
        }
    }
}
