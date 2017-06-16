package distribution.message;

import java.io.Serializable;

public class Header implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String topic;
	private Operation operation;
	
	public Header(String topic, Operation operation) {
		this.topic = topic;
		this.operation = operation;
	}
	
	@Override
	public String toString() {
		return "Header [topic=" + topic + "]";
	}

	public Operation getOperation() {
		return operation;
	}
	
	public String getTopic() {
		return topic;
	}
}
