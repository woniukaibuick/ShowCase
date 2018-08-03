package org.apache.hadoop.hadoop.hdfs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class HDFSUtil {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

//		String configurationFilePath = "D:\\work\\EclipseWorkspace\\ShowCase\\src\\main\\resources\\conf\\core-site.xml";
				//"/conf/core-site.xml";
		String key = "fs.default.name";
		Configuration conf = new Configuration();
		conf.set(key, "hdfs://10.40.6.177:8020");
		conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()); 
		
	    String localSrc = "C://Users/gongxuesong/Desktop/T.txt";
	    String dst = "/user/gongxuesong/tmp_11";
	    String dstFile = "/user/gongxuesong/tmp_11/T.txt";
	    
	    InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
	    
	    FileSystem fs = FileSystem.get(URI.create(dst), conf);
	    OutputStream out = fs.create(new Path(dstFile), new Progressable() {
	      public void progress() {
	        System.out.print(".");
	      }
	    });
//	    fs.copyFromLocalFile(new Path(localSrc), new Path(dst));
//	    fs.crea
	    
	    IOUtils.copyBytes(in, out, 4096, true);
	  }

}
