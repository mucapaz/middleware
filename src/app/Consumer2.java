package app;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Set;

import distribution.QueueManagerProxy;
import distribution.message.Message;
import distribution.message.Operation;

public class Consumer2 implements Runnable{

	private QueueManagerProxy proxy;
	private String name;
	
	public Consumer2(String name, QueueManagerProxy proxy) {
		this.proxy = proxy;
		this.name = name;
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		QueueManagerProxy proxy1 = new QueueManagerProxy();	
		QueueManagerProxy proxy2 = new QueueManagerProxy();
	
		proxy1.send("topic1", "", Operation.SUBSCRIBE);
		
		proxy1.send("topic2", "", Operation.SUBSCRIBE);
		
		proxy2.send("topic11", "", Operation.SUBSCRIBE);
		proxy2.send("topic22", "", Operation.SUBSCRIBE);

		proxy1.send("topic3", "", Operation.SUBSCRIBE);
		
		proxy1.send("topic1", "", Operation.SUBSCRIBE);
		
		Consumer2 c1 = new Consumer2("Consumer 1", proxy1);
		
		Consumer2 c2 = new Consumer2("Consumer 2", proxy2);
		
		
		Thread t1 = new Thread(c1);
		Thread t2 = new Thread(c2);
		
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
	
	}

	@Override
	public void run() {
		while (true) {
			Message msg = proxy.receive();
			
			System.out.println(name + " -> "  + msg.getPayload().getContent());				
		}
	}
}
