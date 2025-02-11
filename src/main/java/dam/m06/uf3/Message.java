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

		doc.append("id", id)
		.append("text", text)
		.append("date_posted", date.toString());

		if(attachment != null)
			doc.append("attachments", attachment);

		return doc;
	}

	@Override
	public String toString()
	{
		return text;
	}
}
