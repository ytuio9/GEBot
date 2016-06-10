package events;

import bot.Message;

public class WelcomeEvent extends IRCEvent {

	public WelcomeEvent(Message message) {
		super(message);
	}

	public String getIdentifier() {
		return "001";
	}
}
