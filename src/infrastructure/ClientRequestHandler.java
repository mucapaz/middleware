package infrastructure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import configuration.Config;
import distribution.message.Message;

public class ClientRequestHandler {

	private String host;
	private int port;
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	public ClientRequestHandler(String host, int port) throws UnknownHostException, IOException{
		this.host = host;
		this.port = port;
		startConnection();
	}

	public void startConnection() throws UnknownHostException, IOException {
		socket = new Socket(this.host, this.port);
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
	}

	public synchronized void send(Message message){
		try {
			output.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			serverDisconnectionTreatment();
			send(message);
		}
	}

	public Message receive(){
		Message msg = null;
		try {
			msg = (Message) input.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( IOException e){
			serverDisconnectionTreatment();
			return receive(); // tá bonito
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

	public void serverDisconnectionTreatment(){
		boolean connectionMade = false;
		
		while(!connectionMade){
			try {
				System.out.println("Trying to reconnect to server");
				startConnection();
				connectionMade = true;
				System.out.println("Reconnection succeded");
			} catch (IOException e) {
				
				try {
					Thread.sleep(Config.delayToRetryConnectionWithServer);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		}
	}
}
