package org.apache.hadoop.hadoop.mr.weather;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**   
* @Title: WeatherMr.java 
* @Package showcase.dw.glbg.mr 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年12月18日 下午6:46:01   
*/
public class WeatherMr {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		if (args == null || args.length != 2) {
			System.err.println("arguments not match,will exit directly!");
			return ;
		}
		Configuration conf = new Configuration();
		//下面这两个属性的设置是无效的--理解错误，下面两个设置只是针对map阶段输出的压缩 优化
//		conf.setBoolean("mapred.compress.map.output", true);
//		conf.setClass("mapred.map.output.compression.codec", GzipCodec.class, CompressionCodec.class);
		
		//有效设置如下：
		conf.setBoolean("mapred.output.compress", true);
		conf.setClass("mapred.output.compression.codec", GzipCodec.class, CompressionCodec.class);
		Job job = new Job(conf);
		job.setJarByClass(WeatherMr.class);
		job.setJobName("P_DW_SAL_CASE_DAY");
		job.setNumReduceTasks(1);
//		job.sett
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setMapperClass(MaxTemperatureMapper.class);
		job.setCombinerClass(MaxTemperatureReducer.class);
		job.setReducerClass(MaxTemperatureReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}

}

class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

	@Override
	protected void cleanup(Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.err.println("MaxTemperatureMapper cleanup!");
		super.cleanup(context);
	}

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
//		super.map(key, value, context);
		System.err.println("MaxTemperatureMapper map function!");
//		int type = value.toString().length() % 3;
//		context.write(new Text(String.valueOf(type)),new IntWritable(value.toString().length()));
		String line = value.toString();
		String year = line.substring(15, 19);
		int ariTemperature;
		if (line.charAt(87) == '+') {
			ariTemperature = Integer.parseInt(line.substring(88, 92));
		} else {
			ariTemperature = Integer.parseInt(line.substring(87, 92));
		}
		String quality = line.substring(92,93);
		if (ariTemperature != 9999 && quality.matches("[01459]")) {
			context.write(new Text(year), new IntWritable(ariTemperature));
		}
	}

	@Override
	public void run(Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.err.println("MaxTemperatureMapper run!");
		super.run(context);
	}

	@Override
	protected void setup(Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.err.println("MaxTemperatureMapper setup!");
		super.setup(context);
	}
	
}

class MaxTemperatureReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

	@Override
	protected void cleanup(Reducer<Text, IntWritable, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.err.println("MaxTemperatureReducer cleanup!");
		super.cleanup(context);
	}

	@Override
	protected void reduce(Text arg0, Iterable<IntWritable> arg1,
			Reducer<Text, IntWritable, Text, IntWritable>.Context arg2) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.err.println("MaxTemperatureReducer reduce!");
//		super.reduce(arg0, arg1, arg2);
		int min = Integer.MIN_VALUE;
		int max = 0;
		int num = 0;
		int total = 0;
		for(IntWritable iw : arg1) {
			max = Math.max(min, iw.get());
			total += iw.get();
			num++;
		}
//		arg2.write(arg0, new IntWritable(max));
		arg2.write(arg0, new IntWritable(total/num));
	}

	@Override
	public void run(Reducer<Text, IntWritable, Text, IntWritable>.Context arg0)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.err.println("MaxTemperatureReducer run!");
		super.run(arg0);
	}

	@Override
	protected void setup(Reducer<Text, IntWritable, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.err.println("MaxTemperatureReducer setup!");
		super.setup(context);
	}
	
}