package org.apache.hadoop.mr.api;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hadoop.hive.ql.exec.TaskRunner;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.TaskTrackerInfo;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

/**   
* @Title: FileSplitTest.java 
* @Package showcase.dw.glbg.mr 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年12月20日 下午2:24:06   
*/
public class FileSplitTest {
	
	public static void main(String[] args) {
//		TextInputFormat
//		FileInputFormat   -->List<InputSplit> getSplits(JobContext job)
//		InputSplit  书上有具体解析 参考hadoop权威指南  输入分片
		/**
		 InputSplit represents the data to be processed by an individual Mapper. 

         Typically, it presents a byte-oriented view on the input and is the responsibility of RecordReader of the job
          to process this and present a record-oriented view.
		 */
//		org.apache.hadoop.io.IntWritable
//		TaskTracker 
//		JobTracker
//		TaskRunner
//		Reporter
		/** TaskStatus 类 
		 * MR Phrase阶段：
		 * public static enum Phase{STARTING, MAP, SHUFFLE, SORT, REDUCE, CLEANUP}
		 * 
		 * 状态：
		  public static enum State {RUNNING, SUCCEEDED, FAILED, UNASSIGNED, KILLED, 
		                            COMMIT_PENDING, FAILED_UNCLEAN, KILLED_UNCLEAN}
		                            
		 * TaskCounter 计数器，计量一些值，也可用自己用Report来记录某些指标                          
		 */
		
//		ConcurrentHashMap
		/**
		1、序列化是干什么的？
		       简单说就是为了保存在内存中的各种对象的状态（也就是实例变量，不是方法），并且可以把保存的对象状态再读出来。虽然你可以用你自己的各种各样的方法来保存object states，但是Java给你提供一种应该比你自己好的保存对象状态的机制，那就是序列化。
		
		2、什么情况下需要序列化   
		    a）当你想把的内存中的对象状态保存到一个文件中或者数据库中时候；
		    b）当你想用套接字在网络上传送对象的时候；
		    c）当你想通过RMI传输对象的时候；
		 */
		
		
		/**
		 * 双亲代理机制：http://blog.csdn.net/yangzishiw/article/details/44065093
		 * Bootstrp loader
		 * ExtClassLoade
		 * AppClassLoader
		 */
		
	}

}
