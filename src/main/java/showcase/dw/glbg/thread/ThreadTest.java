package showcase.dw.glbg.thread;

import java.util.ArrayList;
import java.util.Timer;
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
* @author gongxuesong 
* @date 2017年10月6日 下午9:33:19   
*/
public class ThreadTest {
	
	public static void main(String[] args) throws InterruptedException {
		ThreadTest threadTest = new ThreadTest();
		//如何在Java中实现线程？
		//用Runnable还是Thread？
		//Java中Runnable和Callable有什么不同？
		threadTest.testDifference();
		//Java中CyclicBarrier 和 CountDownLatch有什么不同？
		CountDownLatchDemo cdd;
		//什么是线程安全？
		//Java中如何停止一个线程？
		//异常逃逸 
		ExceptionThread exceptionThread;
		
		//临界资源
		//ThreadLocal变量？
		//volatile防止字撕裂,volatile 变量和 atomic 变量有什么不同？
		//原子类 AtomicInteger,AtomicLong,AtomicReference,原子操作需不需要同步控制，利用原子操作写无锁代码
		threadTest.testThreadsCollide();
		
		//synchronized(this) or synchronized(obejct)
		//同步控制块还是整个方法 synchronized methods or block
		SyncObject so;
		
		
		
		//如何控制多个线程执行顺序
		//优先级设置，后台线程设置，休眠，join
		threadTest.testThreadJoin();
		threadTest.testSetDaemonAndPriority();
		
		//sleep,yield，wait谁释放了锁，谁没有？		
		//为什么wait, notify 和 notifyAll这些方法不在thread类里面？		
		//怎么检测一个线程是否拥有锁？
		
		//线程安全的单例模式
		MySingleton mySingleton;
				
		
		//协作,共享数据等
		//notify 和 notifyAll有什么区别？
		Counting counting = new Counting();
		Thread evenThread = new Thread(new EvenCounting(counting));
		Thread oddThread = new Thread(new OddCounting(counting));
		evenThread.start();
		oddThread.start();
		
		
		Timer timer;
		
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
	public void testDifference() {
		Runnable runnable;
		Callable callable;
		Thread   thread;
	}
	
	@Test
	public void testSetDaemonAndPriority() {
		ThreadOne t1 = new ThreadOne();
		ThreadTwo t2 = new ThreadTwo(t1);
		t1.setDaemon(true);
		t1.setPriority(Thread.MAX_PRIORITY);
		
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
                  return instance;
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