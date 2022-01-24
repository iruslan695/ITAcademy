package com.tc.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import com.tc.webapp.controller.impl.AccountPageCommand;
import com.tc.webapp.controller.impl.BookPageCommand;
import com.tc.webapp.controller.impl.CatalogPageCommand;
import com.tc.webapp.controller.impl.GoToAccountPageCommand;
import com.tc.webapp.controller.impl.GoToBookPageCommand;
import com.tc.webapp.controller.impl.GoToCatalogPageCommand;
import com.tc.webapp.controller.impl.GoToHelloPageCommand;
import com.tc.webapp.controller.impl.GoToLoginationPageCommand;
import com.tc.webapp.controller.impl.GoToRegistrationPageCommand;
import com.tc.webapp.controller.impl.LoginationPageCommand;
import com.tc.webapp.controller.impl.RegistrationPageCommand;


public final class CommandProvider {
	private final Map<String,Command> commands = new HashMap<String,Command>();

	public CommandProvider() {
		commands.put("goToHelloPage", new GoToHelloPageCommand());
		commands.put("accountPage", new AccountPageCommand());
		commands.put("bookPage", new BookPageCommand());
		commands.put("catalogPage", new CatalogPageCommand());
		commands.put("goToAccountPage", new GoToAccountPageCommand());
		commands.put("goToBookPage", new GoToBookPageCommand());
		commands.put("goToCatalogPage", new GoToCatalogPageCommand());
		commands.put("goToLoginationPage", new GoToLoginationPageCommand());
		commands.put("goToRegistrationPage", new GoToRegistrationPageCommand());
		commands.put("loginationPage", new LoginationPageCommand());
		commands.put("registrationPage", new RegistrationPageCommand());
	}
	
	public final Command getCommand(String commandName) {
		return commands.get(commandName);
	}
}
