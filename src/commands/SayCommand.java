package commands;

import bot.Connection;
import events.PRIVMSGEvent;

public class SayCommand implements Command {

	
	public SayCommand() {
		
	}

	@Override
	public boolean isValid(String command) {
		return command.toLowerCase().startsWith("say");
	}

	@Override
	public void execute(Connection connection, PRIVMSGEvent event) {
		String[] params = event.getParameters().split(" ", 2);
		if (params.length > 1) {
			connection.write("PRIVMSG " + event.getTarget() + " :" + params[1]);
		} else {
			connection.write("PRIVMSG " + event.getTarget() + " :" + event.getNick() + ": Invalid syntax.");
		}
	}
	
}