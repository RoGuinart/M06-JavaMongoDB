package dam.m06.uf3;

import java.util.ArrayList;

import org.bson.Document;

public class Thread {
	private int id;
	private Message main_post;
	private ArrayList<Message> replies;


	public int getId()
	{
		return id;
	}

	public Message getMainPost()
	{
		return main_post;
	}
	
	public ArrayList<Message> getReplies()
	{
		return replies;
	}

	public Document toDocument()
	{
		Document doc = new Document();

		doc.append("id", id)
		.append("main_post", main_post)
		.append("replies", replies);

		return doc;
	}

	@Override
	public String toString() {
		return main_post.getText();
	}
}
