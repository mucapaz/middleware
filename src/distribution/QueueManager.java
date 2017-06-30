package distribution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import configuration.Config;
import distribution.message.Header;
import distribution.message.Message;
import distribution.message.MessageCreator;
import distribution.message.Operation;
import infrastructure.ServerRequestHandler;

public class QueueManager implements Runnable{

	public static int port = Config.queueManagerPort;
	
	private HashMap<String, List<Integer>> topicSubscribersMap;
	
	int count = 0;
	
	private Queue queue;
	
	private ServerRequestHandler serverHandler;
	
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		QueueManager queue = new QueueManager();
		Thread t = new Thread(queue);
		t.start();
		
		Scanner in = new Scanner(System.in);
		
		while(in.hasNextLine()){
			String str = in.nextLine();
			
			if(str.equals("exit")){
				queue.stop();
				Thread.sleep(5000);
				System.exit(0);
				
			}		
		}
	}
	
	

	private void stop() {
		queue.stop();
		
	}



	public QueueManager() throws IOException{	
		
		queue = new Queue();
		
		topicSubscribersMap = new HashMap<String, List<Integer>>();
		
		restoreQueueTopics();
		
		serverHandler = new ServerRequestHandler(port, this);	
		
	}
	
	public void restoreQueueTopics(){
		//Cadastra os tópicos presentes na fila do disco 
		Iterator<Message> it = queue.getQueue().iterator();
		while(it.hasNext()){
			topicSubscribersMap.put(it.next().getHeader().getTopic(), new ArrayList<Integer>());
		}
	}

	public void run(){
		MessagePassThread messagePassThread;
		
		messagePassThread = new MessagePassThread(this,!this.queue.isEmpty());
		
		Thread thread = new Thread(messagePassThread);
		thread.start();
		
		while(true){
			serverHandler.connect();
			
			System.out.println("QueueManager -> serverHandler.connect()");	
		}
	}
	
	public synchronized void message(int connectionId, Message msg) {
		
		Header header = msg.getHeader();
		Operation operation = header.getOperation();
			
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
				e.printStackTrace();
			}
		}
	}
	
	private void subscribe(int subscriber, String topic) {
		
		if(!topicSubscribersMap.containsKey(topic))
			topicSubscribersMap.put(topic, new ArrayList<Integer>());
			
		topicSubscribersMap.get(topic).add(subscriber);		
	}

	protected void publish(Message msg) {
		
		if(Config.persistanceTest)
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		String topic = msg.getHeader().getTopic();
		
		List<Integer> subs = topicSubscribersMap.get(topic);
		
			
		List<Integer> remove = new ArrayList<Integer>();
		
		if(subs!=null){
			for(Integer at : subs){
				try {
					serverHandler.send(at, msg);
					
					remove.add(at);
					
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
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
