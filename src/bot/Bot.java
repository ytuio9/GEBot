package bot;

import java.util.ArrayList;

import commands.*;
import events.*;

public class Bot {

	private boolean running = true;

	private String hostName;
	private int port;
	private String nick;

	private boolean connected = false;

	private Connection connection;
	private ArrayList<Command> commands;

	private String[] autojoin = { "#bottest" };

	private long lastRegisterAttempt;

	public Bot(String hostName, int port, String nick) throws InterruptedException {
		this.hostName = hostName;
		this.port = port;
		this.nick = nick;
		connection = new Connection(hostName, port);
		Thread t = new Thread(connection);
		t.start();
		initialise();

		while (running) {
			if (!connection.isRunning()) {
				running = false;
				break;
			}

			if (connection.isInitialised()) {
				for (IRCEvent e : connection.getQueue()) {
					if (!connected && System.currentTimeMillis() - lastRegisterAttempt > 3000) {
						lastRegisterAttempt = System.currentTimeMillis();
						connection.write("NICK :GEBot");
						connection.write("USER GEBot 8 * :OSRS Bot");
					}
					if (e instanceof PRIVMSGEvent) {
						for (Command command : commands) {
							if (command.isValid(((PRIVMSGEvent) e).getParameters().substring(1))) {
								command.execute(connection, (PRIVMSGEvent) e);
							}
						}
					} else if (e instanceof NOTICEEvent) {
					} else if (e instanceof WelcomeEvent) {
						connected = true;
						for (String s : autojoin)
							connection.write("JOIN :" + s);
					} else {

					}

					connection.getQueue().take();
				}
			}
		}
		Thread.sleep(1000);

		quit();
	}

	public void write(String output) {
		connection.write(output);
	}

	public void join(String channel) {
		write("JOIN :" + channel);
	}

	public void quit() {
		running = false;
		connection.write("QUIT :I quit.");
		connection.close();
	}

	private void initialise() {
		commands = new ArrayList<Command>();
		commands.add(new SayCommand());
		commands.add(new LookupCommand());
	}

	public boolean isRunning() {
		return running;
	}
}
