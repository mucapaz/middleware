package distribution;

import distribution.message.Message;

public class QueueManager {

	public synchronized void putMessage(int connectionId, Message msg) {
		
		System.out.println(connectionId + " " + msg);
		
	}
}
