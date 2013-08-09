package semantics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import semantics.DocumentVector;
import semantics.Stopwords;
import java.util.Iterator;
import semantics.Pair;

public class GenerateSynonym implements Serializable{

	private Map<String, ArrayList<String>> mapwm = new HashMap<String, ArrayList<String>>();
	private Map<String, ArrayList<Pair<String,Double>>> synset = new HashMap<String, ArrayList<Pair<String,Double>>>();
	
	
	public void deserializeMap(){
		try{
		FileInputStream fis = new FileInputStream("map.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        mapwm = (HashMap<String, ArrayList<String>>) ois.readObject();
        ois.close();
        
     /*   //testing
        for (Entry<String, ArrayList<String>> word : mapwm.entrySet()) {
			 System.out.println("Word: "+word.getKey()+"\t Meaning: "+word.getValue());
        }
        */
        
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void synonymMap() {
		DocumentVector hitsvector1 = new DocumentVector();
		
		Stopwords sw = new Stopwords();
		//ArrayList<Pair<String, Double>> set= new ArrayList<Pair<String,Double>>();
		try{
		
		String token = "";
		for (Entry<String, ArrayList<String>> string : mapwm.entrySet()) {
			for( int i=0; i<string.getValue().size(); i++){
				hitsvector1 = new DocumentVector();
				StringTokenizer strtok = new StringTokenizer(string.getValue().get(i));
				while(strtok.hasMoreTokens()){
					token = strtok.nextToken();
					if(!sw.is(token))
					hitsvector1.incCount(token);
				}
			}
				//create new thread
				SynonymThread vector= new SynonymThread(string.getKey(),hitsvector1);
				new Thread(vector).start();
				
				
		}
		}catch(Exception e){
			e.printStackTrace();
		}		
		
	}

	public void displaySynset(){
		
		try{
		 for (Entry<String, ArrayList<Pair<String, Double>>> word : synset.entrySet()) {
			 System.out.println("Word: "+word.getKey()+"\t Synonym: "+word.getValue());
		 }
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public void serializeSynset(){
		try{
		FileOutputStream fos = new FileOutputStream("synset.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(synset);
        oos.flush();
        oos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
