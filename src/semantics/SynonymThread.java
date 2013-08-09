package semantics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

public class SynonymThread implements Runnable{

	DocumentVector hitsvector1;
	String word;
	Map<String, ArrayList<String>> mapwm = new HashMap<String, ArrayList<String>>();
	
	public SynonymThread(String key, DocumentVector hitsvector1) {
		// TODO Auto-generated constructor stub
		this.hitsvector1= hitsvector1;
		this.word=key;
		deserializeMap();
	}

		
	public void deserializeMap(){
		try{
		FileInputStream fis = new FileInputStream("map.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        this.mapwm = (HashMap<String, ArrayList<String>>) ois.readObject();
        ois.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void run(){
		
		
		DocumentVector hitsvector2 = new DocumentVector();
		double stdDist;
		Stopwords sw = new Stopwords();
		
		try{
		
				File f=new File("testing_set\\"+word+".txt");
				FileWriter fw= new FileWriter(f);
				PrintWriter pw = new PrintWriter(fw);
				
				String token = "";
				
				for (Entry<String, ArrayList<String>> string1 : mapwm.entrySet()) {
					for( int j=0; j< string1.getValue().size();j++){
						hitsvector2 = new DocumentVector();
						StringTokenizer strtok = new StringTokenizer(string1.getValue().get(j));
						while(strtok.hasMoreTokens()){
							token = strtok.nextToken();
							if(!sw.is(token))
							hitsvector2.incCount(token);
						}
						
						stdDist = hitsvector1.getCosineSimilarityWith(hitsvector2);
						//System.out.println(stdDist);
						// write word @i row : word@j, stdDist if not zero
												
						if(stdDist!=0){
							pw.println(word+" "+string1.getKey()+"  "+stdDist);
							pw.flush();
							//System.out.println(word+" "+string1.getKey()+"  "+stdDist);
							//Pair<String,Double> word_score = new Pair<String, Double>(string1.getKey(),stdDist);
							//set.add(word_score);
						}
					} // end of j
					
				}
				pw.close();
				fw.close();
				/*if(f.length()==0){
					f.delete();
				}*/
			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
}
