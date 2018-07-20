package valar.showcase.hadoop.mr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider.Text;

public class WordCountJob {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = new Job(conf);
		job.setJarByClass(WordCountJob.class);
		job.setJobName("test job");
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);

		
		FileInputFormat.addInputPath(job, new Path("/user/gongxuesong/mr_data"));
		FileOutputFormat.setOutputPath(job, new Path("/user/gongxuesong/mr_output_1"));
		
		job.waitForCompletion(true);
	}
}
