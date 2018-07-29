package valar.showcase.hadoop.mr.api;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.TextInputFormat;

public class NoSplittableTextInputFormat extends TextInputFormat{

	@Override
	protected boolean isSplitable(FileSystem fs, Path file) {
		// TODO Auto-generated method stub
//		return super.isSplitable(fs, file);
		return false;
	}

}
