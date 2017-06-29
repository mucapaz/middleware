package distribution.message;

import java.io.Serializable;

public class Header implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String topic;
	private Operation operation;
	private Class dataClass;
	
	public Header(String topic, Operation operation, Class<? extends Object> class1) {
		this.topic = topic;
		this.operation = operation;
		this.dataClass = class1;
	}
	
	public Class getDataClass() {
		return dataClass;
	}

	public void setDataClass(Class dataClass) {
		this.dataClass = dataClass;
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
