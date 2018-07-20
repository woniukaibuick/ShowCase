package valar.showcase.zk;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**   
* @Title: Master.java 
* @Package showcase.dw.glbg.zk 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年11月30日 下午7:25:41   
*/
public class Master implements Watcher{
	private ZooKeeper zk;
	private String hostPort;
	private String serverID = Integer.toHexString(new Random().nextInt());
	private boolean isLeader;

	
	public Master(String hostPort) {
		super();
		this.hostPort = hostPort;
	}
    
	void startZK() throws IOException {
		zk = new ZooKeeper(hostPort,15000,this);
	}
	
	void stopZK() throws InterruptedException {
		zk.close();
	}
	
	void runForMaster()  {
		while (true) {
			try {
				zk.create("/master", serverID.getBytes(),Ids.OPEN_ACL_UNSAFE , CreateMode.EPHEMERAL);
				isLeader = true;
				break;
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				isLeader = false;
				break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(checkMaster()) break;
		
		}
		
		
	}
	
	
	void createSync() {
		
		StringCallback sc = new StringCallback() {
			
			@Override
			public void processResult(int rc, String path, Object ctx, String name) {
				// TODO Auto-generated method stub
				switch (Code.get(rc)) {
				case CONNECTIONLOSS:
					checkMaster();
					return ;
					
				case OK:
					isLeader = true;
					break;

				default:
					isLeader = false;
					break;
				}
				System.out.println("isLeader:"+isLeader);
			}
		};
		
		zk.create("/master", serverID.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,sc, null);
	}
	
	boolean checkMaster() {
		while (true) {
			Stat stat = new Stat();
			byte[] data;
			try {
				data = zk.getData("/master", false, stat);
				isLeader = new String(data).equals(serverID);
				return true;
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				return false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		System.err.println("event:"+event);
		
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		Master master = new Master("localhost:2181");
		master.startZK();
		
		master.runForMaster();
		master.createSync();
		if (master.isLeader) {
			System.err.println("i am leader!");
			Thread.sleep(60000);
		}else {
			System.err.println("someone else is the leader!");
		}
		
//		Thread.sleep(60000);
		
		master.stopZK();
		
		
		
	}

}
