package infrastructure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import distribution.message.Message;

public class ClientRequestHandler {


	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public ClientRequestHandler(String host, int port) throws UnknownHostException, IOException{
		socket = new Socket(host, port);
		
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
	}
	
	public void send(Message message) throws IOException{
		output.writeObject(message);	
	}
	
	public Message receive(){
		Message msg = null;
		
		try {
			msg = (Message) input.readObject();
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
					
			e.printStackTrace();
		}
		return msg;	
	}
	
	public void close(){
		try {
			output.flush();
			output.close();
			
			input.close();
			
			socket.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
