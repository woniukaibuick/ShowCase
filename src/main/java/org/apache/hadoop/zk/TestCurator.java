package org.apache.hadoop.zk;


import java.net.InetAddress;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.junit.Test;

/**
 * 	
 * @author zhouls
 *
 */
public class TestCurator {

	private String connectString =  "127.0.0.1:2181";
    @Test
    public void test1() throws Exception {
        // 1000：表示curator链接zk的时候超时时间是多少 3：表示链接zk的最大重试次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        int sessionTimeoutMs = 5000;// 这个值只能在4000-40000ms之间 表示链接断掉之后多长时间临时节点会消失
        int connectionTimeoutMs = 3000;// 获取链接的超时时间
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                connectString, sessionTimeoutMs, connectionTimeoutMs,
                retryPolicy);
        client.start();// 开启客户端

        InetAddress localhost = InetAddress.getLocalHost();
        String ip = localhost.getHostAddress();
        
        client.create().creatingParentsIfNeeded()// 如果父节点不存在则创建
            .withMode(CreateMode.EPHEMERAL)//指定节点类型,临时节点
            .withACL(Ids.OPEN_ACL_UNSAFE)// 设置节点权限信息
            .forPath("/monitor/" + ip);//指定节点名称
        while (true) {
            ;
        }
        
    }

    
    

        @Test
        public void test2() throws Exception {
            // 1000：表示curator链接zk的时候超时时间是多少 3：表示链接zk的最大重试次数
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            int sessionTimeoutMs = 5000;// 这个值只能在4000-40000ms之间 表示链接断掉之后多长时间临时节点会消失
            int connectionTimeoutMs = 3000;// 获取链接的超时时间
            CuratorFramework client = CuratorFrameworkFactory.newClient(
                    connectString, sessionTimeoutMs, connectionTimeoutMs,
                    retryPolicy);
            client.start();// 开启客户端

            InetAddress localhost = InetAddress.getLocalHost();
            String ip = localhost.getHostAddress();
            
            client.create().creatingParentsIfNeeded()// 如果父节点不存在则创建
            .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)// 指定节点类型,注意：临时节点必须在某一个永久节点下面
            .withACL(Ids.OPEN_ACL_UNSAFE)// 设置节点权限信息
            .forPath("/monitor/");// 指定节点名称
            while (true) {
                ;
            }

    }
        
        
        
        @Test
        public void test3() throws Exception {
            // 1000：表示curator链接zk的时候超时时间是多少 3：表示链接zk的最大重试次数
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            int sessionTimeoutMs = 5000;// 这个值只能在4000-40000ms之间 表示链接断掉之后多长时间临时节点会消失
            int connectionTimeoutMs = 3000;// 获取链接的超时时间
            CuratorFramework client = CuratorFrameworkFactory.newClient(
                    connectString, sessionTimeoutMs, connectionTimeoutMs,
                    retryPolicy);
            client.start();// 开启客户端

            InetAddress localhost = InetAddress.getLocalHost();
            String ip = localhost.getHostAddress();
            
            client.create().creatingParentsIfNeeded()// 如果父节点不存在则创建
                .withMode(CreateMode.EPHEMERAL)//指定节点类型,临时节点
                .withACL(Ids.OPEN_ACL_UNSAFE)// 设置节点权限信息
                .forPath("/lock/" + ip);//指定节点名称
            while (true) {
                ;
            }
            
        }
        
        
        
        
        @Test
        public void test4() throws Exception {
            // 1000：表示curator链接zk的时候超时时间是多少 3：表示链接zk的最大重试次数
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            int sessionTimeoutMs = 5000;// 这个值只能在4000-40000ms之间 表示链接断掉之后多长时间临时节点会消失
            int connectionTimeoutMs = 3000;// 获取链接的超时时间
            CuratorFramework client = CuratorFrameworkFactory.newClient(
                    connectString, sessionTimeoutMs, connectionTimeoutMs,
                    retryPolicy);
            client.start();// 开启客户端

            InetAddress localhost = InetAddress.getLocalHost();
            String ip = localhost.getHostAddress();
            
            client.create().creatingParentsIfNeeded()// 如果父节点不存在则创建
            .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)// 指定节点类型,注意：临时节点必须在某一个永久节点下面
            .withACL(Ids.OPEN_ACL_UNSAFE)// 设置节点权限信息
            .forPath("/lock/");// 指定节点名称
            while (true) {
                ;
            }

    }
        
        
        

}