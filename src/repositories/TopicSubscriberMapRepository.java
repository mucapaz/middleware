package repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

public class TopicSubscriberMapRepository {

	@SuppressWarnings("unchecked")
	public static HashMap<String, List<Integer>> readFromDisk(){
		File file = new File("data/topicSubs");
		HashMap<String, List<Integer>> hashmap = new HashMap<String, List<Integer>>();
		if (file.exists()){
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
				hashmap = (HashMap<String, List<Integer>>) ois.readObject();
				ois.close();
				System.out.println("Restored subscribers and topics from disk");
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return hashmap;
	}
	
	public static void saveToDisk(HashMap<String, List<Integer>> hashmap){
		File file = new File("data/topicSubs");
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(file));
	        os.writeObject(hashmap);
	        
	        os.flush();
	        os.close();
	        
	    } catch (IOException e) {
			e.printStackTrace();
	    }
	}
}