package teste;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import distribution.message.*;

public class Teste {
//
//	public static void main(String[] args) {
//		try {
//			serializing();
//			testMessageCreator();
//			
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void serializing() throws UnknownHostException, IOException {
//		Payload payload = new Payload();
//		payload.add("teste");
//		Message ms = new Message(new Header("test", Operation.SUBSCRIBE), payload);
//
//		FileOutputStream fo = new FileOutputStream("test.ser");
//		ObjectOutputStream oo = new ObjectOutputStream(fo);
//		oo.writeObject(ms); //
//		oo.close();
//
//		FileInputStream fi = new FileInputStream("test.ser");
//		ObjectInputStream oi = new ObjectInputStream(fi);
//		try {
//			Message m =(Message) oi.readObject();
//			System.out.println(m);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		oi.close();
//	}
//	
//	public static void testMessageCreator() {
//		MessageCreator msgc = new MessageCreator();
//		msgc.setDestination("dest");
//		msgc.addContent("con");
//		msgc.addContent(1);
//		msgc.addContent(9.9);
//		
//		System.out.println(msgc.getMessage());
//	}

}
