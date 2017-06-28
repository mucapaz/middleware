package distribution;

import distribution.message.Message;

public class MessagePassThread implements Runnable{
	
	private QueueManager queueManager;
	
	public MessagePassThread(QueueManager queueManager){
		this.queueManager = queueManager;
	}

	@Override
	public void run() {	
		
		while(true){
			Message msg = queueManager.dequeue();
			
			if(msg == null){
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				queueManager.publish(msg);
				
//				try {
//					Thread.sleep(300);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
			}	
		}
	
	}
	
	
	
}
