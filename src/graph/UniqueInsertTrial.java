package graph;

import java.util.Map;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.UniqueFactory;

public class UniqueInsertTrial {

	private static GraphDatabaseService graphDB = CreateGraph.getInstance();
	private static UniqueFactory<Node> factory = null;
	
	private static enum RelTypes implements RelationshipType
	{
	    KNOWS
	}
	
	public static Node getOrCreateUserWithUniqueFactory(final String firstName) {
		    
		factory = new UniqueFactory.UniqueNodeFactory(graphDB, "users") {
		      @Override
		      protected void initialize(Node created, Map<String, Object> properties) {
		        created.setProperty("id", properties.get("id"));

		        created.setProperty("name", firstName);
		      }
		    };

		    return factory.getOrCreate("id", firstName);
		  }
	
	public static void main(String[] args){
		
		Transaction tx= graphDB.beginTx();
		try{
		getOrCreateUserWithUniqueFactory("Alex").createRelationshipTo
		(getOrCreateUserWithUniqueFactory("Matt"), RelTypes.KNOWS);
		getOrCreateUserWithUniqueFactory("Chris").createRelationshipTo
		(getOrCreateUserWithUniqueFactory("David"), RelTypes.KNOWS);
		getOrCreateUserWithUniqueFactory("Chris").createRelationshipTo
		(getOrCreateUserWithUniqueFactory("Matt"), RelTypes.KNOWS);
		getOrCreateUserWithUniqueFactory("Jack").createRelationshipTo
		(getOrCreateUserWithUniqueFactory("Will"), RelTypes.KNOWS);
		getOrCreateUserWithUniqueFactory("Ben").createRelationshipTo
		(getOrCreateUserWithUniqueFactory("Jack"), RelTypes.KNOWS);
		tx.success();
		
		System.out.println(getOrCreateUserWithUniqueFactory("Matt").getProperty("name"));
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
	