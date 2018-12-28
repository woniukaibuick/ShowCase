package source;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.junit.Before;
import org.junit.Test;

/**
 * @ClassName: ZkAnalysis.java
 * @Description: zk源码分析
 * @author gongva
 * @version V1.0
 * @Date 2018年7月6日 下午2:20:37
 */
public class ZkAnalysis {
	private String[] args;

	@Before
	public void init() {
		args = new String[] {};
	}

	@Test
	public void testQuorumPeerMain() throws IOException, InterruptedException,
			KeeperException {
		org.apache.zookeeper.server.quorum.FastLeaderElection o;
		System.setProperty("zookeeper.jmx.log4j.disable", "false");
		// zk服务器启动的入口类
		org.apache.zookeeper.server.quorum.QuorumPeerMain.main(args);
	}

	@Test
	public void testZooKeeperMain() throws KeeperException, IOException,
			InterruptedException {
		org.apache.zookeeper.ZooKeeperMain.main(args);
	}

	@Test
	public void testDoubleInfinite() {
		Double x = 23.9905555555556d;
		Double y = 4151.6;

		Double m = y / x;
		System.out.println(m.isInfinite() + ":" + m.doubleValue());
	}
}
