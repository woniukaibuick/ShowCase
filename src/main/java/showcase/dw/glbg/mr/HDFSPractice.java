package showcase.dw.glbg.mr;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**   
* @Title: HDFSPractice.java 
* @Package showcase.dw.glbg.mr 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年12月19日 上午10:12:24   
*/
public class HDFSPractice {
	static {
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
	}
	static String uri = "hdfs://cdh-node0:8020/user/gongxuesong/mr_data/stock_in.java";
	public static void main(String[] args) throws Exception {
		testFileSystem() ;
	}
	
	static void testDFSFileStatus() {
		FileStatus fs ;
	}
	
	/*
   Hadoop 2.2.0允许创建软连接并且允许MapReduce程序直接处理这些Symlink。为了允许在集群中创建symlink，需要在配置文件hdfs-site.xml中增加如下的配置项： 
　　<property> 
　　        <name>test.SymlinkEnabledForTesting</name> 
　　        <value>true</value> 
　　</property> 
　　创建symlink有两种方式：一种是FileSystem.createSymlink方式，一种是FileContext.createSymlink方式。考虑到hadoop 1.0等的编码习惯，FileSystem的使用应该更为普遍。
	 * 
	 */
	static void testFileSystem() throws IOException {
		FileSystem fs = FileSystem.get(URI.create(uri),new Configuration()); 
		FileStatus fStatus =  fs.getFileStatus(new Path(uri));
		//FileStatus{path=hdfs://cdh-node0:8020/user/gongxuesong/mr_data/stock_in.java; isDirectory=false; length=54166; replication=3; blocksize=134217728; modification_time=1513060464819; access_time=1513649934142; owner=hdfs; group=gongxuesong; permission=rw-r--r--; isSymlink=false}
		System.err.println(fStatus.getBlockSize());
		InputStream is = fs.open(new Path(uri));
		IOUtils.copyBytes(is, System.out, 1,false);
		IOUtils.closeStream(is);
	}
	
	static void testFsUrlStreamHandler() throws MalformedURLException, IOException {
		InputStream is = 
				 new URL(uri).openStream();
//				byte[] data = new byte[1024];
//			    while (is.available() != -1) {
//			    	is.read(data);
//					System.out.println(new String(data));
//				}  
				IOUtils.copyBytes(is,System.out, 4096, false);
				IOUtils.closeStream(is);
	}

}
