package distribution.message;

import java.io.Serializable;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Header header;
	private Payload payload;
	
	public Message() {
		this.setHeader(new Header());
		this.setPayload(new Payload());
	}

	public Message(Header header, Payload payload){
		this.setHeader(header);
		this.setPayload(payload);
	}

//	private void writeObject(ObjectOutputStream out) throws IOException {
//		out.defaultWriteObject();
//	}
//	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//		in.defaultReadObject();
//	}
//	private void readObjectNoData() throws ObjectStreamException {
//		
//	}
	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "Message [header=" + header + ", payload=" + payload + "]";
	}
	
}
