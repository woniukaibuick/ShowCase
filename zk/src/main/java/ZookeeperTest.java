
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * @Description 
 * @Author  gongxuesong
 * @Date 2018年7月4日 下午6:48:28
 * @Version V1.0
 */
public class ZookeeperTest {
	private ZooKeeper client;
	private String connectString = "127.0.0.1:2181";
	private int sessionTimeout = 2000;
	@Before
	public void init() throws IOException {
	client = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println(event.getPath()+":"+event.getType());
				
			}
		});	
	client.addAuthInfo("zk","foo:zk".getBytes());
	}
	
	@Test
	public void testNormally() throws KeeperException, InterruptedException{
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
	
	@Test
	public void testAsyncCall(){
		client.create("/pengrui", "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new StringCallback() {
			
			@Override
			public void processResult(int rc, String path, Object ctx, String name) {
				// TODO Auto-generated method stub
				System.out.println("rc code:"+Code.get(rc));;
			}
		}, null);
	}
	
	@Test
	public void testACLApi() throws NoSuchAlgorithmException{
		//ACL diest模式时候生成暗码
		System.out.println(DigestAuthenticationProvider.generateDigest("foo:zk-book"));
	}
	
	@Test
	public void testACL() throws IOException, KeeperException, InterruptedException{
		String aclPath = "/zk-book";
		ZooKeeper client1 = new ZooKeeper(connectString, sessionTimeout, null);
		client1.addAuthInfo("digest", "foo:true".getBytes());
		client1.create(aclPath, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
		//注意 这里权限用得是CREATOR_ALL_ACL，如果是用OPEN_ACL_UNSAFE，那么下面另外两个客户端依然可以连接读取数据
		
		ZooKeeper client2 = new ZooKeeper(connectString, sessionTimeout, null);
		client2.addAuthInfo("digest", "foo:zk-book".getBytes());
		byte[] data = client2.getData(aclPath, false, null);
		System.out.println("client with foo:zk-book auth get /zk-book data:"+new String(data));
		//org.apache.zookeeper.KeeperException$NoAuthException: KeeperErrorCode = NoAuth for /zk-book
		
		ZooKeeper client3 = new ZooKeeper(connectString, sessionTimeout, null);
		client3.addAuthInfo("digest", "foo:false".getBytes());
		byte[] data3 = client3.getData(aclPath, false, null);
		System.out.println("client with foo:false auth get /zk-book data:"+new String(data3));
		//org.apache.zookeeper.KeeperException$NoAuthException: KeeperErrorCode = NoAuth for /zk-book
	}

}
