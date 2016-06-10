

import java.util.List;

public class Channel {

	private String name;
	private String modes;
	private int userCount;
	
	List<ChannelUser> users;
	
	public Channel(String name) {
		this.name = name;
	}
	
	public void addModes(String modes) {
		this.modes += modes;
	}
	
	public void addUser(String nick, String prefix) {
		users.add(new ChannelUser(nick, prefix));
	}
	
	public String getName() {
		return name;
	}
	
	public String getModes() {
		return modes;
	}
	
	public int getUserCount() {
		return userCount;
	}
	
}
