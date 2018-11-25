package org.apache.hadoop.mr.udinputformat;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/**
 * 自定义RecordReader，由于是小文件，每一个InputSplit就是一个文件
 * 读取整个文件的字节内容，作为一个BytesWritable value
 * @author gongva
 *
 */
public class WholeFileRecordReader extends RecordReader<NullWritable, BytesWritable> {
    private FileSplit split;
    private Configuration conf ;
    private BytesWritable value = new BytesWritable();
	private boolean processed = false;

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		this.split = (FileSplit) split;
		conf = context.getConfiguration();
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if (!processed) {
			byte[] contents = new byte[(int) split.getLength()];
			Path path = split.getPath();
			FileSystem fs = FileSystem.get(conf);
			FSDataInputStream input = null;
			try {
				input = fs.open(path);
				IOUtils.readFully(input, contents, 0, contents.length);
				value.set(new BytesWritable(contents));
			} finally {
				IOUtils.closeQuietly(input);
			}
			processed = true;
			return true;
		}
		return false;
	}

	@Override
	public NullWritable getCurrentKey() throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		return NullWritable.get();
	}

	@Override
	public BytesWritable getCurrentValue() throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return processed ? 1 : 0;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		//do nothing
	}

}
