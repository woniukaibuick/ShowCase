package org.apache.hadoop.rpc.hadoop;
/**
 * @ClassName:     ClientServerProtocol.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月23日 上午10:53:28 
 */
public interface ClientServerProtocol {

	public static long versionID = 1l;
	
	public String sayHello(String word);
}
