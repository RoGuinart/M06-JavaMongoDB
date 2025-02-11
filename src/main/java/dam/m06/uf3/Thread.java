package dam.m06.uf3;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.bson.Document;

public class Thread
{
	private static int id_count; // TODO: get highest thread ID
	private int id;
	private int reply_count = 0; // Used to create message ID when replying
	private Message main_post;
	private ArrayList<Message> replies;

	public Thread(Message main_post)
	{
		main_post.setId(reply_count++);
		this.main_post = main_post;
		this.replies = new ArrayList<Message>();
		this.id = id_count++;
	}

	public Thread(int id, Message main_post)
	{
		this(main_post);
		this.id = id;
	}

	public Thread(int id, Message main_post, ArrayList<Message> replies)
	{
		this.id = id;
		this.main_post = main_post;
		this.replies = replies;

		try {
			// Last reply will always have the highest ID
			this.reply_count = replies.getLast().getId();
		} catch (NoSuchElementException e) {
			// If there are no replies, set to 1, for the next reply
			this.reply_count = 1;
		}
	}

	public void reply(Message reply)
	{
		reply.setId(reply_count++);
		replies.add(reply);
	}

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
		.append("replies", replies); // Would this work as intended?

		return doc;
	}

	public static Thread parseDocument(Document doc)
	{
		int id = (Integer) doc.get("thread_id");
		Message main_post = Message.parseDocument((Document) doc.get("main_post"));

		ArrayList<Document> replyDoc = (ArrayList<Document>) doc.get("replies");
		ArrayList<Message> replies = new ArrayList<Message>();
		for (Document reply : replyDoc) {
			replies.add(Message.parseDocument(reply));
		}

		return new Thread(id, main_post, replies);
	} 

	@Override
	public String toString()
	{
		return main_post.getText();
	}
}
