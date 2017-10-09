package showcase.dw.glbg.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;


/**   
* @Title: CyclicBarrierDemo.java 
* @Package showcase.dw.glbg.thread 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年10月8日 上午9:08:59   
*/
public class CyclicBarrierDemo {
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(3,new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.err.println(Thread.currentThread().getName()+":line operation!");
			}
		});
		for (int i = 0; i < 3; i++) {
			executorService.submit(new Sporter(cyclicBarrier));
		}
		TimeUnit.SECONDS.sleep(3);
		executorService.submit(new Sporter(cyclicBarrier));
//		executorService.submit(new Rewards(cyclicBarrier));
	}

	@Test
	public void testCyclicBarrier() {
		
	}
}

class Sporter implements Runnable{
	private CyclicBarrier cyclicBarrier;
	

	public Sporter(CyclicBarrier cyclicBarrier) {
		super();
		this.cyclicBarrier = cyclicBarrier;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.err.println(Thread.currentThread().getName()+":sport runing started!");
		try {
			TimeUnit.SECONDS.sleep(2);
			cyclicBarrier.await(2, TimeUnit.SECONDS);
			System.err.println(Thread.currentThread().getName()+":sport runing completed!");
		} catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(Thread.currentThread().getName()+":waiting for next step");
		
	}
	
}
class Rewards implements Runnable{
	private CyclicBarrier cyclicBarrier;
	public Rewards(CyclicBarrier cyclicBarrier) {
		super();
		this.cyclicBarrier = cyclicBarrier;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.err.println("after line operation:");
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(Thread.currentThread().getName()+":rewards");
	}
	
}