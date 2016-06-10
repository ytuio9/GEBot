import bot.Bot;

public class Main {
	
	public static void main(String[] args)
	{
		try {
			Bot bot = new Bot("irc.rizon.net", 6667, "GEBot");
		} catch (InterruptedException e) {
		}
	}
	
}
