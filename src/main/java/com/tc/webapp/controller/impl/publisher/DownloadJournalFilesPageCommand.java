package com.tc.webapp.controller.impl.publisher;

import com.tc.webapp.controller.Command;
import com.tc.webapp.controller.FrontCommand;
import com.tc.webapp.entity.constant.ControllerConstant;
import com.tc.webapp.entity.constant.SQLCharacteristic;
import com.tc.webapp.entity.bean.User;
import com.tc.webapp.service.JournalService;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;
import com.tc.webapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DownloadJournalFilesPageCommand implements Command {
    public static final String UPLOAD_PATH = "C:/Uploads";
    public static final String FILE_EXTENSION = ".pdf";
    public static final String SEPARATOR = "/";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titleName = request.getParameter(ControllerConstant.TITLE_NAME);
        int titlePrice = Integer.parseInt(request.getParameter(ControllerConstant.TITLE_PRICE));
        int releaseNumber = Integer.parseInt(request.getParameter(ControllerConstant.RELEASE_NUMBER));
        String fileUrl = request.getParameter(ControllerConstant.FILE_URL);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        JournalService journalService = serviceFactory.getJournalService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SQLCharacteristic.USER);
        String login = user.getLogin();
        String journalDescription = request.getParameter(ControllerConstant.JOURNAL_DESCRIPTION);
        System.out.println(journalDescription);
        ArrayList<String> subjects = new ArrayList<>(Arrays.asList(request.getParameterValues(ControllerConstant.JOURNAL_SUBJECT)));
        boolean result;
        try {
            result = userService.addJournal(login, titleName, titlePrice, releaseNumber) && journalService.setJournalSubjects(subjects, titleName);
            if (result) {

                String pubName = userService.getPublisherName(login);
                String uploadPath = UPLOAD_PATH + SEPARATOR + pubName + SEPARATOR + titleName + SEPARATOR;
                System.out.println(uploadPath);
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                for (Part part : request.getParts()) {
                    part.write(uploadPath + File.separator + releaseNumber + FILE_EXTENSION);
                }
                journalService.addJournalDescription(uploadPath, journalDescription);
                response.sendRedirect("Controller?command=goToDownloadJournalFilesPage&downloadInfo=ok");
            } else {
                response.sendRedirect("Controller?command=goToDownloadJournalFilesPage&downloadInfo=error");
            }
        } catch (ServiceException e) {
            if (e.getMessage().equals("Release number exception")) {
                response.sendRedirect("Controller?command=goToDownloadJournalFilesPage&downloadInfo=error");
            } else {
                response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_ERROR_PAGE);
            }
        }
    }
}
