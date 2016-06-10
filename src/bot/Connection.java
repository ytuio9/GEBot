package bot;

import java.io.*;
import java.net.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import events.*;

public class Connection implements Runnable {

	
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;

	private String hostName;
	private int port;

	private boolean running = true;
	private boolean initialised = false;
	
	private ArrayBlockingQueue<IRCEvent> eventQueue;

	public Connection(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket(hostName, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			eventQueue = new ArrayBlockingQueue<IRCEvent>(10);
			initialised = true;
			while (running) { // put events in queue
				synchronized (this) {
					Message m = new Message(in.readLine());
					System.out.println(">> " + m.toString());
					if (m.getCommand().equals("PING")) {
						write("PONG :" + m.getParameters()); 
						eventQueue.add(new PINGEvent(m));
					}
					else if (m.getCommand().equals("001")) {
						eventQueue.add(new WelcomeEvent(m));
					} else if (m.getCommand().equals("PRIVMSG")) {
						eventQueue.add(new PRIVMSGEvent(m));
					} else {
						eventQueue.add(new IRCEvent(m));
					}
					
				}
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (UnknownHostException e) {
			System.out.println("Error: unknown host.");
			running = false;
		} catch (IOException e) {
			System.out.println("Error: IOException.");
			running = false;
		}
	}

	public synchronized void write(String message) {
		try {
			System.out.println("<< " + message);
			out.write(message + "\r\n");
			out.flush();
		} catch (IOException e) {
			System.out.println("Error while writing message.");
			close();
			running = false;
		}
	}

	public synchronized String read() {
		try {
			return in.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	public synchronized void close() {
		try {
			running = false;
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
		}
	}

	public synchronized boolean isRunning() {
		return running;
	}
	
	public synchronized BlockingQueue<IRCEvent> getQueue() {
		return eventQueue;
	}
	
	public boolean isInitialised() {
		return initialised;
	}
}
