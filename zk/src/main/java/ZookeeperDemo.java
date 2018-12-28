
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;
/**
 * DESC: ZooKeeper command
	ZooKeeper -server host:port cmd args
	stat path [watch]
	set path data [version]
	ls path [watch]
	delquota [-n|-b] path
	ls2 path [watch]
	setAcl path acl
	setquota -n|-b val path
	history
	redo cmdno
	printwatches on|off
	delete path [version]
	sync path
	listquota path
	get path [watch]
	create [-s] [-e] path data acl
	addauth scheme auth
	quit
	getAcl path
	close
	connect host:port
 * 
 */

/**
 *
 * 
        CreateMode.EPHEMERAL;<br>
        CreateMode.EPHEMERAL_SEQUENTIAL;<br>
        CreateMode.PERSISTENT;<br>
        CreateMode.PERSISTENT_SEQUENTIAL;<br>
          Ids.OPEN_ACL_UNSAFE;<br>
          Ids.READ_ACL_UNSAFE;<br>
          Ids.CREATOR_ALL_ACL;<br>
 * @author gongxuesong
 *
 */
public class ZookeeperDemo {
	private CuratorFramework client;
	private String basePath = "/valar";
	private String connectString = "127.0.0.1:2181";
	/**
	 * 每一个zk集群只需要一个CuratorFramework 实例
	 * RetryPolicy有几种类型 RetryUntilElapsed  RetryOneTime RetryNTimes
	 * ExponentialBackoffRetry BoundedExponentialBackoffRetry
	 */
	@Before	
	public void init(){
        // 1000：表示curator链接zk的时候超时时间是多少 3：表示链接zk的最大重试次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        int sessionTimeoutMs = 5000;// 这个值只能在4000-40000ms之间 表示链接断掉之后多长时间临时节点会消失
        int connectionTimeoutMs = 3000;// 获取链接的超时时间
        client = CuratorFrameworkFactory.newClient(
                connectString, sessionTimeoutMs, connectionTimeoutMs,
                retryPolicy);
        client.start();// 开启客户端
	}
	@Test
	public void testCreate() throws Exception{
		client.create().forPath(basePath, "helloworld".getBytes());
	}
	@Test
	public void testDistributedLock() throws Exception{
		InterProcessMutex lock = new InterProcessMutex(client, basePath);
		if (lock.acquire(100, TimeUnit.SECONDS)) {
			try {
				// TODO: sth
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				lock.release();
			}
		}
	}
	@Test
	public void testGetChildren() throws Exception {
        InetAddress localhost = InetAddress.getLocalHost();
        String ip = localhost.getHostAddress();
        
//        client.create().creatingParentsIfNeeded()// 如果父节点不存在则创建
//            .withMode(CreateMode.EPHEMERAL)//指定节点类型,临时节点
//            .withACL(Ids.OPEN_ACL_UNSAFE)// 设置节点权限信息
//            .forPath("/monitor/" + ip);//指定节点名称
        
        CuratorZookeeperClient  czc = client.getZookeeperClient();
        ZooKeeper zk= czc.getZooKeeper();
        List<String> children =  zk.getChildren("/monitor", false);
        for (String child : children) {
        	String path = "/monitor/"+child;
//			  zk.getChildren(, false);
			  byte[] data1 = zk.getData(path, null, null);
			  byte[]  data = client.getData().forPath(path);
			  System.err.println("path data:"+new String(data1));
		}
        
        NodeCache watcher = new NodeCache(client, "/monitor");
        watcher.getListenable().addListener(new NodeCacheListener() {
			
			@Override
			public void nodeChanged() throws Exception {
				// TODO Auto-generated method stub
				System.err.println("..........node changed!");
			}
		});
        watcher.start(true);
        
        
        PathChildrenCache watch = new PathChildrenCache(client, "/monitor", true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
        	@Override
			public void childEvent(CuratorFramework arg0, PathChildrenCacheEvent event) throws Exception {
				// TODO Auto-generated method stub
				ChildData data = event.getData();
				if (data == null) {
					System.out.println("No data in event[" + event + "]");
				} else {
					System.out.println("Receive event: " + "type=[" + event.getType() + "]" + ", path=["
							+ data.getPath() + "]" + ", data=[" + new String(data.getData()) + "]" + ", stat=["
							+ data.getStat() + "]");
				}
			}
		};
		watch.getListenable().addListener(pathChildrenCacheListener);
		
		watch.start(StartMode.BUILD_INITIAL_CACHE);
		
		
		
		
		
        
        
        
        
        
      
        
        
        
        
        
        
//        .forPath("/./" + ip);//指定节点名称
//        IllegalArgumentException: Invalid path string "/./169.254.96.131" caused by relative paths not allowed @1
        
        

		
//		client.delete();
        
        
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).withACL(Ids.OPEN_ACL_UNSAFE).forPath("/monitor/seq_node_1");
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).withACL(Ids.OPEN_ACL_UNSAFE).forPath("/monitor/seq_node_2");

//        DistributedQueue d;
//        CuratorFrameworkState  stat = client.getState();
//        System.err.println(stat.name());
        
        
        
		Thread.sleep(Integer.MAX_VALUE);
        
//        while (true) {
//            ;
//        }

        //或者
        
//        client.create().creatingParentsIfNeeded()// 如果父节点不存在则创建
//                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)// 指定节点类型,注意：临时节点必须在某一个永久节点下面
//                .withACL(Ids.OPEN_ACL_UNSAFE)// 设置节点权限信息
//                .forPath("/monitor/ + ip");// 指定节点名称
//        while (true) {
//            ;
//        }
		
		
		
		 
	}

}
