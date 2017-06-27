package app;

import java.io.IOException;
import java.net.UnknownHostException;

import distribution.QueueManagerProxy;
import distribution.message.Operation;

public class Consumer {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		QueueManagerProxy proxy1 = new QueueManagerProxy("queue1");
		QueueManagerProxy proxy2 = new QueueManagerProxy("queue2");
		
		proxy1.send("topic1", "", Operation.SUBSCRIBE);
		proxy1.send("topic2", "", Operation.SUBSCRIBE);
		
		proxy2.send("topic11", "", Operation.SUBSCRIBE);
		proxy2.send("topic22", "", Operation.SUBSCRIBE);
		
		proxy1.send("topic3", "", Operation.SUBSCRIBE);
		
		while (true) {
			//Fila 1
			System.out.println(proxy1.receive());
			
			//Fila 2
			System.out.println(proxy2.receive());
		}
		
	}

}
