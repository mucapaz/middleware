package app;

import java.io.IOException;
import java.net.UnknownHostException;

import distribution.QueueManagerProxy;
import distribution.message.Operation;

public class Consumer implements Runnable{

	private QueueManagerProxy proxy;
	private String name;
	
	
	public Consumer(String name, QueueManagerProxy proxy) {
		this.proxy = proxy;
		this.name = name;
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		QueueManagerProxy proxy1 = new QueueManagerProxy("queue1");
		QueueManagerProxy proxy2 = new QueueManagerProxy("queue2");
		
		proxy1.send("topic1", "", Operation.SUBSCRIBE);
		
		proxy1.send("topic2", "", Operation.SUBSCRIBE);
		
		proxy2.send("topic11", "", Operation.SUBSCRIBE);
		proxy2.send("topic22", "", Operation.SUBSCRIBE);

		proxy1.send("topic3", "", Operation.SUBSCRIBE);
		
		proxy1.send("topic1", "", Operation.SUBSCRIBE);
		
		Consumer c1 = new Consumer("Consumer 1", proxy1);
		
		Consumer c2 = new Consumer("Consumer 2", proxy1);
		
		
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
			
			System.out.println(name + " -> "  + proxy.receive());
		}
		
	}

}
