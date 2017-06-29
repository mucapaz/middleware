package configuration;

public class Config {
	public static boolean persist = true;
	public static int queueManagerPort = 13505;
	public static String queueManagerAddress = "localhost";
	public static long delayAfterRestart = 15 * 1000;
	public static boolean persistanceTest = true;
}
