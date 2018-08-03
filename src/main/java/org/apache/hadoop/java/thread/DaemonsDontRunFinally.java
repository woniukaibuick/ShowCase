package org.apache.hadoop.java.thread; //: concurrency/DaemonsDontRunFinally.java
// Daemon threads don't run the finally clause
import java.util.concurrent.*;

import static org.apache.hadoop.util.Print.*;

class ADaemon implements Runnable {
  public void run() {
    try {
      print(Thread.currentThread().getName()+" Starting ADaemon");
      TimeUnit.SECONDS.sleep(1);
    } catch(InterruptedException e) {
      print("Exiting via InterruptedException");
    } finally {
      print(Thread.currentThread().getName()+" This should always run?");
    }
  }
}

public class DaemonsDontRunFinally {
  public static void main(String[] args) throws Exception {
    Thread t = new Thread(new ADaemon());
    t.setName("thread one");
    Thread tt = new Thread(new ADaemon());
    tt.setName("thread two"); 
    tt.start(); 
    t.setDaemon(true);
    t.start();
  }
} /* Output:
Starting ADaemon
*///:~
