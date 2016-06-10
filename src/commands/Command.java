package commands;

import bot.Connection;
import events.*;

public interface Command {
	
	public boolean isValid(String command);
	public void execute(Connection connection, PRIVMSGEvent event);
	
}
