package events;

import bot.Message;

public class PRIVMSGEvent extends IRCEvent {

	private String nick;
	private String target;
	private String parameters;
	
	public PRIVMSGEvent(Message message) {
		super(message);
		nick = message.getPrefix().split("!")[0];
		target = message.getSplitParameters()[2];
		parameters = message.getParameters().split(" ", 2)[1].substring(1);
	}

	public String getIdentifier() {
		return "PRIVMSG";
	}
	
	public String getNick() {
		return nick;
	}
	
	public String getTarget() {
		return target;
	}
	
	public String getParameters() {
		return parameters;
	}

}
