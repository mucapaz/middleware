package infrastructure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import distribution.message.Message;

public class ClientRequestHandler {

//	private String host;
//	private int port;
//	private boolean expectedReply;
	
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public ClientRequestHandler(String host, int port) throws UnknownHostException, IOException{
		socket = new Socket(host, port);
		
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
	}
	
	public void send(Message message) throws IOException{
		
		
		
	}
	
	public byte[] receive(){
		
		
		
		return null;	
	}
	
	
}
