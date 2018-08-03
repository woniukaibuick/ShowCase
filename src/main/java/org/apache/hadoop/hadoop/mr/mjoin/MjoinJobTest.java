package org.apache.hadoop.hadoop.mr.mjoin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * @ClassName:     MjoinJobTest.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月29日 下午5:11:39 
 */
public class MjoinJobTest {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
		
/*		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		job.setJarByClass(MjoinJobTest.class);
		job.setNumReduceTasks(0);
		
		job.setMapperClass(MapSideJoinMapperOffical.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);*/
		
/*		FileInputFormat.setInputPaths(job, new Path("D:/tmp/data/map_side_join"));
		FileOutputFormat.setOutputPath(job, new Path("D:/tmp/data/map_side_join_output101"));
		
		
		job.addCacheFile(new URI("file:///D:/tmp/data/map_side_join_cache/pdts.txt"));
		job.addCacheFile(new URI("/tmp/data/map_side_join_cache/pdts.txt"));*/
		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);

		job.setJarByClass(MjoinJobTest.class);

		job.setMapperClass(MapSideJoinMapperOffical.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("D:/tmp/data/map_side_join"));
		FileOutputFormat.setOutputPath(job, new Path("D:/tmp/data/map_side_join_output107"));

		job.addCacheFile(new URI("file:/D:/tmp/data/map_side_join_cache/pdts.txt"));
		
		
		job.waitForCompletion(true);
	}

}
class MjoinMapper extends Mapper<LongWritable,Text,Text,NullWritable>{
	/**
	 * key:pd001  value:apple
	 */
	private static Map<String,String> productMap = new HashMap<String,String>();
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		String[] orderArr = value.toString().split("\t");
		String  pid = orderArr[1];
		String  pName = productMap.get(pid);
		context.write(new Text(value.toString() +"\t"+pName), NullWritable.get());
		
		
	}

	@Override
	protected void setup(
			Mapper<LongWritable, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
//		super.setup(context);
		productMap.clear();
		InputStreamReader isr = new InputStreamReader(new FileInputStream("pdts.txt"));
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		while ((line=br.readLine()) != "") {
			String[] pdtArr = line.split(",");
			productMap.put(pdtArr[0], pdtArr[1]);
		}
		br.close();
		
	}

	@Override
	protected void cleanup(
			Mapper<LongWritable, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		super.cleanup(context);
		productMap.clear();
		productMap = null;
	}
	
	
	
	
}
class MapSideJoinMapperOffical extends Mapper<LongWritable, Text, Text, NullWritable> {
	// 用一个hashmap来加载保存产品信息表
	Map<String, String> pdInfoMap = new HashMap<String, String>();

	Text k = new Text();

	/**
	 * 通过阅读父类Mapper的源码，发现 setup方法是在maptask处理数据之前调用一次 可以用来做一些初始化工作
	 */
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("pdts.txt")));
		String line;
		while (StringUtils.isNotEmpty(line = br.readLine())) {
			String[] fields = line.split(",");
			pdInfoMap.put(fields[0], fields[1]);
		}
		br.close();
	}

	// 由于已经持有完整的产品信息表，所以在map方法中就能实现join逻辑了
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String orderLine = value.toString();
		String[] fields = orderLine.split("\t");
		String pdName = pdInfoMap.get(fields[1]);
		k.set(orderLine + "\t" + pdName);
		context.write(k, NullWritable.get());
	}

}