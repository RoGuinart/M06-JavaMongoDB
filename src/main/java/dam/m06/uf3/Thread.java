package dam.m06.uf3;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Thread
{
	private ObjectId _id;
	private Message main_post;
	private ArrayList<Message> replies;

	public Thread(Message main_post)
	{
		this(main_post, new ArrayList<Message>());
	}

	public Thread(Message main_post, ArrayList<Message> replies)
	{
		this(new ObjectId(), main_post, replies);
	}

	public Thread(ObjectId _id, Message main_post, ArrayList<Message> replies)
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

	public ObjectId getId()
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

	public Document toDocument()
	{
		ArrayList<Document> replyDoc = new ArrayList<Document>();
		for (Message msg : replies)
			replyDoc.add(msg.toDocument());

		Document doc = new Document("_id", _id);
		doc.append("main_post", main_post.toDocument())
		.put("replies", replyDoc);

		return doc;
	}

	public static Thread parseDocument(Document doc)
	{
		ObjectId _id = doc.getObjectId("_id");
		Message main_post = Message.parseDocument((Document) doc.get("main_post"));

		ArrayList<Document> replyDoc = (ArrayList<Document>) doc.get("replies");
		ArrayList<Message> replies = new ArrayList<Message>();
		for (Document reply : replyDoc) {
			replies.add(Message.parseDocument(reply));
		}

		return new Thread(_id, main_post, replies);
	}

	@Override
	public String toString()
	{
		return main_post.getText();
	}
}
