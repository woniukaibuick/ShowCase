package org.apache.hadoop.zk;

import java.io.IOException;

import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * @Description 
 * @Author  gongxuesong
 * @Date 2018年7月5日 上午9:29:26
 * @Version V1.0
 */
public class ZkSyncTest {
	
	public static void main(String[] args)   {
		
		
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper("127.0.0.1:2181", 2000, new Watcher() {
				
				public void process(WatchedEvent paramWatchedEvent) {
					// TODO Auto-generated method stub
					System.out.println("WatchedEvent:"+paramWatchedEvent.getType());
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		zk.create("/pengrui/data11", "data11".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new StringCallback() {
			
			public void processResult(int rc, String path, Object ctx, String name) {
				// TODO Auto-generated method stub
				System.out.println(rc);
			}
		}, null);
	}

}
