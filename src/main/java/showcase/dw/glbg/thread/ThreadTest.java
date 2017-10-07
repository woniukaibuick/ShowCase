package showcase.dw.glbg.thread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

import junit.framework.Assert;



/**   
* @Title: ThreadTest.java 
* @Package showcase.dw.glbg.thread 
* @Description: TODO(description) 
* @author gongxuesong@globalegrow.com   
* @date 2017年10月6日 下午9:33:19   
*/
public class ThreadTest {
	
	public static void main(String[] args) {
		//1.临界资源
		ThreadTest threadTest = new ThreadTest();
		threadTest.testThreadsCollide();
		//2.协作
		Counting counting = new Counting();
		Thread evenThread = new Thread(new EvenCounting(counting));
		Thread oddThread = new Thread(new OddCounting(counting));
		evenThread.start();
		oddThread.start();
		
		
	}
	
	/**
	 * share<br>
	 * show many kinds of methods to simulate  
	 */
	@Test
	public void testThreadsCollide() {
		EvenChecker.test(new EvenGenerator());
		Assert.assertTrue(true);
	}
	
	@Test
	public void testAccessWithoutLock() {
		
	}
	
	@Test
	public void testLock() {
		Lock lock = new ReentrantLock();
	}
	
	@Test
	public void testRunnable() {
		LiftOff liftOff = new LiftOff();
		liftOff.run();
	}
	/**
	 * join can be interrupted
	 * @throws InterruptedException
	 */
	@Test
	public void testThreadJoin() throws InterruptedException {
		ThreadOne t1 = new ThreadOne();
		ThreadTwo t2 = new ThreadTwo(t1);
//		ThreadThree t3 = new ThreadThree(t2);
//		t2.interrupt();
	}
	@Test
	public void testExecutorService() throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newCachedThreadPool();
		ArrayList<Future<String>> list = new ArrayList<Future<String>>();
		for (int i = 0; i < 5; i++) {
			list.add(es.submit(new LiftOn()));
		}
		for(Future<String> future : list) {
			System.err.println("status:"+future.isDone());
			System.err.println(future.get());
		}
	}
}

class LiftOff implements Runnable{
	
	protected int countDown = 10;
	private static int taskCount = 0;
	private final int id = taskCount++;
    
	public LiftOff() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public LiftOff(int countDown) {
		super();
		this.countDown = countDown;
	}
    public String getStatus() {
    	return "#"+id+"("+(countDown>0?	countDown :"liftOff!")+".)";
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (countDown-->0) {
			System.err.println(getStatus());
			Thread.yield();
		}
	}
	
}

class LiftOn implements Callable<String>{
    private static int priority = 1;
	private static int countDown = 0;
	private final int id = countDown++;
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		Thread.currentThread().setPriority(priority++);
//		TimeUnit.MICROSECONDS.sleep(1000);
		int i = 10000;
		double total = 0;
		while(i>0) {
			total = Math.E+Math.PI+i;
			i--;
		}
		return "id:"+id;
	}
	
}
class ThreadOne extends Thread{
	
	

	public ThreadOne() {
		super();
		// TODO Auto-generated constructor stub
		start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			TimeUnit.SECONDS.sleep(10);
			System.out.println("thread one do something@!");
			System.err.println("thread one catch:Thread.currentThread().isInterrupted:"+Thread.currentThread().isInterrupted());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("thread one Thread.currentThread().isInterrupted:"+Thread.currentThread().isInterrupted());
			System.err.println("thread one ecounter InterruptedException while sleeping!");
		}
	}
	
}
class ThreadTwo extends Thread{
    private ThreadOne threaOne;
    
	public ThreadTwo(ThreadOne threaOne) {
		super();
		this.threaOne = threaOne;
		start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			threaOne.join();
			System.err.println("thread two catch:Thread.currentThread().isInterrupted:"+Thread.currentThread().isInterrupted());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("thread twoThread.currentThread().isInterrupted:"+Thread.currentThread().isInterrupted());
			System.err.println("thread one join meet exception!");
		}
		System.out.println("thread two do something");
	}
	
}


class ThreadThree extends Thread{
	private ThreadTwo t;
	public ThreadThree(ThreadTwo t) {
		super();
		// TODO Auto-generated constructor stub
		this.t = t;
		start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("thread three do something");
	}
}

class MySingleton {  
    
    //使用volatile关键字保其可见性  
    volatile private static MySingleton instance = null;  
      
    private MySingleton(){}  
       
    public static MySingleton getInstance() {  
        try {    
            if(instance != null){//懒汉式   
                  
            }else{  
                //创建实例之前可能会有一些准备性的耗时工作   
                Thread.sleep(300);  
                synchronized (MySingleton.class) {  
                    if(instance == null){//二次检查  
                        instance = new MySingleton();  
                    }  
                }  
            }   
        } catch (InterruptedException e) {   
            e.printStackTrace();  
        }  
        return instance;  
    }  
}  