package org.apache.hadoop.rpc.netty.common;
/**
 * @ClassName:     UserLoginService.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月23日 下午2:13:46 
 */
public interface UserLoginService {
	
	public String login(String username,String password);

}
