
import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @Description 
 * @Author  gongxuesong
 * @Date 2018年7月4日 下午6:48:28
 * @Version V1.0
 */
public class ZookeeperTest {
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		
	 ZooKeeper client = new ZooKeeper("127.0.0.1:2181", 2000, new Watcher() {
			
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println(event.getPath()+":"+event.getType());
				
			}
		});
		
		String create = client.create("/pengrui/node5", "company".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(create);
		Stat stat = null;// new Stat();
		client.getACL("/pengrui", stat);
		System.out.println(stat);
		
		
		client.getChildren("/pengrui", true);
		
		client.delete("/pengrui/node5", -1);
		stat = new Stat();
		byte[] bytes = client.getData("/pengrui/node2", false, stat);
		System.out.println(":"+new String(bytes));
		
		
		
		
		
		
	}

}
