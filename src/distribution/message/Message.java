package distribution.message;

import java.io.Serializable;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Header header;
	private Payload payload;
	
	public Message(Header header, Payload payload){
		this.header = header;
		this.payload = payload;
	}
	
	
	
}
