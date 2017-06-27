package distribution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import distribution.message.Header;
import distribution.message.Message;
import distribution.message.Operation;
import distribution.message.Payload;
import infrastructure.ServerRequestHandler;

public class QueueManager {

	public static int port = 13505;
	
	private List<String> topics;
	private HashMap<String, List<Integer>> topicSubscribersMap;
	
	private ConcurrentLinkedQueue queue;
	
	private ServerRequestHandler serverHandler;
	

	public static void main(String[] args) throws IOException{
		
		/*
		 * Read messages from data? 
		 */

		
		QueueManager queue = new QueueManager();
		queue.run();
		
	}
	
	public QueueManager() throws IOException{
		
		topics = new ArrayList<String>();		
		
		topicSubscribersMap = new HashMap<String, List<Integer>>();		
		
		serverHandler = new ServerRequestHandler(port, this);
		
		queue = new ConcurrentLinkedQueue<>();
		
		
		
	}
	
	public void run(){
	
		MessagePassThread messagePassThread = new MessagePassThread(this);
		Thread thread = new Thread(messagePassThread);
		thread.start();
		
		
		while(true){
			
			
			serverHandler.connect();
			
			System
			
		}
		
//		thread.join();
	}
	
	public QueueManager(int port, String queueLocation) throws IOException{
	
		
		/*
		 * READ QUEUE FROM DATA
		 */
	}
	
	
	public synchronized void message(int connectionId, Message msg) {
		
		Header header = msg.getHeader();
		Operation operation = header.getOperation();
		
		if(operation.equals(Operation.PUBLISH)){
			enqueue(msg);
			
		}else if(operation.equals(Operation.SUBSCRIBE)){
			subscribe(connectionId, msg.getHeader().getTopic());
		}
		
	}
	
	private void enqueue(Message msg){
		queue.add(msg);
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
		
		removeSubscribers(remove, subs);
	}
	
	private synchronized void removeSubscribers(List<Integer> remove, List<Integer> subs){
		for(Integer at : remove){
			subs.remove(at);
		}
	}

	public ConcurrentLinkedQueue getQueue() {
		return queue;
	}
	
	
}
