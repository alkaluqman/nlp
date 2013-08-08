package semantics;


import java.util.ArrayList;

import readFromXML.MakeMap;
import readFromXML.Monier;
import readFromXML.ReadXMLDOM;


public class Main {
	
	private static ArrayList<Monier> mList= new ArrayList<Monier>();
	
	public static void main(String args[]){
	
/*		ReadXMLDOM handler = new ReadXMLDOM("monier.xml");
		//handler.displayList();
		mList = handler.getList(); 	
		
		
		MakeMap maps= new MakeMap(mList);
		maps.mapWM();*/
		
	
		GenerateSynonym gs= new GenerateSynonym();
		gs.deserializeMap(); System.out.println("Generating synonyms");
		gs.synonymMap();
		System.out.println("Serializing");
		//gs.displaySynset();
		//gs.serializeSynset();
		System.out.println("Completed");
	}
		
}
