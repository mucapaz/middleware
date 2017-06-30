package distribution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import configuration.Config;
import distribution.message.Message;

public class Queue {
	File file;
	
	ConcurrentLinkedQueue<Message> queue;
	
	boolean persist = Config.persist; 
	
	public static AtomicBoolean stop = new AtomicBoolean(false);
	
	public Queue(){
		if(persist){
			file = new File("data/queue");
			if (file.exists()){
		        readQueue();
		        System.out.println("Init queue. size = " +  queue.size());
		        
		    } else {
		    	queue = new ConcurrentLinkedQueue<Message>();
		    	try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
		            os.writeObject(queue);
		            os.close();
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
	}
	
	public void  enqueue(Message message){
		queue.add(message);
		if(persist) 
			saveQueue();
	}
	
	public Message dequeue(){
		Message message;
		message = queue.poll();
		if(persist && message != null) //se a queue não estiver vazia, atualiza no disco
				saveQueue();
		return message;
	}
		
	/** returns the queue read from the queue without deleting it*/
	public ConcurrentLinkedQueue<Message> getQueue(){
		//readQueue();
		return queue;
	}
	
	/**reads queue from File*/
	@SuppressWarnings("unchecked")
	public synchronized void readQueue(){
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
				this.queue = (ConcurrentLinkedQueue<Message>) ois.readObject();
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
	}
	

	public String printQueue(){
		readQueue();
		Iterator<Message> it = queue.iterator();
		String result = "";
		while(it.hasNext()){
			result += "\n" + ((Message)it.next()).toString();
		}
		return result;
	}
	
	/**saves queue to File*/
	public synchronized void saveQueue(){
		
		if(!stop.get()){
				
			ObjectOutputStream os = null;
			try {
				
				os = new ObjectOutputStream(new FileOutputStream(file));
		        os.writeObject(queue);
		       
		        os.flush();
		        os.close();
		        
		    } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	}

	public boolean isEmpty() {
		return queue.size() == 0;
	}

	public void stop() {
		stop.set(true);
	}

}
