package events;

import bot.Message;

public class NOTICEEvent extends IRCEvent {

	public NOTICEEvent(Message message) {
		super(message);
	}

	public String getIdentifier() {
		return "NOTICE";
	}

}
