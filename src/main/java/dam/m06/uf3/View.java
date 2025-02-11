package dam.m06.uf3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class View
{
	public static int menu(Scanner in)
	{
		return getInt(in,
			"""
			What do you want to do?
			0. Quit
			1. See active threads
			2. See threads filtered by date
			3. Create thread
			4. Delete thread
			5. See messages on a thread
			6. See messages on thread filtered by date
			7. Reply to a thread
			8. Delete a message on a thread
			"""
		);
	}

	public static void SeeThreads(ArrayList<Thread> threads, boolean numbered)
	{
		int i = 1;
		System.out.println("\nThreads:"); // Blank line
		for (Thread thr : threads) {
			if(numbered)
				System.out.printf("%3d. ", i++);

			System.out.println(thr);
		}
		System.out.println();
	}

	public static Thread CreateThread(Scanner in)
	{
		String op = getString(in, "Please write the thread message:");

		String attachment = getAttachment(in);
		Message main_post = new Message(op, attachment);
		Thread thr = new Thread(main_post);

		return thr;
	}

	public static Thread DeleteThread(Scanner in, ArrayList<Thread> thrs)
	{
		System.out.println("Select thread to delete.");
		return getThread(in, thrs);
	}

	public static void SeeReplies(Thread thr, boolean numbered)
	{
		System.out.println(thr);
		for (Message msg : thr.getReplies()) {
			System.out.printf("%3d. %s\n", msg.getId(), msg.getText());
		}
		System.out.println(); // Blank line
	}

	/**
	 * Creates a new message inside a thread
	 * @param in  Scanner to read from
	 * @param thr Thread to reply to. This same object must then be uploaded
	 *            to the database
	 */
	public static void ReplyToThread(Scanner in, Thread thr)
	{
		String op = getString(in, "Please write the thread message:");

		String attachment = getAttachment(in);
		Message main_post = new Message(op, attachment);

		thr.reply(main_post);
	}

	public static void DeleteReply(Scanner in, Thread thr)
	{
		System.out.println("Select message to delete.");
		Message msg = getReply(in, thr);

		if(msg != null)

			thr.getReplies().remove(msg);
	}
	
	
	//
	// HELPER FUNCTIONS
	//

	/**
	 * Asks for an integer number. Does not leave until it receives a valid input.
	 * @param in Scanner
	 * @param prompt Message to print before receiving input.
	 * @return integer input
	 */
	static int getInt(Scanner in, String prompt) 
	{
		int result = 0;
		boolean error;
		do {
			error = false;
			System.out.println(prompt);
			if(in.hasNextInt()) {
				result = in.nextInt();
			} else {
				System.err.println("Invalid input.");
				error = true;
			}
			in.nextLine();
		} while (error);
		return result;
	}

	/**
	 * Asks for a string
	 * @param in Scanner
	 * @param prompt Prompt to print before receiving input.
	 * @return String written by the user
	 */
	static String getString(Scanner in, String prompt) 
	{
		String result = "";
		boolean error;
		do {
			error = false;
			System.out.println(prompt);
			if(in.hasNextLine()) {
				result = in.nextLine();
			} else {
				System.err.println("Invalid input.");
				error = true;
			}
		} while (error);
		return result;
	}

	/**
	 * Asks for an attachment link. It does not check whether this link is valid.
	 * @param in Scanner
	 * @return Link to document or null if there is none
	 */
	static String getAttachment(Scanner in) 
	{
		String result = "";
		boolean error;
		do {
			error = false;
			System.out.println("Enter a document link to attach to the message, or press Enter to skip:");
			if(in.hasNextLine()) {
				result = in.nextLine();
			} else {
				System.err.println("Invalid input.");
				error = true;
			}
		} while (error);

		if(result.equals(""))
			return null;

		return result;
	}

	/**
	 * Asks for a thread
	 * 
	 * @param in Scanner
	 * @param thrs List of available threads
	 * @return Selected thread or null if cancelled.
	 */
	static Thread getThread(Scanner in, ArrayList<Thread> thrs)
	{
		int threadCount = thrs.size();
		int result = -1;
		boolean error;
		
		System.out.println("Type \"quit\" to cancel, \"list\" to show the thread list again.");
		// Show active threads
		SeeThreads(thrs, true);
		
		do {
			error = false;

			if(in.hasNextInt()) {
				result = in.nextInt();
			
				if(result > threadCount || result <= 0) {
					System.err.println("Invalid input.");
					error = true;
				}
			} else {
				String tmp = in.nextLine();

				if(tmp.equals("quit"))
					return null;
				else if(tmp.equals("list"))
					SeeThreads(thrs, true);
				else 
					System.err.println("Invalid input.");
			
				error = true;
				continue;
			}
			in.nextLine();
		} while (error);
	
		return thrs.get(result - 1);
	}

	/**
	 * Asks user to select a reply from a thread
	 * 
	 * @param in Scanner
	 * @param thr Thread to read from
	 * @return Selected message or null if cancelled.
	 */
	static Message getReply(Scanner in, Thread thr)
	{
		ArrayList<Message> msgs = thr.getReplies();
		int threadCount = msgs.size();
		int result = -1;
		boolean error;
		
		System.out.println("Type \"quit\" to cancel, \"list\" to show the replies again.");

		// Show replies to thread
		SeeReplies(thr, true);
		
		do {
			error = false;

			if(in.hasNextInt()) {
				result = in.nextInt();
			
				if(result >= threadCount || result < 0) {
					System.err.println("Invalid input.");
					error = true;
				}
			} else {
				String tmp = in.nextLine();

				if(tmp.equals("quit"))
					return null;
				else if(tmp.equals("list"))
					SeeReplies(thr, true);
				else 
					System.err.println("Invalid input.");
			
				error = true;
				continue;
			}
			in.nextLine();
		} while (error);
	
		return msgs.get(result);
	}

	public static LocalDateTime getDate(Scanner in, String prompt)
	{
		boolean error;
		String dateStr;
		LocalDateTime date = null;
		do {
			error = false;
			try {
				dateStr = getString(in, prompt);
				if(dateStr.equals(""))
					return null;

				dateStr = dateStr.replace(' ', 'T'); // Nevessary for ISO 8601

				date = LocalDateTime.parse(dateStr);
			} catch (Exception e) {
				error = true;
				System.err.println("Invalid date! Format is YYYY-MM-DD HH:mm:SS");
			}

		} while (error);

		return date;
	}
}
