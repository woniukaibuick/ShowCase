package org.apache.hadoop.mr.udinputformat;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/**
 * 自定义InputFormat，合并多个小文件
 * @author gongva
 *
 */
public class WholeFileInputFormat extends FileInputFormat<NullWritable, BytesWritable> {

	@Override
	public RecordReader<NullWritable, BytesWritable> createRecordReader(
			InputSplit split, TaskAttemptContext context) throws IOException,
			InterruptedException {
		WholeFileRecordReader rd = new WholeFileRecordReader();
		rd.initialize(split, context);
		return rd;
	}

	@Override
	protected long getFormatMinSplitSize() {
		// TODO Auto-generated method stub
		return super.getFormatMinSplitSize();
	}

	@Override
	protected boolean isSplitable(JobContext context, Path filename) {
		return false;
	}

	@Override
	protected List<FileStatus> listStatus(JobContext job) throws IOException {
		// TODO Auto-generated method stub
		return super.listStatus(job);
	}

	@Override
	protected void addInputPathRecursively(List<FileStatus> result,
			FileSystem fs, Path path, PathFilter inputFilter)
			throws IOException {
		// TODO Auto-generated method stub
		super.addInputPathRecursively(result, fs, path, inputFilter);
	}

	@Override
	protected FileSplit makeSplit(Path file, long start, long length,
			String[] hosts) {
		// TODO Auto-generated method stub
		return super.makeSplit(file, start, length, hosts);
	}

	@Override
	protected FileSplit makeSplit(Path file, long start, long length,
			String[] hosts, String[] inMemoryHosts) {
		// TODO Auto-generated method stub
		return super.makeSplit(file, start, length, hosts, inMemoryHosts);
	}

	@Override
	public List<InputSplit> getSplits(JobContext job) throws IOException {
		// TODO Auto-generated method stub
		return super.getSplits(job);
	}

	@Override
	protected long computeSplitSize(long blockSize, long minSize, long maxSize) {
		// TODO Auto-generated method stub
		return super.computeSplitSize(blockSize, minSize, maxSize);
	}

	@Override
	protected int getBlockIndex(BlockLocation[] blkLocations, long offset) {
		// TODO Auto-generated method stub
		return super.getBlockIndex(blkLocations, offset);
	}
	


}
