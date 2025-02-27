package dam.m06.uf3;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Thread
{
	private String _id;
	private Message main_post;
	private ArrayList<Message> replies;

	public Thread(Message main_post)
	{
		this(main_post, new ArrayList<Message>());
	}

	public Thread(Message main_post, ArrayList<Message> replies)
	{
		this(null, main_post, replies);
	}

	public Thread(String _id, Message main_post, ArrayList<Message> replies)
	{
		this._id = _id;
		this.main_post = main_post;
		this.replies = replies;
	}

	/**
	 * Sets a reply ID for a thread
	 */
	public void reply(Message reply)
	{
		//replies.add(reply);
	}

	public String getId()
	{
		return _id;
	}

	public Message getMainPost()
	{
		return main_post;
	}
	
	public ArrayList<Message> getReplies()
	{
		return replies;
	}

	public String toJson()
	{
		JSONObject jsonObject = new JSONObject();
		JSONArray repliesJSON = new JSONArray();
		for (Message msg : replies)
			repliesJSON.put(msg.toJson());

		jsonObject.put("_id", _id);
		jsonObject.put("main_post", main_post.toJson());
		jsonObject.put("replies", replies.toString());

		System.out.println(jsonObject);
		return jsonObject.toString();
	}

	public static Thread parseJson(JSONObject json)
	{
		String _id;
		Message main_post;
		ArrayList<Message> replies = new ArrayList<Message>();

		_id = json.getString("_id");
		main_post = Message.parseJson(json.getJSONObject("main_post"));
		JSONArray replyJson = json.getJSONArray("replies");
		for (int i = 0; i < replyJson.length(); i++) {
			JSONObject jsonObject = replyJson.getJSONObject(i);

			replies.add(Message.parseJson(jsonObject));
		}

		return new Thread(_id, main_post, replies);
	}

	@Override
	public String toString()
	{
		return main_post.getText();
	}
}
