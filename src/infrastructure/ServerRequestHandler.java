package infrastructure;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import distribution.QueueManager;
import distribution.message.Message;
import repositories.ConnectionIdRepository;
import repositories.TopicSubscriberMapRepository;

public class ServerRequestHandler {

	private QueueManager queueManager;

	private int port;
	private ServerSocket socket;

	private Map<Integer, ObjectOutputStream> connectionMap;
	private int connectionCounter = ConnectionIdRepository.readFromDisk();

	public ServerRequestHandler(int port, QueueManager queueManager) throws IOException{
		this.port = port;
		this.socket = new ServerSocket(port);
		this.queueManager = queueManager;

		this.connectionMap = new HashMap<Integer, ObjectOutputStream>();
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				System.out.println("Running shutdown Hook at ServerRequestHandler.java");
				ConnectionIdRepository.saveToDisk(connectionCounter);
			}
		});
	}

	public void connect(){
		try {
			Socket clientSocket = socket.accept();
			ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
			connectionCounter++;
			
			System.out.println("Server Conecting");
			connectionMap.put(connectionCounter, output);
			output.writeObject(connectionCounter);
			System.out.println("Sending cookie: " + connectionCounter);
			
			ConnectionThread connection = new ConnectionThread(connectionCounter,
					clientSocket, queueManager);

			new Thread(connection).start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public synchronized void send(int connectionId, Message msg) throws IOException,NullPointerException{
		ObjectOutputStream output = connectionMap.get(connectionId);
		
		try {
			output.writeObject(msg);
			output.flush();
			
		} catch (IOException e) {
			
			connectionMap.remove(connectionId);
			// TODO Auto-generated catch block
			e.printStackTrace();

			throw e;
		}
	}
	
	public synchronized void updateConnectionId(int oldId, int cookieId){
		ObjectOutputStream output = connectionMap.get(oldId);
		connectionMap.remove(oldId);
		connectionMap.put(cookieId, output);
		System.out.println("Updating connectionId " + oldId + " to " + cookieId);
	}
}