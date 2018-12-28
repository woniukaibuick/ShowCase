
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
	/**
	 * 测试chroot特性，connectString后面新增了应用路经，zk连接后所有操作路经都是相对chroot来说的
	 */
	private String connectString = "127.0.0.1:2181/app/junit";
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
	
	@Test
	public void testChroot() throws KeeperException, InterruptedException{
		client.addAuthInfo("digest", "foo:true".getBytes());
		client.create("/testChroot", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	}
	
	/**
	 * 左移的规则只记住一点：丢弃最高位，0补最低位
	 * 右移的规则只记住一点：符号位不变，左边补上符号位
                     无符号右移的规则只记住一点：忽略了符号位扩展，0补最高位
	 */
	@Test
	public void testSessionTrackerImplInitializeNextSession(){
		long currentTimeMillis= -1545818009343l;
		System.out.println(Long.toBinaryString(currentTimeMillis));
		
		long a = currentTimeMillis << 24;
		System.out.println(Long.toBinaryString(a));
		;
		long b = a >> 8;
		System.out.println(Long.toBinaryString(b));
		
		long c =  a >>> 8;
		System.out.println(Long.toBinaryString(c));
		
		//output
		/*
		1111111111111111111111101001100000010110000100010001000100000001
		1001100000010110000100010001000100000001000000000000000000000000
		1111111110011000000101100001000100010001000000010000000000000000
		0000000010011000000101100001000100010001000000010000000000000000
		*/
	}
/*    public static long initializeNextSession(long id) {
        long nextSid = 0;
        nextSid = (System.currentTimeMillis() << 24) >> 8;
        nextSid =  nextSid | (id <<56);
        return nextSid;
    }*/

}
