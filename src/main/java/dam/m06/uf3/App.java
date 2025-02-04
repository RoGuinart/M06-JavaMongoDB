package dam.m06.uf3;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App 
{
	public static String dbString()
	{
		String base = "mongodb+srv://%s:%s@%s.mongodb.net";
		File file = new File("database.xml");

		String user, pwd, cluster;

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(file);

			user = doc.getElementsByTagName("user").item(0).getTextContent();
			pwd = doc.getElementsByTagName("password").item(0).getTextContent();
			cluster = doc.getElementsByTagName("cluster").item(0).getTextContent();

			return String.format(base, user, pwd, cluster);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main( String[] args ) {
		String uri = dbString();

		if(uri == null) {
			System.err.println("Cannot find database.");
			return;
		}


		try (MongoClient mongoClient = MongoClients.create(uri)) {
			MongoDatabase database = mongoClient.getDatabase("Forum");
			MongoCollection<Document> collection = database.getCollection("Threads");
			FindIterable<Document> doc = collection.find();

			for (Document document : doc) {
				System.out.println(document.toJson());
			}
		}
	}
}
