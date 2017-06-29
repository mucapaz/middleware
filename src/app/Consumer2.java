package app;

import java.io.IOException;
import java.net.UnknownHostException;

import distribution.QueueManagerProxy;
import distribution.message.Operation;

public class Consumer2 {

	public static void main(String[] args) throws UnknownHostException, IOException {

		QueueManagerProxy proxy1 = new QueueManagerProxy();
		
		proxy1.send("topic1", "", Operation.SUBSCRIBE);
		
		while (true) {
			
			System.out.println(proxy1.receive());
		}
	}

}
