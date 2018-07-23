package valar.showcase.rpc.hadoop;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

/**
 * @ClassName:     HadoopCommonRpcClient.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月23日 上午10:53:04 
 */
public class HadoopCommonRpcClient {
	
	public static void main(String[] args) throws IOException {
		ClientServerProtocol proxy = RPC.getProxy(ClientServerProtocol.class, 1l, new InetSocketAddress("localhost", 8066), new Configuration());
		
		System.out.println("client:"+proxy.sayHello("rpc"));
	}

}
