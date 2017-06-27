package app;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

import distribution.QueueManagerProxy;
import distribution.message.Operation;

public class Producer {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
		
		QueueManagerProxy proxy1 = new QueueManagerProxy("queue1");
		QueueManagerProxy proxy2 = new QueueManagerProxy("queue2");
		QueueManagerProxy proxy3 = new QueueManagerProxy("queue3");
		
		Random rand = new Random();
		
		while(true){
			Thread.sleep(500);
			//Fila 1
			proxy1.send("topic1", "Conteúdo 1 " + rand.nextInt(10000), Operation.PUBLISH);
			
			
			proxy1.send("topic2", "Tópico 2, Fila 1" + rand.nextInt(10000), Operation.PUBLISH);
			proxy1.send("topic3", "Tópico 3, Fila 1" + rand.nextInt(10000), Operation.PUBLISH);
			
			
			
			
			//Fila 2
			proxy2.send("topic11", "Tópico 11, Fila 2" + rand.nextInt(10000), Operation.PUBLISH);
			proxy2.send("topic22", "Tópico 22, Fila 2" + rand.nextInt(10000), Operation.PUBLISH);
			
			//Fila 3
			proxy3.send("topic111", "Tópico 111, Fila 3" + rand.nextInt(10000), Operation.PUBLISH);
			
			
			System.out.println("Producer sent");
		}
		
	}
	
}
