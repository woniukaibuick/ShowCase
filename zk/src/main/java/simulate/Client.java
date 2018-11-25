package org.apache.hadoop.zk.simulate;

import java.io.IOException;

import org.apache.zookeeper.Op;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Client implements Watcher{
	private ZooKeeper zk;
	private String hostPort;

	void startZK() throws IOException{
		zk = new ZooKeeper(hostPort, 15000, this);
	}
	public Client(String hostPort) {
		super();
		this.hostPort = hostPort;
	}

	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
	Op deleteNode(){
		return null;
		
	}
	String queryCommand(String command){
		while (true) {
			
		}
	}

}
