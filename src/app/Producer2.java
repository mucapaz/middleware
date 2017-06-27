package app;

import java.io.IOException;
import java.net.UnknownHostException;

import distribution.QueueManagerProxy;
import distribution.message.Operation;

public class Producer2 {
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		QueueManagerProxy proxy1 = new QueueManagerProxy("queue1");
		
		int n = 0;
		
		while(true){
			n++;
			String content = "Mensagem " + n;
			proxy1.send("topic1", content, Operation.PUBLISH);
		}
			
		
	}

}
