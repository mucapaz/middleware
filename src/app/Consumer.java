package app;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Set;

import distribution.QueueManagerProxy;
import distribution.message.Message;
import distribution.message.Operation;

public class Consumer implements Runnable{

	private QueueManagerProxy proxy;
	private String name;
	
	
	public Consumer(String name, QueueManagerProxy proxy) {
		this.proxy = proxy;
		this.name = name;
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		QueueManagerProxy proxy1 = new QueueManagerProxy();		
	
		Consumer c1 = new Consumer("Consumer 1", proxy1);
		
		Thread t1 = new Thread(c1);
		
		t1.start();
		
		proxy1.requestTopics();
		
		t1.join();
	}

	@Override
	public void run() {
		while (true) {
			
			Message msg = proxy.receive();
			
			if(msg.getHeader().getOperation() == Operation.LIST){
					
				Object[] objs = (Object[]) msg.getPayload().getContent();
				
				String[] topicos = new String[objs.length];
				
				System.out.println("Número de tópicos: " + topicos.length);
				
				for(int x=0;x<objs.length;x++){
					
					topicos[x] = (String) objs[x];
				
					System.out.print(topicos[x] + " ");
					
				}
				System.out.println();
				
				
				for (String string : topicos) {
					proxy.subscribe(string);
				}
			}else{
				System.out.println(name + " -> "  + msg.getPayload().getContent());
			}				
		}
	}
	
	
	
}
