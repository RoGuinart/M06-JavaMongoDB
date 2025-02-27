package dam.m06.uf3;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONException;
import org.json.JSONObject;

public class Message
{
	private String text;
	private ZonedDateTime date;
	private String attachment; // Link to document

	public Message(String text, String attachment)
	{
		this(text, attachment, ZonedDateTime.now());
	}

	public Message(String text, String attachment, ZonedDateTime date)
	{
		this.text = text;
		this.attachment = attachment;
		this.date = date;
	}

	public String getText()
	{
		return text;
	}

	public String getAttachments()
	{
		return attachment;
	}

	public String toJson()
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("date_posted", date.format(DateTimeFormatter.ISO_INSTANT));
		jsonObject.put("text", text);
		jsonObject.put("attachment", attachment); // Link to document
		return jsonObject.toString();
	}

	public static Message parseJson(JSONObject json)
	{
		ZonedDateTime date;
		String dateStr = json.getString("date_posted");
		date = (dateStr != null) ? ZonedDateTime.ofInstant(Instant.parse(dateStr), ZoneId.of("UTC")) : null;

		String text = json.getString("text");
		String attachment;

		// How to check for optional parameters???
		try {
			attachment = json.getString("attachment");
		} catch (JSONException e) {
			attachment = null;
		}

		return new Message(text, attachment, date);
	}

	@Override
	public String toString()
	{
		return text;
	}
}
