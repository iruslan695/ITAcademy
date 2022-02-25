package com.tc.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import com.tc.webapp.controller.impl.admin.ChangeUserStatusPageCommand;
import com.tc.webapp.controller.impl.admin.GoToAddPublisherInfoPageCommand;
import com.tc.webapp.controller.impl.admin.GoToChangeUserStatusPageCommand;
import com.tc.webapp.controller.impl.general.*;
import com.tc.webapp.controller.impl.publisher.DownloadJournalFilesPageCommand;
import com.tc.webapp.controller.impl.publisher.GoToDownloadJournalFilesPageCommand;


public final class CommandProvider {
    private final Map<String, Command> commands = new HashMap<String, Command>();

    public CommandProvider() {
        commands.put(FrontCommand.GO_TO_HELLO_PAGE, new GoToHelloPageCommand());
        commands.put(FrontCommand.ACCOUNT_PAGE, new AccountPageCommand());
        commands.put(FrontCommand.BOOK_PAGE, new BookPageCommand());
        commands.put(FrontCommand.CATALOG_PAGE, new CatalogPageCommand());
        commands.put(FrontCommand.GO_TO_ACCOUNT_PAGE, new GoToAccountPageCommand());
        commands.put(FrontCommand.GO_TO_BOOK_PAGE, new GoToBookPageCommand());
        commands.put(FrontCommand.GO_TO_CATALOG_PAGE, new GoToCatalogPageCommand());
        commands.put(FrontCommand.GO_TO_LOGINATION_PAGE, new GoToLoginationPageCommand());
        commands.put(FrontCommand.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
        commands.put(FrontCommand.LOGINATION_PAGE, new LoginationPageCommand());
        commands.put(FrontCommand.REGISTRATION_PAGE, new RegistrationPageCommand());
        commands.put(FrontCommand.GO_TO_ERROR_PAGE, new GoToErrorPageCommand());
        commands.put(FrontCommand.GO_TO_BECOME_PUBLISHER_PAGE, new GoToBecomePublisherPageCommand());
        commands.put(FrontCommand.GO_TO_TOP_UP_BALANCE_PAGE, new GoToErrorPageCommand());
        commands.put(FrontCommand.GO_TO_CHANGE_USER_STATUS_PAGE, new GoToChangeUserStatusPageCommand());
        commands.put(FrontCommand.CHANGE_USER_STATUS_PAGE, new ChangeUserStatusPageCommand());
        commands.put(FrontCommand.GO_TO_DOWNLOAD_JOURNAL_FILES, new GoToDownloadJournalFilesPageCommand());
        commands.put(FrontCommand.GO_TO_ADD_PUBLISHER_INFO, new GoToAddPublisherInfoPageCommand());
        commands.put(FrontCommand.DOWNLOAD_JOURNAL_FILES, new DownloadJournalFilesPageCommand());
        commands.put(FrontCommand.JOURNAL_PAGE, new JournalPageCommand());
        commands.put(FrontCommand.GO_TO_JOURNAL_PAGE, new GoToJournalPageCommand());
        commands.put(FrontCommand.GO_TO_SUBSCRIBE_PAGE, new GoToSubscribePageCommand());
        commands.put(FrontCommand.SUBSCRIBE_PAGE, new SubscribePageCommand());
    }

    public Command getCommand(String commandName) {
        if (commandName == null) {
            return commands.get(FrontCommand.NO_COMMAND_PAGE);
        }
        return commands.get(commandName);
    }
}
