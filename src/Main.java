import java.io.*;
import java.net.*;

import bot.Bot;
import bot.Message;

public class Main {
	
	public static void main(String[] args)
	{
		try {
			Bot bot = new Bot("irc.rizon.net", 6667, "GEBot");
		} catch (InterruptedException e) {
		}

	}
	
}
