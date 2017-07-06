package app;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Set;

import distribution.QueueManagerProxy;
import distribution.message.Message;
import distribution.message.Operation;

public class Consumer implements Runnable{
    private static final String TOPICS_NUMBER_MSG_FORMAT = "Topics number: %d";
    private static final String PAYLOAD_MSG_FORMAT = "%s -> Message: %s; Topics: %s";

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
				
				System.out.println(String.format(TOPICS_NUMBER_MSG_FORMAT, topicos.length));
				
				for(int x=0;x<objs.length;x++){
					
					topicos[x] = (String) objs[x];
				
					System.out.print(topicos[x] + " ");
					
				}
				System.out.println();
				
				
				for (String string : topicos) {
					proxy.subscribe(string);
				}
			}else{
				System.out.println(String.format(PAYLOAD_MSG_FORMAT, name, msg.getPayload().getContent(), msg.getHeader().getTopic()));
			}				
		}
	}
	
	
	
}
