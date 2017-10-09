package showcase.dw.glbg.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**   
* @Title: SemaphoreSampleDemo.java 
* @Package showcase.dw.glbg.thread 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年10月8日 下午1:45:16   
*/
public class SemaphoreSampleDemo {
	public static void main(String[] args) {
//		int N = 8; // 工人数
//		Semaphore semaphore = new Semaphore(5); // 机器数目
//		for (int i = 0; i < N; i++)
//			new Worker(semaphore).start();
		
//        SemaphoreThread test = new SemaphoreThread();
//        test.useThread();
	}

	static class Worker extends Thread {
		private Semaphore semaphore;

		public Worker(Semaphore semaphore) {
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			String TAG = Thread.currentThread().getName();
			try {
				semaphore.acquire();
//				System.out.println(TAG+"_availablePermits_"+semaphore.availablePermits());
				System.err.println(TAG+ "占用一个机器在生产...");
				Thread.sleep(2000);
				System.out.println(TAG+ "释放出机器");
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.err.println(TAG+" via exception!");
			}
			
		}
	}
	
}
class SemaphoreThread {
    private int a = 0;

    /**
     * 银行存钱类
     */
    class Bank {
        private int account = 100;

        public int getAccount() {
            return account;
        }

        public void save(int money) {
            account += money;
        }
    }

    /**
     * 线程执行类，每次存10块钱
     */
    class NewThread implements Runnable {
        private Bank bank;
        private Semaphore semaphore;

        public NewThread(Bank bank, Semaphore semaphore) {
            this.bank = bank;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            int b = a++;
            if (semaphore.availablePermits() > 0) {
                System.out.println("线程" + b + "启动，进入银行,有位置立即去存钱");
            } else {
                System.out.println("线程" + b + "启动，进入银行,无位置，去排队等待等待");
            }
            try {
                semaphore.acquire();
                bank.save(10);
                System.out.println(b + "账户余额为：" + bank.getAccount());
                Thread.sleep(1000);
                System.out.println("线程" + b + "存钱完毕，离开银行");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 建立线程，调用内部类，开始存钱
     */
    public void useThread() {
        Bank bank = new Bank();
        Semaphore semaphore = new Semaphore(2);
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            es.submit(new Thread(new NewThread(bank, semaphore)));
        }
        es.shutdown();
        // 从信号量中获取两个许可，并且在获得许可之前，一直将线程阻塞
        semaphore.acquireUninterruptibly(2);
        System.out.println("到点了，工作人员要吃饭了");
        // 释放两个许可，并将其返回给信号量
        semaphore.release(2);
    }
}
