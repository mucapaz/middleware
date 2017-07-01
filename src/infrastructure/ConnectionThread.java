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
		this.input = new ObjectInputStream(this.socket.getInputStream());
		this.queueManager = queueManager;
	}

	@Override
	public void run() {
		firstMessageRoutine();
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
	
	/** updates the connectionId with the cookie provided or treats the message normally*/
	public void firstMessageRoutine(){
		try {
			Object obj = input.readObject();
			if(obj instanceof Message){
				Message msg = (Message) obj;
				queueManager.message(connectionId, msg);
			}else{
				int cookieId = (int) obj;
				updateConnectionId(this.connectionId,cookieId);
				this.connectionId = cookieId;
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateConnectionId(int oldId, int cookieId){
		queueManager.updateConnectionId(oldId, cookieId);
	}
}
