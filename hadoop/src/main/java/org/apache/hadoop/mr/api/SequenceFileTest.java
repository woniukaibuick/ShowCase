package org.apache.hadoop.mr.api;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;


/**   
* @Title: SequenceFileTest.java 
* @Package showcase.dw.glbg.mr 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年12月19日 下午7:23:59   
*/
public class SequenceFileTest {
	public static void main(String[] args) throws IOException {
		String[] arr = new String[] {
				"one just as test",
				"two just as test"
		};
		String uri_1 = "hdfs://cdh-node0:8020/user/gongxuesong/data/sample1.txt";
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri_1),conf);
//		IntWritable key = new IntWritable();
//		Text value = new Text();
//		SequenceFile.Writer sw = SequenceFile.createWriter(fs, conf, new Path(uri_1), key.getClass(), value.getClass());
//		for (int i = 0; i < 100; i++) {
//			key.set(i+1);
//			value.set(UUID.randomUUID().toString());
//			sw.append(key, value);
//		}
//		
//		IOUtils.close(sw);
		
		
		SequenceFile.Reader reader = new  SequenceFile.Reader(fs, new Path(uri_1), conf);
		IntWritable key = new IntWritable();
		Text val = new Text();
		while (reader.next(key)) {
			reader.getCurrentValue(val);
			System.err.println(key.get() + ";"+val); 
			
			if (key == null) {
				System.out.println("end");
			}
		}
	}

}
