package valar.showcase.hadoop.yarn;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;

/**
 * @ClassName:     Test.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月23日 下午6:34:26 
 */
public class Test {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance();
		//从此处跟进去窥测提交流程
		job.waitForCompletion(true);
//		DistributedCache dc;
		job.addCacheFile(null);
		System.runFinalization();
	}

}
