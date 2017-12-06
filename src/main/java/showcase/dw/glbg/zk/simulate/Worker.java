package showcase.dw.glbg.zk.simulate;

import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooKeeper;

public class Worker implements Watcher{

	private ZooKeeper zk;
	private String hostPort;
	private Random random = new Random();
    private String serverID = Integer.toHexString(random.nextInt());
    private String status;
    
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		updateStatus(status);
	}

	public Worker(String hostPort) {
		super();
		this.hostPort = hostPort;
	}

	void startZK() throws IOException{
		zk = new ZooKeeper(hostPort, 15000, this);
	}
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Worker ServerID:"+hostPort+" "+serverID+" processing!");
	}
	void register() throws KeeperException, InterruptedException{
		StringCallback sc = new StringCallback() {
			
			public void processResult(int rc, String path, Object ctx, String name) {
				// TODO Auto-generated method stub
				switch (Code.get(rc)) {
				case OK:
					System.out.println("node register succeed@!");
					break;
					
				case NODEEXISTS:
					System.out.println("node already registered@!");
					break;
					
				case CONNECTIONLOSS:
					System.out.println("connection loss, will try again@!");
					try {
						register();
					} catch (KeeperException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					System.out.println("create register failed:"+Code.get(rc));
					break;
			}
		};
		
	};
		zk.create("/workers/worker-"+serverID, "IDle".getBytes(), Ids.READ_ACL_UNSAFE, CreateMode.EPHEMERAL,sc,null);
	}
	
	
	synchronized private void updateStatus(String status){
		if (status == this.status) {
			StatCallback sc = new StatCallback() {
				
				public void processResult(int rc, String path, Object ctx, Stat stat) {
					// TODO Auto-generated method stub
					switch (Code.get(rc)) {
					case CONNECTIONLOSS:
						updateStatus((String)ctx);
						break;

					default:
						break;
					}
				}
			};
			zk.setData("/workers/"+serverID, status.getBytes(), -1,sc , status);
		}
	}

}
