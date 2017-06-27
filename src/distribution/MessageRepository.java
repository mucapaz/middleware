package distribution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Queue;

import distribution.message.Message;

public class MessageRepository {
	File file;
	Queue<Message> queue;
	
	public MessageRepository(){
		file = new File("data/queue.dat");
		if (file.exists()){
	        readQueue();
	    } else {
	    	queue = new LinkedList<Message>();
	    	try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
	            os.writeObject(queue);
	            os.close();
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	public void enqueue(Message message){
		queue.add(message);
		saveQueue();
	}
	
	public Message dequeue(){
		Message message;
		message = queue.remove();
		saveQueue();
		return message;
	}
	
	/** returns the queue read from the queue without deleting it*/
	public Queue<Message> getQueue(){
		readQueue();
		return queue;
	}
	
	/**reads queue from File*/
	@SuppressWarnings("unchecked")
	public void readQueue(){
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
				this.queue = (Queue<Message>) ois.readObject();
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**saves queue to File*/
	public void saveQueue(){
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
	        os.writeObject(queue);
	        os.close();
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
