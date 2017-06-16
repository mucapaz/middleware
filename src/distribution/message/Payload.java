package distribution.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Payload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Object> content;
	
	public Payload() {
		this.setContent(new ArrayList<Object>());
	}
	
	public Payload(List<Object> content) {
		this.setContent((ArrayList<Object>) content);
	}
	
	public void add(Object o) {
		getContent().add(o);
	}

	public List<Object> getContent() {
		return content;
	}

	public void setContent(List<Object> content2) {
		this.content = (ArrayList<Object>) content2;
	}

	@Override
	public String toString() {
		return "Payload [content=" + content + "]";
	}
}
