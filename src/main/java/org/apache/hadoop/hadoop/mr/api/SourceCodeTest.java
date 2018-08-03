package org.apache.hadoop.hadoop.mr.api;

//import org.apache.hadoop.fs.ChecksumFileSystem;
//import org.apache.hadoop.io.WritableComparable;
//import org.apache.hadoop.io.compress.SnappyCodec;
//import org.apache.hadoop.mapred.JobTracker;
//import org.apache.hadoop.mapreduce.MRConfig;
//import org.apache.hadoop.mapreduce.OutputCommitter;
//import org.apache.hadoop.mapreduce.TaskTrackerInfo;
//import org.apache.hadoop.yarn.server.resourcemanager.ResourceManager;
//import org.apache.hadoop.yarn.server.resourcemanager.rmapp.RMApp;

public class SourceCodeTest {

	public static void main(String[] args) {
//		DataBlockScanner 一个在datanode上检查块数据异常的线程
//		NameNode
//		DataNode
		
//		ChecksumFileSystem
//		SnappyCodec
		
//		WritableComparable
//		IntWritable
		
		
//		JobTracker
//		TaskTracker
//		MRConfig
		
//		ApplicationManager
//		ResourceManager
//		NodeManager
		
		
//		MRAppMaster
		
//		OutputCommitter
/*		OutputCommitter describes the commit of task output for a Map-Reduce job. 

		The Map-Reduce framework relies on the OutputCommitter of the job to:


		1. Setup the job during initialization. For example, create the temporary output directory for the job during the 
		initialization of the job. 
		2. Cleanup the job after the job completion. For example, remove the temporary output directory after the job completion. 
		3. Setup the task temporary output. 
		4. Check whether a task needs a commit. This is to avoid the commit procedure if a task does not need commit. 
		5. Commit of the task output. 
		6. Discard the task commit. 
		The methods in this class can be called from several different processes and from several different contexts. 
		It is important to know which process and which context each is called from. Each method should be marked accordingly
		 in its documentation. It is also important to note that not all methods are guaranteed to be called once and only once. 
		 If a method is not guaranteed to have this property the output committer needs to handle this appropriately. 
		 Also note it will only be in rare situations where they may be called multiple times for the same task.		
	*/
		
		
//		YarnChild
	
	}
}
