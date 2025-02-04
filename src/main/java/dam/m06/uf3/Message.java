package dam.m06.uf3;

import java.util.ArrayList;

import org.bson.Document;

public class Message
{
	private int id;
	private String text;
	private ArrayList<String> attachments; // Link to document

	public int getId()
	{
		return id;
	}

	public String getText()
	{
		return text;
	}

	public ArrayList<String> getAttachments()
	{
		return attachments;
	}

	public Document toDocument()
	{
		Document doc = new Document();

		doc.append("id", id)
		.append("text", text)
		.append("attachments", attachments);

		return doc;
	}

	@Override
	public String toString()
	{
		return text;
	}
}
