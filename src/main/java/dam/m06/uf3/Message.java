package dam.m06.uf3;

import java.time.LocalDateTime;

import org.bson.Document;

public class Message
{
	private int id;
	private String text;
	private LocalDateTime date;
	private String attachment; // Link to document

	public Message(int id, String text)
	{
		this(id, text, null, LocalDateTime.now());
	}

	public Message(String text, String attachment)
	{
		this.text = text;
		this.attachment = attachment;
		this.date = LocalDateTime.now();
	}

	public Message(int id, String text, String attachment, LocalDateTime date)
	{
		this.id = id;
		this.text = text;
		this.attachment = attachment;
		this.date = date;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public String getText()
	{
		return text;
	}

	public String getAttachments()
	{
		return attachment;
	}

	public Document toDocument()
	{
		Document doc = new Document();

		doc.append("reply_id", id)
		.append("date_posted", date.toString())
		.append("text", text);

		if(attachment != null)
			doc.append("attachment", attachment);

		return doc;
	}

	public static Message parseDocument(Document doc)
	{
		int reply_id = (Integer) doc.get("reply_id");

		LocalDateTime date;
		String dateStr = (String) doc.get("date_posted");
		date = (dateStr != null) ? LocalDateTime.parse(dateStr) : null;

		String text = (String) doc.get("text");
		String attachment = (String) doc.get("attachment");

		return new Message(reply_id, text, attachment, date);
	}

	@Override
	public String toString()
	{
		return text;
	}
}
