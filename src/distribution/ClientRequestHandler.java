package distribution;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientRequestHandler {

//	private String host;
//	private int port;
//	private boolean expectedReply;
	
	private Socket socket;
	private DataOutputStream output;
	private DataInputStream input;
	
	public ClientRequestHandler(String host, int port) throws UnknownHostException, IOException{
		socket = new Socket(host, port);
		
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
	}
	
	public void send(byte[] msg) throws IOException{
		output.write(msg,0,msg.length);	
		output.flush();
	}
	
	public byte[] receive(){
		
		
		
		return null;	
	}
	
	
}
