package org.apache.hadoop.java.thread; 
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo2{
	public static void main(String[] args) {
		final CountDownLatch cdl = new CountDownLatch(5);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.submit(new CompletedJob(cdl));
		for(int i = 0; i < 5; i ++) {
			exec.submit(new CountDownJob(cdl));
		}
		exec.shutdown();
	}	
}
class CountDownJob implements Runnable{
	private CountDownLatch cdl;
	public CountDownJob(CountDownLatch cdl) {
		super();
		this.cdl = cdl;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			TimeUnit.SECONDS.sleep(2);
			cdl.countDown();
			System.out.println(Thread.currentThread().getName()+" completed!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
}
class CompletedJob implements Runnable{
	private CountDownLatch cdl;
	public CompletedJob(CountDownLatch cdl) {
		super();
		this.cdl = cdl;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			cdl.await();
			TimeUnit.SECONDS.sleep(2);
			System.err.println("all jobs were completed!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
