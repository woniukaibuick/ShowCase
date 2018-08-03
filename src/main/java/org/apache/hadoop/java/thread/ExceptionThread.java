package org.apache.hadoop.java.thread; //: concurrency/ExceptionThread.java
// {ThrowsException}
import java.util.concurrent.*;

public class ExceptionThread implements Runnable {
  public void run() {
    throw new RuntimeException();
  }
  public static void main(String[] args) {
//	  Thread.setDefaultUncaughtExceptionHandler(new UserDefinedUncaughtExceptionHandler());
	  testCaptureEscapedException();
  }
  
  public static void testCaptureEscapedException(){
	  try {
		ExecutorService es = Executors.newCachedThreadPool();
		  es.execute(new ExceptionThread());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.err.println("well,captured exception!");
		e.printStackTrace();
	}
  }
  
  public static void testEscapedException(){
		ExecutorService es = Executors.newCachedThreadPool();
		  es.execute(new ExceptionThread());
  }
} 


class UserDefinedUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// TODO Auto-generated method stub
		System.err.println("oops,got exception:"+e.getMessage());
	}
	
}
