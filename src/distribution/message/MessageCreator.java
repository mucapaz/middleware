package distribution.message;

import java.util.List;

public class MessageCreator {
	
	private Message message;

	public MessageCreator() {
		this.setMessage(new Message());
	}
	
	public MessageCreator(String destination, Operation op, List<Object> content) {
		Header header = new Header(destination, op);
		Payload payload = new Payload(content);
		this.setMessage(new Message(header, payload));
	}
	
	public void setDestination(String destination) {
		Header header = getMessage().getHeader();
		header.setDestination(destination);
	}
	
	public void addContent(Object object) {
		Payload payload = getMessage().getPayload();
		payload.add(object);
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
}
