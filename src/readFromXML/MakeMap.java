package readFromXML;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class MakeMap implements Serializable{

	private ArrayList<Monier> mList = new ArrayList<Monier>();
	private Map<String, ArrayList<String>> mapwm = new HashMap<String, ArrayList<String>>();


	public MakeMap(ArrayList<Monier> mList) {
		super();
		this.mList = mList;
		System.out.println("mList size= " + mList.size());
	}

	public void writeMap(Map<String, ArrayList<String>> map) {
		
		try {
			FileWriter fw = new FileWriter("TempMap.txt");
			PrintWriter pw = new PrintWriter(fw);

			for (Entry<String, ArrayList<String>> word : map.entrySet()) {
				 //System.out.println("Word: "+word.getKey()+"\t Meaning: "+word.getValue());
				 //System.out.println(word.getKey()+" No of meanings= "+word.getValue().size());
				for(int i=0; i<word.getValue().size(); i++){
					pw.println("Word: " + word.getKey() + "\t Meaning: "
							+ word.getValue().get(i));
				}
			
				pw.flush();
			}
			pw.close();
			fw.close();
			// System.out.println("Map size= "+map.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void serializeMap(Map<String, ArrayList<String>> map){
		try{
		FileOutputStream fos = new FileOutputStream("map.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(map);
        oos.flush();
        oos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void mapWM() {

		ArrayList<String> meaningList= new ArrayList<String>();
		for (Monier word : mList) {
			if(mapwm.containsKey(word.getKey1())){
				meaningList= mapwm.get(word.getKey1());
				meaningList.add(word.getC());
				mapwm.put(word.getKey1(), meaningList);
			}
			else{
				meaningList= new ArrayList<String>();
				meaningList.add(word.getC());
				mapwm.put(word.getKey1(), meaningList);
			}
			
			// System.out.println(word.getKey1()+" = "+ word.getC());
		}

		System.out.println("Map size= " + mapwm.size());
		System.out.println("writing map to file");
		writeMap(mapwm);
		System.out.println("serializing map");
		serializeMap(mapwm);
		System.out.println("finished serializing");
	}


}
