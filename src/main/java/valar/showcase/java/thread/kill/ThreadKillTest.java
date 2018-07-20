package valar.showcase.java.thread.kill;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**   
* @Title: ThreadKillTest.java 
* @Package showcase.dw.glbg.thread.kill 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年10月11日 下午10:35:45   
*/
public class ThreadKillTest {

	public static void main(String[] args) throws InterruptedException {
//		 testKillTHreadByException();
//		 testKillThreadByPassPara();
		 testCancelFutureTask();
	}
	private static void testCancelFutureTask() {
		 ExecutorService exec = Executors.newCachedThreadPool();
		 CancelFutureTaskThread cftt = new CancelFutureTaskThread();
		 Future<?>  mytask = exec.submit(cftt);
//		 System.out.println(task.isDone());
//		 task.cancel(true);
//		 mytask.cancel(true);
		 exec.shutdown();
	}
	/**
	 * output:
		target status:RUNNABLE
		Exception in thread "Thread-0" java.lang.ArithmeticException: / by zero
			at showcase.dw.glbg.thread.kill.ExceptionThreadKiller.run(ThreadKillTest.java:42)
			at java.lang.Thread.run(Unknown Source)
		target status:TERMINATED
	 * @author gongxuesong
	 *
	 */
	private static void testKillTHreadByException() {
		Thread t = new Thread(new ExceptionThreadKiller());
		t.start();
		System.out.println("target status:"+t.getState());	
		for(;;) {
			if(t.getState().equals(Thread.State.TERMINATED))
				System.err.println("target status:"+t.getState());	
		}
	}
	/**
	 * CancelableThread output:
		target status:NEW
		target status:RUNNABLE
		Thread-0:runing
		Thread-0:runing
		Thread-0:runing...
		target status:TERMINATED
	 */
	private static void testKillThreadByPassPara() throws InterruptedException {
		CancelableThread run = new CancelableThread();
		Thread target = new Thread(run);
		System.err.println("target status:"+target.getState());	
		target.start();
		System.err.println("target status:"+target.getState());	
		target.sleep(1);
		run.setCancel(true);
		for(;;) {
			if(target.getState().equals(Thread.State.TERMINATED))
				System.err.println("target status:"+target.getState());			
		}
	}
}

class ExceptionThreadKiller implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int a = 0;
		System.out.println(1/a);
		
	}
	
}

class CancelableThread implements Runnable{

	private boolean cancel;

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!isCancel()) {
			System.out.println(Thread.currentThread().getName()+":runing");
		}
	}
	
}

class CancelFutureTaskThread implements Callable<String>{

	private boolean flag = true;
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		try {
			while (flag) {
				System.err.println("runing");
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
}

