package valar.showcase.java.thread;

import java.util.concurrent.TimeUnit;

/**   
* @Title: CountingCooperate.java 
* @Package showcase.dw.glbg.thread 
* @Description: TODO(description) 
* @date 2017年10月7日 下午6:09:27   
*/
public class CountingCooperate {

	public static void main(String[] args) throws InterruptedException {
		Counting counting = new Counting();
		Thread evenThread = new Thread(new EvenCounting(counting));
		Thread oddThread = new Thread(new OddCounting(counting));
		evenThread.start();
		oddThread.start();
//		while (!counting.isCompleted()) {
//			System.err.println(evenThread.holdsLock(counting));
//			TimeUnit.SECONDS.sleep(5);
//		}
		
	}

}

class Counting {
	private int countNum = 0;

	public synchronized void genNum() {
		countNum++;
		notifyAll();
	}
	public synchronized void waitForGenEven() throws InterruptedException {
		while (countNum %2 ==0 ) {
			wait();
		}
	}
	public synchronized void waitForGenOdd() throws InterruptedException {
		while (countNum %2 !=0 ) {
			wait();
		}
	}
	public int getCountNum(){return countNum;}
	public boolean isCompleted() {return countNum >= 20;};
	
	
}

class EvenCounting implements Runnable{

	private Counting counting;
	
	public EvenCounting(Counting counting) {
		super();
		this.counting = counting;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.err.println();
		    while (!Thread.interrupted() && !counting.isCompleted()) {
				try {
					counting.waitForGenEven();
					TimeUnit.SECONDS.sleep(5);
					counting.genNum();
					System.err.println("EvenCounting:"+counting.getCountNum());
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		
	}
	
}
class OddCounting implements Runnable{
	private Counting counting;
	public OddCounting(Counting counting) {
		super();
		this.counting = counting;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println();
		while (!Thread.interrupted() && !counting.isCompleted()) {
			try {
				counting.waitForGenOdd();
				TimeUnit.SECONDS.sleep(5);
				counting.genNum();
				System.out.println("OddCounting:"+counting.getCountNum());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}