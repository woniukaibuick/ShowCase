package org.apache.hadoop.mr.findfriends;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @ClassName:     FindFriendsTest.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年7月31日 下午2:26:11 
 */
public class FindFriendsTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);

		job.setJarByClass(FindFriendsTest.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		//第一阶段
/*		job.setMapperClass(FindFriendsMapper.class);
		job.setReducerClass(FindFriendsReducer.class);
		FileInputFormat.setInputPaths(job, new Path("D:/tmp/srcdata/friends"));
		FileOutputFormat.setOutputPath(job, new Path("D:/tmp/srcdata/friends_output00"));*/
		
		//第二阶段
/*		job.setMapperClass(FindFriendsStageTwoMapper.class);
		job.setReducerClass(FindFriendsStageTwoReducer.class);
		FileInputFormat.setInputPaths(job, new Path("D:/tmp/srcdata/friends_tmp"));
		FileOutputFormat.setOutputPath(job, new Path("D:/tmp/srcdata/friends_tmp_output00"));*/
		job.waitForCompletion(true);
		
	}
}
class FindFriendsMapper extends Mapper<LongWritable, Text, Text, Text>{
	private Text outputKey = new Text();
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {

		String[] item = value.toString().split(":");
		String user = item[0];
		String[] friends = item[1].split(",");
		for(String friend : friends){
			outputKey.set(friend);
			context.write(outputKey, new Text(user));
		}
	}
	
}
class FindFriendsReducer extends Reducer<Text, Text,Text, Text>{
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, Text, Text>.Context context) throws IOException,
			InterruptedException {
		List<String> friendsList = new ArrayList<String>();
		Collections.sort(friendsList);
		for(Text value : values){
			friendsList.add(value.toString());
		}
		for (int i = 0; i < friendsList.size()-2; i++) {
			for (int j = i+1; j < friendsList.size()-1; j++) {
				String comp = friendsList.get(i)+"-"+friendsList.get(j);
				context.write(new Text(comp), key);
			}
		}

	}
}



class FindFriendsStageTwoMapper extends Mapper<LongWritable, Text, Text, Text>{
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {

		String[] item = value.toString().split("\t");
		context.write(new Text(item[0]), new Text(item[1]));
	}
	
}
class FindFriendsStageTwoReducer extends Reducer<Text, Text,Text, Text>{
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, Text, Text>.Context context) throws IOException,
			InterruptedException {
		StringBuffer sb  = new StringBuffer();
		for(Text friend : values){
			sb.append(friend.toString()).append(",");
		}
		context.write(key, new Text(sb.toString()));
	}
}
