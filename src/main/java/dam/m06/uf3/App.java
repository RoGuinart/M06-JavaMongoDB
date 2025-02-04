package dam.m06.uf3;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App 
{
	public static void main( String[] args ) {
		try {
			MongoDatabase db = ConnectionManager.getConnection();
			MongoCollection<Document> collection = db.getCollection("Threads");
			FindIterable<Document> doc = collection.find();
			
			for (Document document : doc) {
				System.out.println(document.toJson());
			}

			ConnectionManager.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
