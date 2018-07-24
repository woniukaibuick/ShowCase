package valar.showcase.rpc.netty.appclient;

import valar.showcase.rpc.netty.client.RPCClientProxy;
import valar.showcase.rpc.netty.common.UserLoginService;

/**
 * @ClassName:     AppClientTest.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月23日 下午2:14:46 
 */
public class AppClientTest {

	public static void main(String[] args) {
		UserLoginService service = RPCClientProxy.get(UserLoginService.class);
		String result = service.login("valar", "password");
		System.out.println(result);
	}
}
