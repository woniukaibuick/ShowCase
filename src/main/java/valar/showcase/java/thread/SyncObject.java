package valar.showcase.java.thread; //: concurrency/SyncObject.java
// Synchronizing on another object.
import static valar.showcase.util.Print.*;

import java.util.concurrent.TimeUnit;

class DualSynch {
  private Object syncObject = new Object();
  public synchronized void f() {
    for(int i = 0; i < 5; i++) {
      print("f()");
      Thread.yield();
    }
  }
  public void g() {
//	  synchronized(this) {  
    synchronized(syncObject) {
      for(int i = 0; i < 5; i++) {
        print("g()");
        Thread.yield();
      }
    }
  }
}

public class SyncObject {
  public static void main(String[] args) throws InterruptedException {
    final DualSynch ds = new DualSynch();
    new Thread() {
      public void run() {
        ds.f();
      }
    }.start();
//    TimeUnit.SECONDS.sleep(3);
    ds.g();
  }
} 
/* Output: (Sample) if use syncObject
g()
f()
g()
f()
g()
f()
g()
f()
g()
f()
*///:~


/**
 * if use this
g()
g()
g()
g()
g()
f()
f()
f()
f()
f()

 */
