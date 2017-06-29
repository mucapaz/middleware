package distribution.message;

public class MessageCreator {

	public static Message createMessage(String topic, Operation operation) {
		Header header = new Header(topic, operation, null);
		return createMessage(header,null,operation);
	}
	
	public static Message createMessage(Operation operation) {
		Header header = new Header(null, operation, null);
		return createMessage(header,null,operation);
	}

	public static Message createMessage(String topic, Object content, Operation operation) {
		Header header = new Header(topic, operation, content.getClass());
		return createMessage(header,content,operation);
	}

	public static Message createMessage(Header header, Object content, Operation operation) {
		Payload payload = new Payload(content);
		Message message = new Message(header, payload);
		return message;
	}

}
