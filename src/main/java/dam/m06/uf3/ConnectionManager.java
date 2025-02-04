package dam.m06.uf3;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnectionManager
{
	private static String url = null;
	private static String dbString;
	private static boolean active = false;
	private static MongoClient connection;
	private static MongoDatabase database;

	/**
	 * Sets up necessary components for the database. This includes
	 * username, password, cluster name and database name. These are all
	 * written in database.xml.
	 * 
	 * This method does not return a connection, please call getConnection()
	 * 
	 * @return true on success, false on error
	 */
	public static boolean init()
	{
		final String base = "mongodb+srv://%s:%s@%s.mongodb.net";
		File file = new File("database.xml");
		String user, pwd, cluster;
		boolean error = false;

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(file);

			user = doc.getElementsByTagName("user").item(0).getTextContent();
			pwd = doc.getElementsByTagName("password").item(0).getTextContent();
			cluster = doc.getElementsByTagName("cluster").item(0).getTextContent();
			dbString = doc.getElementsByTagName("database").item(0).getTextContent();

			url = String.format(base, user, pwd, cluster);
		} catch(Exception e) {
			e.printStackTrace();
			error = true;
		}

		return error;
	}

	/**
	 * Creates a MongoDB database or returns an already exissting one.
	 * The database 
	 * 
	 * @return MongoClient database or null on error.
	 */
	public static MongoDatabase getConnection()
	{
		if(url == null && init())
			return null;

		if(active)
			return database;


		connection = MongoClients.create(url);
		database = connection.getDatabase(dbString);
		active = true;

		return database;
	}

	/**
	 * Closes a MongoDB connection if present.
	 */
	public static void closeConnection()
	{
		if(!active)
			return;

		connection.close();
		active = false;
	}
}
