package Utils;

import java.io.IOException;

public class ThreadTestMain {

	public static void main (String[] args) throws IOException {
		
		ThreadTest t = new ThreadTest();
		
//		new Thread(new ThreadTest("rowPembeli0", "rowKue0", 1)).start();
//
//		new Thread(new ThreadTest("rowPembeli1", "rowKue0", 2)).start();
//
//		new Thread(new ThreadTest("rowPembeli2", "rowKue0", 3)).start();
//
//		new Thread(new ThreadTest("rowPembeli3", "rowKue0", 4)).start();
//
//		new Thread(new ThreadTest("rowPembeli4", "rowKue0", 5)).start();
//
//		new Thread(new ThreadTest("rowPembeli5", "rowKue0", 6)).start();
//
//		new Thread(new ThreadTest("rowPembeli6", "rowKue0", 7)).start();
//		
//		new Thread(new ThreadTest("rowPembeli7", "rowKue0", 8)).start();
		
		new Thread(new Scheduler.DailyJob()).start();
	}
	
	
}
