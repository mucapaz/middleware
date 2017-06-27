package distribution.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Payload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object content;

	
	public Payload(Object content) {
		this.content = content;
	}

	public Object getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "Payload [content=" + content + "]";
	}
}
