package dam.m06.uf3;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);

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
				ArrayList<Thread> thrs = Model.GetThreads(null, null);
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
				Thread thr = View.getThread(in, Model.GetThreads(null, null));
				Model.DeleteThread(thr);
				break;
			}
			case 5: // See messages on thread
			{
				ArrayList<Thread> thrs = Model.GetThreads(null, null);
				Thread thr = View.getThread(in, thrs);
				
				View.SeeReplies(thr, false);
				break;
			}
			case 6: // Reply to a thread
			{
				Thread thr = View.getThread(in, Model.GetThreads(null, null));

				// Cancel
				if(thr == null)
					break;

				Message msg = View.ReplyToThread(in);

				Model.ReplyToThread(thr, msg);
				break;
			}
			case 7: // Delete message on a thread
			{
				Thread thr = View.getThread(in, Model.GetThreads(null, null));

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
		Instant date_min, date_max;

		System.out.println("Format YYYY-MM-DD (HH:mm:SS) (blank for current time)");
		date_min = View.getDate(in, "Please write the minimum date: ");
		date_max = View.getDate(in, "Please write the maximum date: ");

		ArrayList<Thread> thrs = Model.GetThreads(date_min, date_max);
		View.SeeThreads(thrs, false);

	}
}
