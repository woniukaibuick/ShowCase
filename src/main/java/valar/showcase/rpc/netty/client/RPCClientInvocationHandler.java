package valar.showcase.rpc.netty.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import valar.showcase.rpc.netty.common.RpcRequest;

/**
 * @ClassName:     RPCClientInvocationHandler.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月23日 上午11:13:10 
 */
public class RPCClientInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		//封装请求  
		
		RpcRequest req = new RpcRequest();
		req.setClassName(method.getDeclaringClass().getName());
		req.setMethodName(method.getName());
		req.setParameters(args);
		req.setParameterTypes(method.getParameterTypes());
		req.setRequestId(UUID.randomUUID().toString());
		
		// 发送请求
		
		
		
		// 解析结果
		
		
		return null;
	}

}
