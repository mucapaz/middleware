package distribution;

import configuration.Config;
import distribution.message.Message;

public class MessagePassThread implements Runnable{
	
	private QueueManager queueManager;
	
	boolean hasDelay;
	
	public MessagePassThread(QueueManager queueManager, boolean hasDelay){		
		this.queueManager = queueManager;
		this.hasDelay = hasDelay;
	}

	@Override
	public void run() {	
		
		if(hasDelay){
			try {
				Thread.sleep(Config.delayAfterRestart);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}	
		
		
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
			}	
		}
	
	}
	
	
	
}
