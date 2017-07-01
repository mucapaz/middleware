package distribution;

import java.io.IOException;
import java.net.UnknownHostException;

import configuration.Config;
import distribution.message.Message;
import distribution.message.MessageCreator;
import distribution.message.Operation;
import infrastructure.ClientRequestHandler;

public class QueueManagerProxy {
	
	private ClientRequestHandler crh;
	
	public QueueManagerProxy() throws UnknownHostException, IOException{
		crh = new ClientRequestHandler(Config.queueManagerAddress, QueueManager.port);
	}
	
	public void requestTopics(){
		Message message = MessageCreator.createMessage(Operation.LIST);
		crh.send(message);

	}
	
	public void subscribe(String topic){
		Message message = MessageCreator.createMessage(topic, Operation.SUBSCRIBE);
		crh.send(message);

	}
	
	public void send(String topic, Object content, Operation operation){
		Message message = MessageCreator.createMessage(topic, content, operation);
		crh.send(message);

				
	}

	public Message receive(){
		return crh.receive();
	}
	
}
