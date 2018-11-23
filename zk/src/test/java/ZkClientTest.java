import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeperMain;

/**
 * @ClassName:     ZkClientTest.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月9日 下午2:51:59 
 */
public class ZkClientTest {
	
	public static void main(String[] args) throws KeeperException, IOException, InterruptedException {
      org.apache.zookeeper.ZooKeeperMain zkm = new ZooKeeperMain(args);
      zkm.main(args);
	}

}
