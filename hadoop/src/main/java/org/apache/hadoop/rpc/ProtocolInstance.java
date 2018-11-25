package org.apache.hadoop.rpc;
/**
 * @ClassName:     ProtocolInstance.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月23日 上午10:54:18 
 */
public class ProtocolInstance implements ClientServerProtocol {

	@Override
	public String sayHello(String word) {
		// TODO Auto-generated method stub
		System.out.println("Server msg:"+word);
		return "hello "+word;
	}

}
