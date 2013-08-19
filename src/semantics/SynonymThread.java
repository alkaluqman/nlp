package semantics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;  

import sun.font.CreatedFontTracker;

public class SynonymThread implements Runnable{

	DocumentVector hitsvector1;
	String word;
	
	public SynonymThread(String key, DocumentVector hitsvector1) {
		// TODO Auto-generated constructor stub
		this.hitsvector1= hitsvector1;
		this.word=key;
		
	}

	
	public void run(){
		
		
		DocumentVector hitsvector2 = new DocumentVector();
		double stdDist;
		Stopwords sw = new Stopwords();
		File f;
		try{
			if(System.getProperty("os.name").startsWith("Windows")){
				f=new File("results\\"+word+".txt");
				f.createNewFile();
			}
			else{
				f=new File("results/"+word);
				f.createNewFile();
			}
				
				FileWriter fw= new FileWriter(f);
				PrintWriter pw = new PrintWriter(fw);
				
				String token = "";
				
				for (Entry<String, ArrayList<String>> string1 : GenerateSynonym.mapwm.entrySet()) {
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
				
			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
}
