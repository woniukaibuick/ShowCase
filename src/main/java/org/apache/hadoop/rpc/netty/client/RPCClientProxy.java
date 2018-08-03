package org.apache.hadoop.rpc.netty.client;

import java.lang.reflect.Proxy;

/**
 * @ClassName:     RPCClientProxy.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月23日 上午11:14:04 
 */
public class RPCClientProxy {
	
	public static <T> T get(Class<T> interfaceClass){
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class[]{interfaceClass}, new RPCClientInvocationHandler());
	}

}
