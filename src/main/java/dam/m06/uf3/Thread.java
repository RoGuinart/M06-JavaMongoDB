package dam.m06.uf3;

import java.util.ArrayList;

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
		// Last reply will always have the highest ID
		this.reply_count = replies.getLast().getId(); 
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

	@Override
	public String toString()
	{
		return main_post.getText();
	}
}
