package distribution;

import java.io.IOException;
import java.net.UnknownHostException;

import distribution.message.Message;
import distribution.message.MessageCreator;
import distribution.message.Operation;
import infrastructure.ClientRequestHandler;

public class QueueManagerProxy {

	private String queueName;
	
	private ClientRequestHandler crh;
	
	public QueueManagerProxy(String queueName) throws UnknownHostException, IOException{
		this.queueName = queueName;
		

		crh = new ClientRequestHandler("127.0.0.1", 16999);
	}
	
	public synchronized void send(String topic, Object content, Operation operation){
		Message message = MessageCreator.createMessage(topic, content, operation);
		
		try {
			crh.send(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
		
	public Object receive(){
		return crh.receive().getPayload().getContent();
	}
	
}
