package infrastructure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import distribution.QueueManager;
import distribution.message.Message;

public class ConnectionThread implements Runnable{

	private Socket socket;
	private int connectionId;
	private ObjectInputStream input;
	private QueueManager queueManager;

	public ConnectionThread(int connectionId ,Socket socket, QueueManager queueManager) throws IOException {
		this.socket = socket;
		this.connectionId = connectionId;
		this.input = new ObjectInputStream(socket.getInputStream());
		this.queueManager = queueManager;
	}

	@Override
	public void run() {
		try {
			while(true){
				Message msg = (Message) input.readObject();
				queueManager.message(connectionId, msg);
			}				
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
