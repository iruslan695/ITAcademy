package com.tc.webapp.controller.impl.general;

import com.tc.webapp.controller.Command;
import com.tc.webapp.entity.bean.User;
import com.tc.webapp.entity.constant.ControllerConstant;
import com.tc.webapp.entity.constant.SQLCharacteristic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToSubscribePageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SQLCharacteristic.USER);
        int balance = user.getBalance();
        request.setAttribute(ControllerConstant.BALANCE, balance);
        String journalName = request.getParameter(ControllerConstant.JOURNAL_NAME);
        int journalPrice = Integer.parseInt(request.getParameter(ControllerConstant.JOURNAL_PRICE));
        request.setAttribute(ControllerConstant.JOURNAL_NAME, journalName);
        request.setAttribute(ControllerConstant.JOURNAL_PRICE, journalPrice);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/subscribePage.jsp");
        dispatcher.forward(request, response);
    }
}
