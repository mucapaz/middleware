package distribution.message;

import java.io.Serializable;

public class Header implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String destination;
	private Operation operation;

	public Header() {
		
	}
	
	public Header(String destination, Operation op) {
		this.setDestination(destination);
		this.setOperation(op);
	}
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	@Override
	public String toString() {
		return "Header [destination=" + destination + "]";
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
}
