package commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.JSONObject;

import bot.Connection;
import events.PRIVMSGEvent;

public class LookupCommand implements Command {

	private boolean initialised;
	private final String JSON_STRING;
	
	public LookupCommand() {
		Path p = Paths.get(System.getProperty("user.home"), "GEBot data", "item_info.txt");
		String str = "";
		try {
			Scanner scanner = new Scanner(Files.newInputStream(p));
			while(scanner.hasNext())
				str += scanner.nextLine();
			scanner.close();			
			initialised = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSON_STRING = str;
	}

	@Override
	public boolean isValid(String command) {
		return command.toLowerCase().startsWith("gelookup");
	}

	@Override
	public void execute(Connection connection, PRIVMSGEvent event) {
		String[] params = event.getParameters().split(" ", 2);
		if (params.length < 2) {
			connection.write("PRIVMSG " + event.getTarget() + " :Invalid syntax. Proper syntax: !gelookup <id | name>");
		} else {
			JSONObject obj = new JSONObject(JSON_STRING);
			boolean found = false;
			int id = 0;
			for (String s : JSONObject.getNames(obj)) {
				JSONObject obj2 = obj.getJSONObject(s);
				if (obj2.getString("name").equalsIgnoreCase(params[1])) {
					id = Integer.parseInt(s);
					found = true;
					break;
				}
			}
			if (!found) {
				connection.write("PRIVMSG " + event.getTarget() + " :" + params[1] + " not found.");
			} else {
				connection.write("PRIVMSG " + event.getTarget() + " :Found item: " + id);
			}
		}
	}
	
	public boolean isInitialised() {
		return initialised;
	}

}
