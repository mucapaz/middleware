package distribution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import distribution.message.Header;
import distribution.message.Message;
import distribution.message.Operation;
import distribution.message.Payload;
import infrastructure.ServerRequestHandler;

public class QueueManager {

	private List<String> topics;
	private HashMap<String, List<Integer>> topicConnectionsMap;
	
	private ServerRequestHandler serverHandler;
	
	public void main(String[] args){
		
		int port = 19999;
	}
	
	public QueueManager(int port) throws IOException{
		topics = new ArrayList<String>();		
		topicConnectionsMap = new HashMap<String, List<Integer>>();		
		
		serverHandler = new ServerRequestHandler(port, this);
	}
	
	public synchronized void putMessage(int connectionId, Message msg) {
		
		Header header = msg.getHeader();
//		Payload payload = msg.getPayload();
		Operation operation = header.getOperation();
		
		if(operation.equals(Operation.PUBLISH)){
			publish(msg);
			
		}else if(operation.equals(Operation.SUBSCRIBE)){
			subscribe(connectionId, msg.getHeader().getTopic());
		}
		
		System.out.println(connectionId + " " + msg);
	}
	
	private void subscribe(int connectionId, String topic) {
		
		
	}

	private void publish(Message msg) {
	
		String topic = msg.getHeader().getTopic();
		
		List<Integer> connections = topicConnectionsMap.get(topic);
		
		if(connections!=null){
			for(Integer at : connections){
				serverHandler.send(at, msg);
				
			}
		}
		
	}
	
}
