package app;

import java.io.IOException;
import java.net.UnknownHostException;

import distribution.QueueManagerProxy;
import distribution.message.Operation;

public class Consumer2 {

	public static void main(String[] args) throws UnknownHostException, IOException {

		QueueManagerProxy proxy1 = new QueueManagerProxy("queue1");
		
		while (true) {
			proxy1.send("topic1", "", Operation.SUBSCRIBE);
			System.out.println(proxy1.receive());
		}
	}

}
