package org.apache.hadoop.hadoop.mr.udinputformat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SmallFilesToSequenceFileConverter extends Configured implements Tool{

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new SmallFilesToSequenceFileConverter(),
				args);
		System.exit(exitCode);

	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf  = new Configuration();
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		Job job = Job.getInstance(conf);
		job.setInputFormatClass(WholeFileInputFormat.class);
		job.setOutputFormatClass(FileOutputFormat.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(BytesWritable.class);
		job.setMapperClass(SequenceFileMapper.class);
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	/**
	 * 其实我这里只是输出为FileOutputFormat格式，不是SequenceFile，为了方便先看下合并后的内容
	 * @author gongva
	 *
	 */
	class SequenceFileMapper  extends Mapper<NullWritable ,BytesWritable,Text,BytesWritable> {
		private Text filenameKey;
		
		@Override
		protected void map(
				NullWritable key,
				BytesWritable value,
				Mapper<NullWritable, BytesWritable, Text, BytesWritable>.Context context)
				throws IOException, InterruptedException {
			context.write(filenameKey, value);
			
		}

		@Override
		protected void cleanup(
				Mapper<NullWritable, BytesWritable, Text, BytesWritable>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.cleanup(context);
		}

		@Override
		public void run(
				Mapper<NullWritable, BytesWritable, Text, BytesWritable>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.run(context);
		}

		@Override
		protected void setup(
				Mapper<NullWritable, BytesWritable, Text, BytesWritable>.Context context)
				throws IOException, InterruptedException {
			filenameKey = new Text();
			FileSplit split = (FileSplit) context.getInputSplit();
			filenameKey.set(split.getPath().toString());
		}
		
	}

}
