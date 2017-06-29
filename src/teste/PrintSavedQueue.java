package teste;

import distribution.Queue;
import distribution.message.*;

public class PrintSavedQueue {

	public static void main(String[] args) {
		Queue mr = new Queue();
		
//		mr.enqueue(dummyMsg("1"));
//		mr.enqueue(dummyMsg("2"));
//		mr.enqueue(dummyMsg("3"));
//		mr.enqueue(dummyMsg("4"));
//		mr.enqueue(dummyMsg("5"));
//		mr.enqueue(dummyMsg("6"));
		
		System.out.println(mr.printQueue());
	}
	
	static Message dummyMsg(String message){		
		Header header = new Header("nottopic",Operation.PUBLISH, message.getClass());
		Payload payload = new Payload(message);
		Message teste = new Message(header,payload);
		return teste;
	}
	
	
}
