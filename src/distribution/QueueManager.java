package distribution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import configuration.Config;
import distribution.message.Header;
import distribution.message.Message;
import distribution.message.MessageCreator;
import distribution.message.Operation;
import infrastructure.ServerRequestHandler;

public class QueueManager {

	public static int port = Config.queueManagerPort;
	
	private HashMap<String, List<Integer>> topicSubscribersMap;
	
	int count = 0;
	
	private Queue queue;
	
	private ServerRequestHandler serverHandler;
	
	
	public static void main(String[] args) throws IOException{
		
		/* 
		 * Read messages from data? 
		 */
		
		QueueManager queue = new QueueManager();
		queue.run();
		
	}
	
	public QueueManager() throws IOException{	
		queue = new Queue();
		
		topicSubscribersMap = new HashMap<String, List<Integer>>();		
		
		serverHandler = new ServerRequestHandler(port, this);
		
//		queue = new ConcurrentLinkedQueue<>();
		
	}

	public void run(){
	
		MessagePassThread messagePassThread = new MessagePassThread(this);
		Thread thread = new Thread(messagePassThread);
		thread.start();
		
		
		while(true){
			serverHandler.connect();
			
			System.out.println("QueueManager -> serverHandler.connect()");	
		}
		
//		thread.join();
	}
	
	public synchronized void message(int connectionId, Message msg) {
		
		Header header = msg.getHeader();
		Operation operation = header.getOperation();
		
		
		System.out.println(operation);
		
		if(operation.equals(Operation.PUBLISH)){
			queue.enqueue(msg);
			
			String topic = msg.getHeader().getTopic();
			
			if(!topicSubscribersMap.containsKey(topic)){
				topicSubscribersMap.put(topic, new ArrayList<Integer>());
			}
			
		}else if(operation.equals(Operation.SUBSCRIBE)){
			subscribe(connectionId, msg.getHeader().getTopic());	
			
		}else if (operation.equals(Operation.LIST)){
			try {
				Message message  = MessageCreator.createMessage("",  topicSubscribersMap.keySet().toArray() ,Operation.LIST); 
							
				serverHandler.send(connectionId, message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	private void enqueue(Message msg){
//		queue.enqueue(msg);
//	}
	
	private void subscribe(int subscriber, String topic) {
		
		if(!topicSubscribersMap.containsKey(topic))
			topicSubscribersMap.put(topic, new ArrayList<Integer>());
			
		topicSubscribersMap.get(topic).add(subscriber);		
	}

	protected void publish(Message msg) {
	
		/*
		 * Teste de persistência
		 */
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		String topic = msg.getHeader().getTopic();
		
		List<Integer> subs = topicSubscribersMap.get(topic);
		
			
		List<Integer> remove = new ArrayList<Integer>();
		
		if(subs!=null){
			for(Integer at : subs){
				try {
					serverHandler.send(at, msg);
					
					remove.add(at);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
		
//		removeSubscribers(remove, subs);
	}
	
	private synchronized void removeSubscribers(List<Integer> remove, List<Integer> subs){
		for(Integer at : remove){
			subs.remove(at);
		}
	}

	public Message dequeue() {	
		return queue.dequeue();
	}
	
}
