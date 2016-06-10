package events;

import bot.Message;

public class IRCEvent {
	protected Message message;
	
	public IRCEvent(Message message) {
		this.message = message;
	}
	
	public String getIdentifier() {
		return message.getCommand();
	}
	
	public Message getMessage() {
		return message;
	}

}