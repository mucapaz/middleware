package app;

import java.io.IOException;
import java.net.UnknownHostException;

import distribution.QueueManagerProxy;

public class Producer {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
		
		QueueManagerProxy proxy1 = new QueueManagerProxy("queue 1");
		
		while(true){
			Thread.sleep(500);
			
			proxy1.send("topic1", content, operation);
		}
		
	}
	

}
