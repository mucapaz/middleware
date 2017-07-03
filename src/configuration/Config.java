package configuration;

public class Config {
	public static boolean persist = true;
	public static int queueManagerPort = 13505;
	public static String queueManagerAddress = "localhost";
	public static long delayToSendMessagesAfterRestart = 2 * 1000;
	public static long delayToRetryConnectionWithServer = delayToSendMessagesAfterRestart/2;
	public static boolean persistanceTest = true; //essa bool pode voar
}
