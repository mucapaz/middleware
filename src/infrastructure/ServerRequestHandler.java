package infrastructure;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import distribution.QueueManager;
import distribution.message.Message;

public class ServerRequestHandler {

	private QueueManager queueManager;

	private int port;
	private ServerSocket socket;

	private Map<Integer, ObjectOutputStream> connectionMap;
	private int connectionCounter = 0;

	public ServerRequestHandler(int port, QueueManager queueManager) throws IOException{
		this.port = port;
		this.socket = new ServerSocket(port);
		this.queueManager = queueManager;

		this.connectionMap = new HashMap<Integer, ObjectOutputStream>();
	}

	public void connect(){

		try {
			Socket clientSocket = socket.accept();
			ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
			connectionCounter++;
			connectionMap.put(connectionCounter, output);

			ConnectionThread connection = new ConnectionThread(connectionCounter,
					clientSocket, queueManager);

			new Thread(connection).start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public synchronized void send(int connectionId, Message msg) throws IOException{
		ObjectOutputStream output = connectionMap.get(connectionId);
		
		try {
			output.writeObject(msg);
			output.flush();
					
			
		} catch (IOException e) {
			
			connectionMap.remove(connectionId);
			// TODO Auto-generated catch block
			e.printStackTrace();

			throw e;
		} catch (NullPointerException e){
			e.printStackTrace();
		}
	}

}