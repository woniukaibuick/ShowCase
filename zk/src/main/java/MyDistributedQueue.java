
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class MyDistributedQueue<T> implements Queue<T> {

	private static final Logger LOGGER = Logger.getLogger(MyDistributedQueue.class);

	private static final String LOCK_ZNODE = "/queue";
	private static CuratorFramework client;
	private static String connectString = "127.0.0.1:2181";

	private static int sessionTimeoutMs = 5000;
	private static int connectionTimeoutMs = 3000;
	private static int baseSleepTimeMs = 1000;
	private static int maxRetries = 3;
	private int defaultQueueSize = Integer.MAX_VALUE;
	/**
	 * key:the node path<br>
	 * value: queue object
	 */
	private Map<T,String> objectMap;

	private static String ZK_LOCK_PATH = null;

	static enum NodeStatus {
		PREP, RUNNING, SUCCEEDED, SUSPENDED, KILLED, FAILED
	}

	static {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
		CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, sessionTimeoutMs,
				connectionTimeoutMs, retryPolicy);
		client.start();
	}

	private void doWithLock(CuratorFramework client) throws Exception {
		InterProcessMutex lock = new InterProcessMutex(client, ZK_LOCK_PATH);
		try {
			if (lock.acquire(10 * 1000, TimeUnit.SECONDS)) {
				System.out.println(Thread.currentThread().getName() + " hold lock");
				Thread.sleep(5000L);
				System.out.println(Thread.currentThread().getName() + " release lock");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				lock.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public NodeStatus getNodeStatus(String str) {
		NodeStatus result = null;
		if(str == null) str = "";
		switch (str.toUpperCase()) {
		case "PREP":
			result = NodeStatus.PREP;
			break;
		default:
			result = NodeStatus.PREP;
			break;
		}
		return result;
	}

	public NodeStatus getNodeStatus(T object) throws Exception {
		if (object == null) {
			throw new Exception("Object cannot be null!");
		}
		String nodeID = objectMap.get(object);
		List<String> paths = client.getChildren().forPath(LOCK_ZNODE);
		int index = paths.indexOf(nodeID);
		for (int i = 0; i < index; i++) {
			String data = getDataNode(client, paths.get(i));
			if(data == null || data.isEmpty() ||  !NodeStatus.SUCCEEDED.equals(getNodeStatus(data))) {
				
			}
		}
		 return NodeStatus.SUCCEEDED;
	}

	/**
	 * Tree Cache：Path Cache和Node Cache的“合体”，监视路径下的创建、更新、删除事件，并缓存路径下所有孩子结点的数据。
	 * Tree Cache is not available in curator-recipes 2.3.0
	 * @throws Exception
	 */
	@Deprecated
	private void addListenerForTree() throws Exception {
//		TreeCache treeCache = new TreeCache(client, LOCK_ZNODE);
//		treeCache.getListenable().addListener(new TreeCacheListener() {
//
//			@Override
//			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
//				// TODO Auto-generated method stub,need to do something when opeations happens
//				ChildData childData = event.getData();
//				// childData.getStat();
//				// childData.getData();
//				// childData.getPath();
//
//			}
//		});
//		treeCache.start();
	}

	public MyDistributedQueue(int defaultQueueSize) {
		this.defaultQueueSize = defaultQueueSize;
		objectMap = new HashMap<T,String>(this.defaultQueueSize);
	}

	private static boolean createNode(CuratorFramework client, String path, byte[] data) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		String forPath = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
				.withACL(Ids.OPEN_ACL_UNSAFE).forPath(path, data);
		return true;
	}

	private static String getDataNode(CuratorFramework client, String path) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		byte[] datas = client.getData().forPath(path);
		return new String(datas);
	}

	private static void deleteDataNode(CuratorFramework client, String path) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		Void forPath = client.delete().deletingChildrenIfNeeded().forPath(path);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return objectMap.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return objectMap == null || objectMap.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return objectMap.values().contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		objectMap.clear();
		try {
			deleteDataNode(client, LOCK_ZNODE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.toString(), e);
		}
	}

	@Override
	public boolean add(T e) {
		// TODO Auto-generated method stub
		if(e == null) {
			return false;
		}
		String nodeID = e.toString();
		try {
			createNode(client, LOCK_ZNODE + "/" + nodeID, NodeStatus.PREP.toString().getBytes());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LOGGER.error(e1.toString(), e1);
			return false;
		}
		objectMap.put( e,nodeID);
		return true;
	}

	@Override
	public boolean offer(T e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T peek() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
