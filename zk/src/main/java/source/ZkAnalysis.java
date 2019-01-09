package source;

import java.io.File;
import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ZKDatabase;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.persistence.FileTxnSnapLog;
import org.apache.zookeeper.server.quorum.QuorumPeer;
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

	/**
	 * zk服务端统一入口QuorumPeerMain，解析配置后根据server是否大于1选择集群模式或者单机模式
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	@Test
	public void testQuorumPeerMain() throws IOException, InterruptedException,
			KeeperException {
		org.apache.zookeeper.server.quorum.FastLeaderElection o;
		System.setProperty("zookeeper.jmx.log4j.disable", "false");
		// zk服务器启动的入口类
		org.apache.zookeeper.server.quorum.QuorumPeerMain.main(args);
		//QuorumPeerMain.runFromConfig
/*		  ServerCnxnFactory cnxnFactory = ServerCnxnFactory.createFactory();
          cnxnFactory.configure(config.getClientPortAddress(),
                                config.getMaxClientCnxns());
  
          quorumPeer = new QuorumPeer();
          quorumPeer.setClientPortAddress(config.getClientPortAddress());
          quorumPeer.setTxnFactory(new FileTxnSnapLog(
                      new File(config.getDataLogDir()),
                      new File(config.getDataDir())));
          quorumPeer.setQuorumPeers(config.getServers());
          quorumPeer.setElectionType(config.getElectionAlg());
          quorumPeer.setMyid(config.getServerId());
          quorumPeer.setTickTime(config.getTickTime());
          quorumPeer.setMinSessionTimeout(config.getMinSessionTimeout());
          quorumPeer.setMaxSessionTimeout(config.getMaxSessionTimeout());
          quorumPeer.setInitLimit(config.getInitLimit());
          quorumPeer.setSyncLimit(config.getSyncLimit());
          quorumPeer.setQuorumVerifier(config.getQuorumVerifier());
          quorumPeer.setCnxnFactory(cnxnFactory);
          quorumPeer.setZKDatabase(new ZKDatabase(quorumPeer.getTxnFactory()));
          quorumPeer.setLearnerType(config.getPeerType());
  
          quorumPeer.start();
          quorumPeer.join();*/
	}

	/**
	 * The command line client to ZooKeeper.
	 * @throws KeeperException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void testZooKeeperMain() throws KeeperException, IOException,
			InterruptedException {
		org.apache.zookeeper.ZooKeeperMain.main(args);
	}

	/**
	 * 单机模式zk主类
	 */
	@Test
	public void testZooKeeperServerMain(){
		ZooKeeperServerMain.main(args);
		//runFromConfig方法内 执行步骤
/*		  ZooKeeperServer zkServer = new ZooKeeperServer();  // 创建ServerStats，收集基本信息
	
	      FileTxnSnapLog ftxn = new FileTxnSnapLog(new
	             File(config.dataLogDir), new File(config.dataDir));//txnlog 和 snapshot 处理类，提供对日志文件和快照数据文件进行操作的接口
	      zkServer.setTxnLogFactory(ftxn);//
	      zkServer.setTickTime(config.tickTime);//
	      zkServer.setMinSessionTimeout(config.minSessionTimeout);//设置会话最小超市时间
	      zkServer.setMaxSessionTimeout(config.maxSessionTimeout);//设置会话最大超市时间
	      cnxnFactory = ServerCnxnFactory.createFactory();//创建ServerCnxnFactory，此处可以通过配置文件使用自定义的
	      //默认实现有NIOServerCnxnFactory何NettyServerCnxnFactory。自定义实现继承ServerCnxnFactory即可
	      cnxnFactory.configure(config.getClientPortAddress(),
	              config.getMaxClientCnxns());//初始化一个Thread作为ServerCnxnFactory的主线程，然后初始化 NIO（ServerSocketChannel）服务器
	      cnxnFactory.startup(zkServer);//启动ServerCnxnFactory的主线程，恢复本地数据 创建SessionTracker，设置RequestProcessors
	      //单机模式下的Processor是只有三个PrepRequestProcessor ——> SyncRequestProcessor ——> FinalRequestProcessor  责任链模式
	      cnxnFactory.join();//
	      if (zkServer.isRunning()) {
	          zkServer.shutdown();
	      }*/
		
		
	}
	@Test
	public void testDoubleInfinite() {
		Double x = 23.9905555555556d;
		Double y = 4151.6;

		Double m = y / x;
		System.out.println(m.isInfinite() + ":" + m.doubleValue());
	}
}
