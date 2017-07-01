package repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

public class ConnectionIdRepository {

	public static int readFromDisk(){
		File file = new File("data/connectionId");
		int connectionId = 0;
		if (file.exists()){
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
				connectionId = (int) ois.readObject();
				ois.close();
				System.out.println("Restored Last Connection Id Used: "+ connectionId);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return connectionId;
	}
	
	public static void saveToDisk(int connectionId){
		File file = new File("data/connectionId");
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(file));
	        os.writeObject(connectionId);
	        
	        os.flush();
	        os.close();
	        
	    } catch (IOException e) {
			e.printStackTrace();
	    }
	}
}