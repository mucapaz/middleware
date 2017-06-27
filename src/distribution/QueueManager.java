package distribution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import distribution.message.Header;
import distribution.message.Message;
import distribution.message.Operation;
import infrastructure.ServerRequestHandler;

public class QueueManager {

	public static int port = 13505;
	
	private List<String> topics;
	private HashMap<String, List<Integer>> topicSubscribersMap;
	
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
		
		topics = new ArrayList<String>();		
		
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
		
		if(operation.equals(Operation.PUBLISH)){
			queue.enqueue(msg);
			
		}else if(operation.equals(Operation.SUBSCRIBE)){
			subscribe(connectionId, msg.getHeader().getTopic());
		}
		
	}
	
	private void enqueue(Message msg){
		queue.enqueue(msg);
	}
	
	private void subscribe(int subscriber, String topic) {
		
		if(!topicSubscribersMap.containsKey(topic))
			topicSubscribersMap.put(topic, new ArrayList<Integer>());
			
		topicSubscribersMap.get(topic).add(subscriber);		
	}

	protected synchronized void publish(Message msg) {
	
		String topic = msg.getHeader().getTopic();
		
		List<Integer> subs = topicSubscribersMap.get(topic);
		
		/*
		 * Delete subscribers that we lost connection
		 */		
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
