package com.tc.webapp.controller.impl.publisher;

import com.tc.webapp.controller.Command;
import com.tc.webapp.controller.FrontCommand;
import com.tc.webapp.entity.ControllerConstant;
import com.tc.webapp.entity.SQLCharacteristic;
import com.tc.webapp.entity.User;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;
import com.tc.webapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DownloadJournalFilesPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titleName = request.getParameter(ControllerConstant.TITLE_NAME);
        int titlePrice = Integer.parseInt(request.getParameter(ControllerConstant.TITLE_PRICE));
        int releaseNumber = Integer.parseInt(request.getParameter(ControllerConstant.RELEASE_NUMBER));
        String fileUrl = request.getParameter(ControllerConstant.FILE_URL);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SQLCharacteristic.USER);
        String login = user.getLogin();
        System.out.println(login);
        boolean result;
        try {
            result = userService.addJournal(login, titleName, titlePrice, releaseNumber, fileUrl);
            if (result) {
                response.sendRedirect("Controller?command=accountPage&downloadInfo=ok");
            } else {
                response.sendRedirect("Controller?command=accountPage&downloadInfo=error");
            }
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=" + FrontCommand.GO_TO_ERROR_PAGE);
        }
    }
}
