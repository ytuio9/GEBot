

public class ChannelUser {

	private String prefix;
	private String nick;
	
	public ChannelUser(String prefix, String nick) {
		this.prefix = prefix;
		this.nick = nick;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public String getNick() {
		return this.nick;
	}
	
	public boolean equals(String nick) {
		return this.nick.equals(nick);
	}
}
