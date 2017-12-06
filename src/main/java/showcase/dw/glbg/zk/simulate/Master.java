package showcase.dw.glbg.zk.simulate;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;


public class Master implements Watcher {
	private Random random = new Random();
    private String serverID = Integer.toHexString(random.nextInt());
	private ZooKeeper zk;
	private String hostPort;
	private boolean isLeader;
	
	public Master(String hostPort) {
		super();
		this.hostPort = hostPort;
	}
	
	void startZK() throws IOException{
		zk = new ZooKeeper(hostPort,15000,this);
	}
	
	void stopZK() throws InterruptedException{
		zk.close();
	}
	
	void runForMaster(){
		StringCallback sc = new StringCallback() {
			
			public void processResult(int arg0, String arg1, Object arg2, String arg3) {
				// TODO Auto-generated method stub
				System.err.println("StringCallback");
				switch (Code.get(arg0)) {
				case CONNECTIONLOSS:
					System.out.println("StringCallback:CONNECTIONLOSS");
					break;
				case OK:
					isLeader = true;
					break;
 
				default:
					isLeader = false;
					System.out.println("StringCallback:"+Code.get(arg0));
					break;
				}
				System.out.println("serverID:"+serverID+",isLeader:"+isLeader);
			}
		};
		zk.create("/master", serverID.getBytes(), Ids.READ_ACL_UNSAFE, CreateMode.EPHEMERAL, sc, null);
	}

	public void process(WatchedEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0);
	}

}
