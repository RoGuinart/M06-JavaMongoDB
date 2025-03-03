package dam.m06.uf3;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Message
{
	private ObjectId _id;
	private String text;
	private ZonedDateTime date;
	private String attachment; // Link to document

	public Message(String text, String attachment)
	{
		this(text, attachment, ZonedDateTime.now());
	}

	public Message(String text, String attachment, ZonedDateTime date)
	{
		this(new ObjectId(), text, attachment, date);
	}

	public Message(ObjectId _id, String text, String attachment, ZonedDateTime date)
	{
		this._id = _id;
		this.text = text;
		this.attachment = attachment;
		this.date = date;
	}

	public ObjectId getId()
	{
		return _id;
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
		Document doc = new Document("_id", _id);

		doc.append("date_posted", date.format(DateTimeFormatter.ISO_INSTANT))
		.append("text", text);

		if(attachment != null)
			doc.append("attachment", attachment);

		return doc;
	}

	public static Message parseDocument(Document doc)
	{
		ObjectId _id = doc.getObjectId("_id");

		ZonedDateTime date;
		String dateStr = doc.getString("date_posted");
		date = (dateStr != null) ? ZonedDateTime.ofInstant(Instant.parse(dateStr), ZoneId.of("UTC")) : null;

		String text = doc.getString("text");
		String attachment = doc.getString("attachment");

		return new Message(_id, text, attachment, date);
	}

	@Override
	public String toString()
	{
		return text;
	}
}