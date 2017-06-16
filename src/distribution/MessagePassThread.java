package distribution;

import java.util.concurrent.ConcurrentLinkedQueue;

import distribution.message.Message;

public class MessagePassThread implements Runnable{
	
	private QueueManager queueManager;
	
	public MessagePassThread(QueueManager queueManager){
		this.queueManager = queueManager;
	}

	@Override
	public void run() {
		
		ConcurrentLinkedQueue<Message> queue = queueManager.getQueue(); 
		
		while(true){
			Message msg = queue.poll();
			
			if(msg == null){
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				queueManager.publish(msg);
				
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}	
		}
	
	}
	
	
	
}
