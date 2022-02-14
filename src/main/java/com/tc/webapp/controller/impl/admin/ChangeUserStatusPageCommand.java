package com.tc.webapp.controller.impl.admin;

import com.tc.webapp.controller.Command;
import com.tc.webapp.entity.ControllerConstant;
import com.tc.webapp.entity.SQLCharacteristic;
import com.tc.webapp.service.ServiceException;
import com.tc.webapp.service.ServiceFactory;
import com.tc.webapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeUserStatusPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(SQLCharacteristic.LOGIN);
        String role = request.getParameter(SQLCharacteristic.ROLE);
        String pubName = request.getParameter(ControllerConstant.PUBLISHER_NAME);
        String pubRoyalty = request.getParameter(ControllerConstant.PUBLISHER_ROYALTY);
        int pubRoyaltyInt;
        System.out.println(login);
        System.out.println(role);

        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        boolean result;
        try {
            if (role.equals(SQLCharacteristic.ROLE_PUBLISHER) && (pubName == null || pubRoyalty == null)) {
                response.sendRedirect("Controller?command=goToAddPublisherInfoPage&login=" + login);
            } else if (pubName == null && pubRoyalty == null) {
                result = userService.changeUserStatus(login, role);
                if (result) {
                    response.sendRedirect("Controller?command=goToChangeUserStatusPage&changeResult=success");
                } else {
                    response.sendRedirect("Controller?command=goToChangeUserStatusPage&changeResult=error");
                }
            } else if ((pubName == null && pubRoyalty != null) || (pubName != null && pubRoyalty == null)) {
                response.sendRedirect("Controller?command=goToChangeUserStatusPage&changeResult=error");
            } else {
                System.out.println("info added");
                pubRoyaltyInt = Integer.parseInt(pubRoyalty);
                result = userService.changeUserStatus(login, role, pubName, pubRoyaltyInt);
                if (result) {
                    response.sendRedirect("Controller?command=goToChangeUserStatusPage&changeResult=success");
                } else {
                    response.sendRedirect("Controller?command=goToChangeUserStatusPage&changeResult=error");
                }
            }
        } catch (ServiceException e) {
            response.sendRedirect("Controller?command=goToErrorPage");
        }
    }
}
