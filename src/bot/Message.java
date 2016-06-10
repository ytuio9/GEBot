package bot;

public class Message {

	private String raw;
	private String prefix;
	private String command;
	private String parameters;
	private String[] splitParameters;
	
	public Message(String input) {
		raw = input;
		splitParameters = input.split(" ");
		if (splitParameters.length > 2) {
			prefix = splitParameters[0].substring(1);
			command = splitParameters[1];
			parameters = input.split(" ", 3)[2];
		} else {
			command = splitParameters[0];
			parameters = splitParameters[1].substring(1);
		}
	}
	
	public String getPrefix() {
		return prefix;
	}

	public String getCommand() {
		return command;
	}

	public String getParameters() {
		return parameters;
	}
	
	public String toString() {
		return raw;
	}
	
	public String[] getSplitParameters() {
		return splitParameters;
	}
	
}
