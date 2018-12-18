package source;
import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeperMain;
import org.apache.zookeeper.server.quorum.QuorumCnxManager.Listener;
import org.apache.zookeeper.server.quorum.QuorumPeerMain;

/**
 * @ClassName:     ZkAnalysis.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月6日 下午2:20:37 
 */
public class ZkAnalysis {

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		org.apache.zookeeper.server.quorum.FastLeaderElection o;
        System.setProperty("zookeeper.jmx.log4j.disable", "false");
        //zk服务器启动的入口类
        org.apache.zookeeper.server.quorum.QuorumPeerMain main = new QuorumPeerMain();
        main.main(args);
        //

		
/*		Double x = 23.9905555555556d;
		Double y = 4151.6;
		
		Double m = y/x;
		System.out.println(m.isInfinite() +":"+m.doubleValue());*/
		
	}
}
