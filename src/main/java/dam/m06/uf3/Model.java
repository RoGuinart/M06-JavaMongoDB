package dam.m06.uf3;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Model
{
	public static void CreateThread(Thread thr)
	{
		MongoDatabase db = ConnectionManager.getConnection();
		MongoCollection<Document> collection = db.getCollection("Threads");

		collection.insertOne(thr.toDocument());

		ConnectionManager.closeConnection();
	}

	/**
	 * Reads the threads from the database
	 * @param filter  Filter for the threads
	 * @return Array list with threads
	 */
	public static ArrayList<Thread> GetThreads(Bson filter)
	{
		MongoDatabase db = ConnectionManager.getConnection();
		MongoCollection<Document> collection = db.getCollection("Threads");

		FindIterable<Document> doc = collection.find();

		if(filter != null)
			doc = doc.filter(filter);

		ArrayList<Thread> thrs = new ArrayList<Thread>();
		for (Document document : doc) {
			thrs.add(Thread.parseDocument(document));
		}

		ConnectionManager.closeConnection();

		return thrs;
	}

	public static void UpdateThread(Thread thr)
	{

	}

	public static void DeleteThread(Thread thr)
	{
		MongoDatabase db = ConnectionManager.getConnection();
		MongoCollection<Document> collection = db.getCollection("Threads");

		collection.deleteOne(thr.toDocument());

		ConnectionManager.closeConnection();
	}

}
