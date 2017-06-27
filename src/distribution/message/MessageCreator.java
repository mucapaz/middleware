package distribution.message;

import java.util.List;

public class MessageCreator {

//	public MessageCreator() {
//		this.setMessage(new Message());
//	}
//	
//	public MessageCreator(String topic, Operation op, Object content) {
//		Header header = new Header(topic, op);
//		Payload payload = new Payload(content);
//		this.setMessage(new Message(header, payload));
//	}

	public static Message createMessage(String topic, Object content, Operation operation) {
		
		Header header = new Header(topic, operation);
		Payload payload = new Payload(content);
		
		Message message = new Message(header, payload);
		
		return message;
	}

}
