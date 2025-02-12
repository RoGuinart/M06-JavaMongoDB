package dam.m06.uf3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

public class Main
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);

		// Dirty way to setup Thread's id_count variable
		Model.GetThreads(null);

		int choice;
		do {
			choice = View.menu(in);

			switch (choice) {
			case 0: // Quit
			{
				System.out.println("Quitting...");
				break;
			}
			case 1: // See threads
			{
				ArrayList<Thread> thrs = Model.GetThreads(null);
				View.SeeThreads(thrs, true);
				
				break;
			}
			case 2: // See threads by date
			{
				SeeThreadsByDate(in);
				break;
			}
			case 3: // Create thread
			{
				Thread thr = View.CreateThread(in);
				Model.CreateThread(thr);

				break;
			}
			case 4: // Delete thread
			{
				Thread thr = View.getThread(in, Model.GetThreads(null));
				Model.DeleteThread(thr);
				break;
			}
			case 5: // See messages on thread
			{
				ArrayList<Thread> thrs = Model.GetThreads(null);
				Thread thr = View.getThread(in, thrs);
				
				View.SeeReplies(thr, false);
				break;
			}
			case 6: // See messages on thread filtered by date
			{
				break;
			}
			case 7: // Reply to a thread
			{
				Thread thr = View.getThread(in, Model.GetThreads(null));

				// Cancel
				if(thr == null)
					break;

				Message msg = View.ReplyToThread(in);

				Model.ReplyToThread(thr, msg);
				break;
			}
			case 8: // Delete message on a thread
			{
				Thread thr = View.getThread(in, Model.GetThreads(null));

				// Cancel
				if(thr == null)
					break;
				Message msg = View.getReply(in, thr);

				// Cancel
				if(msg == null)
					break;

				Model.DeleteReply(thr, msg);
				break;
			}
			default:
				System.err.println("Invalid input.");
				break;
			}
		} while (choice != 0);
	}

	private static void SeeThreadsByDate(Scanner in)
	{
		LocalDateTime date_min, date_max;
		Bson filter;

		boolean loop;

		do {
			loop = false;
			System.out.println("Format YYYY-MM-DD HH:mm:SS (blank for no date)");
			date_min = View.getDate(in, "Please write the minimum date: ");
			date_max = View.getDate(in, "Please write the maximum date: ");

			if(date_min == null && date_max == null) {
				System.err.println("You must write at least one date!");
				loop = true;
			}
		} while (loop);

		final String fieldName = "main_post.date_posted";

		if(date_min == null) {
			filter = Filters.lte(fieldName, date_max.toString());
		} else if(date_max == null) {
			filter = Filters.gte(fieldName, date_min.toString());
		} else {
			filter = Filters.and(
				Filters.gte(fieldName, date_min.toString()), 
				Filters.lte(fieldName, date_max.toString())
			);
		}

		ArrayList<Thread> thrs = Model.GetThreads(filter);
		View.SeeThreads(thrs, false);

	}
}
