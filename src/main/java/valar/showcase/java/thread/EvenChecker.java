package valar.showcase.java.thread; //: concurrency/EvenChecker.java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {
  private EnhancedIntGenerator generator;
  private final int id;
  public EvenChecker(EnhancedIntGenerator g, int ident) {
    generator = g;
    id = ident;
  }
  public void run() {
    while(!generator.isCanceled()) {
//      int val = generator.next();
//      int val = generator.synchronizedNext();
//    	int val = generator.lockedNext();
//    	int val = generator.atomicNext();
//    	int val = generator.lockedNextAndReturnInAvance();
    	int val = generator.atomicNextStepByStep();
//    	int val = generator.threadLocalNext();
      if(val % 2 != 0) {
        System.out.println(val + " not even!");
        generator.cancel(); // Cancels all EvenCheckers
      }
    }
  }
  // Test any type of IntGenerator:
  public static void test(EnhancedIntGenerator gp, int count) {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < count; i++)
      exec.execute(new EvenChecker(gp, i));
    exec.shutdown();
  }
  // Default value for count:
  public static void test(EnhancedIntGenerator gp) {
    test(gp, 10);
  }
} ///:~
