package semantics;

import graph.PopulateGraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/*import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import semantics.DocumentVector;
import semantics.Stopwords;
import semantics.Pair;

public class GenerateSynonym implements Serializable{

	public static Map<String, ArrayList<String>> mapwm = new HashMap<String, ArrayList<String>>();
	private Map<String, ArrayList<Pair<String,Double>>> synset = new HashMap<String, ArrayList<Pair<String,Double>>>();
	private final int NUM_THREADS= Runtime.getRuntime().availableProcessors() +1;
	private final ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
	
	
	
	
	public GenerateSynonym() {
		deserializeMap();
	}

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
		
	/*	//create results directory
		try{
		if(System.getProperty("os.name").startsWith("Windows")){
			String path= new java.io.File(".").getCanonicalPath()+"\\results";
			Path dir= Paths.get(path);
			Files.createDirectory(dir);	
		}
		else{
			FileAttribute<Set<PosixFilePermission>> perms= 
					PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx")); 
			String path= new java.io.File(".").getCanonicalPath()+"/results";
			Files.createDirectory(Paths.get(path), perms);
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}*/
		
		// create neo4j graph
		PopulateGraph graph= new PopulateGraph();
		
		//threading
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
				/*SynonymThread vector= new SynonymThread(string.getKey(),hitsvector1);
				new Thread(vector).start();*/
			
			//create thread pool
			SynonymThread vector= new SynonymThread(string.getKey(),hitsvector1,graph);
			executor.execute(vector);
				
				
		}
		}catch(Exception e){
			executor.shutdown();
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
