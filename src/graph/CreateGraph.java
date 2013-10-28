package graph;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class CreateGraph {

	// singleton implementation
	
	private static String DB_PATH = "trial";
	private static GraphDatabaseService graphDB = null;
	
	private CreateGraph(){
	// empty	
	}
	
	public static GraphDatabaseService getInstance(){
		
		if(graphDB==null){
			graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
			
		}
		
		return graphDB;
	}
	
	public void stopInstance(){
		graphDB.shutdown();
	}
	
	public static void registerShutdownHook( final GraphDatabaseService graphDb ){
		
	    Runtime.getRuntime().addShutdownHook( new Thread(){
	        public void run(){
	            graphDb.shutdown();
	        }
	    } );
	}
	
}