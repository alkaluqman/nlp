package graph;

import java.util.Map;

import javax.imageio.spi.RegisterableService;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.UniqueFactory;


public class PopulateGraph {

	private static GraphDatabaseService graphDB;
	private static UniqueFactory<Node> factory = null;
	
	public PopulateGraph(){
		
		graphDB=CreateGraph.getInstance();
		CreateGraph.registerShutdownHook(graphDB);
	}
	
	private static enum RelTypes implements RelationshipType
	{
	    HAS_SYNONYM
	}
	
	public synchronized static Node getOrCreateUniqueNode(final String word, final String meaning ){
		
		factory = new UniqueFactory.UniqueNodeFactory(graphDB, "users") {
		      @Override
		      protected void initialize(Node created, Map<String, Object> properties) {
		        created.setProperty("id", properties.get("id"));

		        created.setProperty("word", word);
		        created.setProperty("meaning", meaning);
		      }
		    };

		    return factory.getOrCreate("id", word);
	}
	
	public synchronized void insertIntoGraph(String word1, String meaning1, 
			String word2, String meaning2, double docSimScore){
		
		// transaction
		Transaction tx= graphDB.beginTx();
		try{
			Relationship rel= getOrCreateUniqueNode(word1, meaning1).
			createRelationshipTo(getOrCreateUniqueNode(word2, meaning2), RelTypes.HAS_SYNONYM);
			rel.setProperty("score", docSimScore);
						
			tx.success();
		}
		catch(Exception e){
			tx.failure();
			e.printStackTrace();
		}
		finally{
			tx.finish();
		}
	}
	
}