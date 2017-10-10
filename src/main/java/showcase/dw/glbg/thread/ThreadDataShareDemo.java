package showcase.dw.glbg.thread;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @Title: ThreadDataShareDemo.java
 * @Package showcase.dw.glbg.thread
 * @Description: TODO(共享内存机制和消息通信机制示例)
 * @author gong_xuesong@126.com
 * @date 2017年10月8日 下午2:39:35
 */
public class ThreadDataShareDemo {

	public static void main(String[] args) throws IOException {
//		Acount Acount = new Acount(0);
//		Bank b = new Bank(Acount);
//		Consumer c = new Consumer(Acount);
//		new Thread(b).start();
//		new Thread(c).start();
		
		
	    PipedInputStream pin = new PipedInputStream();  
	    PipedOutputStream pout = new PipedOutputStream();  
	    pin.connect(pout); 
	      
	    ReadThread readTh   = new ReadThread(pin);  
	    WriteThread writeTh = new WriteThread(pout);  
	    new Thread(readTh).start();  
	    new Thread(writeTh).start();  
	}
} 
class ReadThread implements Runnable  
{  
  private PipedInputStream pin;  
  ReadThread(PipedInputStream pin)   //  
  {  
    this.pin=pin;  
  }  
    
  public void run() //由于必须要覆盖run方法,所以这里不能抛,只能try  
  {  
    try  
    {  
      sop("R:读取前没有数据,阻塞中...等待数据传过来再输出到控制台...");  
      byte[] buf = new byte[1024];  
      int len = pin.read(buf);  //read阻塞  
      sop("R:读取数据成功,阻塞解除...");  
        
      String s= new String(buf,0,len);  
      sop(s);    //将读取的数据流用字符串以字符串打印出来  
      pin.close();       
    }  
    catch(Exception e)  
    {  
      throw new RuntimeException("R:管道读取流失败!");  
    }     
  }  
    
  public static void sop(Object obj) //打印  
  {  
    System.out.println(obj);  
  }  
}  
  
class WriteThread implements Runnable  
{  
  private PipedOutputStream pout;  
  WriteThread(PipedOutputStream pout)  
  {  
    this.pout=  pout;  
  }  
    
  public void run()  
  {  
    try  
    {  
      sop("W:开始将数据写入:但等个5秒让我们观察...");  
      Thread.sleep(5000);  //释放cpu执行权5秒  
      pout.write("W: writePiped 数据...".getBytes());  //管道输出流  
      pout.close();  
    }  
    catch(Exception e)  
    {  
      throw new RuntimeException("W:WriteThread写入失败...");  
    }  
  }  
    
  public static void sop(Object obj) //打印  
  {  
    System.out.println(obj);  
  }  
}  
class Acount {

	private int money;

	public Acount(int money) {
		this.money = money;
	}

	public synchronized void getMoney(int money) {
		// 注意这个地方必须用while循环，因为即便再存入钱也有可能比取的要少
		while (this.money < money) {
			System.out.println("取款：" + money + " 余额：" + this.money + " 余额不足，正在等待存款......");
			try {
				wait();
			} catch (Exception e) {
			}
		}
		this.money = this.money - money;
		System.out.println("取出：" + money + " 还剩余：" + this.money);

	}

	public synchronized void setMoney(int money) {

		try {
			Thread.sleep(10);
		} catch (Exception e) {
		}
		this.money = this.money + money;
		System.out.println("新存入：" + money + " 共计：" + this.money);
		notify();
	}
}

// 存款类
class Bank implements Runnable {
	Acount Acount;

	public Bank(Acount Acount) {
		this.Acount = Acount;
	}

	public void run() {
		while (true) {
			int temp = (int) (Math.random() * 1000);
			Acount.setMoney(temp);
		}
	}

}

// 取款类
class Consumer implements Runnable {
	Acount Acount;

	public Consumer(Acount Acount) {
		this.Acount = Acount;
	}

	public void run() {
		while (true) {
			int temp = (int) (Math.random() * 1000);
			Acount.getMoney(temp);
		}
	}
}
