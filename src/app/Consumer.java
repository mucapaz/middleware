package app;

import java.io.IOException;
import java.net.UnknownHostException;

import distribution.QueueManagerProxy;

public class Consumer {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		QueueManagerProxy queue1proxy = new QueueManagerProxy("topic1");
		
		while (true) {
			System.out.println(queue1proxy.receive());
		}
		
	}

}
