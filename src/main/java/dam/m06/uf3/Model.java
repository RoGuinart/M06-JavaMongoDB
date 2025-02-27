package dam.m06.uf3;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Model
{
	private static final String API_URL = "https://rguinart-mongodb-api-chi.vercel.app";

	public static void CreateThread(Thread thr)
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("text", thr.getMainPost().getText());

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(API_URL + "/add"))
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(thr.getMainPost().toJson()))
			.build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the threads from the database
	 * @param filter  Filter for the threads
	 * @param date_min Filter, minimum date allowed
	 * @param date_max Filter, maximum date allowed
	 * @return Array list with threads
	 */
	public static ArrayList<Thread> GetThreads(Instant date_min, Instant date_max)
	{
		ArrayList<Thread> thrs = new ArrayList<Thread>();
		try {
			String list;
			if(date_min != null && date_max != null)
				list = String.format("/list/%s/%s", date_min, date_max);
			else
				list = "/list";

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(API_URL + list))
				.GET()
				.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			String responseBody = response.body();

			// Parse JSON response
			JSONArray jsonArray = new JSONArray(responseBody);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				thrs.add(Thread.parseJson(jsonObject));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return thrs;
	}

	public static void DeleteThread(Thread thr)
	{
/*
		MongoDatabase db = ConnectionManager.getConnection();
		MongoCollection<Document> collection = db.getCollection("Threads");

		collection.deleteOne(thr.toJson());

		ConnectionManager.closeConnection();
*/
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("text", thr.getMainPost().getText());

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(API_URL + "/thread/" + thr.getId()))
			.header("Content-Type", "application/json")
			.DELETE()
			.build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void ReplyToThread(Thread thr, Message reply)
	{
/*
		MongoDatabase db = ConnectionManager.getConnection();
		MongoCollection<Document> collection = db.getCollection("Threads");

		thr.reply(reply); // Set message ID in the thread

		Document query = new Document().append("thread_id", thr.getId());
		Bson update = Updates.addToSet("replies", reply.toJson());

		collection.updateOne(query, update);

		ConnectionManager.closeConnection();
/* /
	JSONObject jsonObject = new JSONObject();
	jsonObject.put("text", thr.getMainPost().getText());

	HttpClient client = HttpClient.newHttpClient();
	HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create(url + "/thread/" + thr.getId()))
		.header("Content-Type", "application/json")
		.PUT(HttpRequest.BodyPublishers.ofString(thr.toJson().toString()))
		.build();

	try {
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
	} catch (IOException | InterruptedException e) {
		e.printStackTrace();
	}

*/
	}

	public static void DeleteReply(Thread thr, Message reply)
	{
/*
		MongoDatabase db = ConnectionManager.getConnection();
		MongoCollection<Document> collection = db.getCollection("Threads");

		Document query = new Document().append("thread_id", thr.getId());
		Bson update = Updates.pull("replies", reply.toJson());

		collection.updateOne(query, update);

		ConnectionManager.closeConnection();
*/
	}

}
