package org.apache.hadoop.java.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.hadoop.hive.ql.parse.HiveParser.selectStatement_return;

/**   
* @Title: Setup.java 
* @Package showcase.dw.glbg.thread 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年10月11日 下午6:43:30   
*/
class MyProducer implements Runnable {
	   private final BlockingQueue queue;
	   MyProducer(BlockingQueue q) { queue = q; }
	   public void run() {
	     try {
	       while (true) { queue.put(produce()); }
	     } catch (InterruptedException ex) { 
          }
	   }
	   Object produce() {return null; }
	 }

class MyConsumer implements Runnable {
	   private final BlockingQueue queue;
	   MyConsumer(BlockingQueue q) { queue = q; }
	   public void run() {
	     try {
	       while (true) { consume(queue.take()); }
	     } catch (InterruptedException ex) { }
	   }
	   void consume(Object x) { }
	 }

public class Setup {
	public static void main(String[] args) {
	     BlockingQueue q = new ArrayBlockingQueue(3);
	     MyProducer p = new MyProducer(q);
	     MyConsumer c1 = new MyConsumer(q);
	     MyConsumer c2 = new MyConsumer(q);
	     new Thread(p).start();
	     new Thread(c1).start();
	     new Thread(c2).start();
	   }
	 }
