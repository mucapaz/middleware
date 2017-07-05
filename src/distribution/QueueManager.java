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
import repositories.TopicSubscriberMapRepository;

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
				System.exit(0);
			}		
		}

	}

	public QueueManager() throws IOException{	

		queue = new Queue();

		topicSubscribersMap = TopicSubscriberMapRepository.readFromDisk();

		//restoreQueueTopics();

		serverHandler = new ServerRequestHandler(port, this);

		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				System.out.println("Running shutdown Hook at QueueManager.java");
				TopicSubscriberMapRepository.saveToDisk(topicSubscribersMap);
			}
		});

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

		if(Config.persistanceTest){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		String topic = msg.getHeader().getTopic();

		List<Integer> subs = topicSubscribersMap.get(topic); //pega subs

		if(subs!=null){
			for(Iterator<Integer> i = subs.iterator(); i.hasNext();){
				int sub = i.next();
				try {
					serverHandler.send(sub, msg);
				} catch (IOException e) {

				}catch(NullPointerException npe){
					i.remove();
					System.out.println("Removed Offline Subscriber from Topic: " + topic);
				}
			}
		}
	}

	private synchronized void removeSubscribers(List<Integer> remove, List<Integer> subs){
		for(Integer at : remove){
			subs.remove(at);
		}
	}

	public void updateConnectionId(int oldId, int cookieId){
		serverHandler.updateConnectionId(oldId, cookieId);
	}

	public Message dequeue() {	
		return queue.dequeue();
	}

}
