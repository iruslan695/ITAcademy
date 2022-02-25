package com.tc.webapp.controller.impl.general;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tc.webapp.controller.Command;
import com.tc.webapp.controller.FrontCommand;
import com.tc.webapp.entity.constant.ControllerConstant;
import com.tc.webapp.service.JournalService;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;

public class GoToCatalogPageCommand implements Command {
    private static String URL = "Controller?command=catalogPage&subject=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        JournalService journalService = serviceFactory.getJournalService();
        ArrayList<String> subjectList;
        Map<String, String> subjectMap = new HashMap<>();
        String subject;
        try {
            subjectList = journalService.getSubjects();
            for (int i = 0; i < subjectList.size(); i++) {
                subject = subjectList.get(i);
                subjectMap.put(subject, URL + subject);
            }
            request.setAttribute(ControllerConstant.SUBJECT_MAP, subjectMap);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/catalogPage.jsp");
            dispatcher.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_ERROR_PAGE);
        }
    }
}
