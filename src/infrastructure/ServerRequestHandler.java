package infrastructure;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import distribution.QueueManager;

public class ServerRequestHandler {
	
	private QueueManager queueManager;
	
	private int port;
	private ServerSocket socket;

	private Map<Integer, Socket> connectionSocketMap;
	private int connectionSocketCounter = 0;
	
	public ServerRequestHandler(int port, QueueManager queueManager) throws IOException{
		this.port = port;
		this.socket = new ServerSocket(port);
		this.queueManager = queueManager;
		
		this.connectionSocketMap = new HashMap<Integer, Socket>();
	}
	
	public void connect(){
		
		try {
			Socket clientSocket = socket.accept();
			connectionSocketCounter++;
			connectionSocketMap.put(connectionSocketCounter, clientSocket);
			
			ConnectionThread connection = new ConnectionThread(connectionSocketCounter,
					clientSocket, queueManager);
			
			new Thread(connection).start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}