package org.apache.hadoop.rpc.hadoop;

import java.io.IOException;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;

/**
 * @ClassName:     HadoopCommonRpcClient.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月23日 上午10:53:04 
 */
public class HadoopCommonRpcServer {
	public static void main(String[] args) throws HadoopIllegalArgumentException, IOException {
	  Configuration conf = new Configuration();
	   Builder builder = new RPC.Builder(conf);
	   builder.setBindAddress("localhost")
	          .setProtocol(ClientServerProtocol.class)
	          .setInstance(new ProtocolInstance())
	          .setPort(8066)
	          .setVerbose(true);
	   
	   
	   builder.build().start();
	}

}
