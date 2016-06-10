package events;

import bot.Message;

public class PINGEvent extends IRCEvent {

	public PINGEvent(Message message) {
		super(message);
	}

	@Override
	public String getIdentifier() {
		return "PING";
	}

}
