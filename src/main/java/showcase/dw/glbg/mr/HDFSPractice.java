package showcase.dw.glbg.mr;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSInputStream;
import org.apache.hadoop.hdfs.DFSOutputStream;
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
		
		FileStatus[] fileStatus = fs.globStatus(new Path("/user/gong*"));
		System.err.println(fileStatus.length);
		
		InputStream is = fs.open(new Path(uri));
		IOUtils.copyBytes(is, System.out, 1,false);
		IOUtils.closeStream(is);
		
		
		/** FSDataInputStream:
		 * Utility that wraps a FSInputStream in a DataInputStream and buffers input through a BufferedInputStream. 
		 */
		
		/**
		 * FSDataOutputStream
		 * Utility that wraps a OutputStream in a DataOutputStream.
		 */
		
		/** 
		 * DFSOutputStream:
		 * DFSOutputStream creates files from a stream of bytes. 
		 * The client application writes data that is cached internally by this stream.
		 *  Data is broken up into packets, each packet is typically 64K in size. 
		 *  A packet comprises of chunks. Each chunk is typically 512 bytes and has an associated checksum with it. 
		 *  When a client application fills up the currentPacket, it is enqueued into dataQueue.
		 *   The DataStreamer thread picks up packets from the dataQueue,
		 *    sends it to the first datanode in the pipeline and moves it from the dataQueue to the ackQueue. 
		 *    The ResponseProcessor receives acks from the datanodes.
		 *     When an successful ack for a packet is received from all datanodes,
		 *      the ResponseProcessor removes the corresponding packet from the ackQueue. 
		 *      In case of error, all outstanding packets and moved from ackQueue.
		 *       A new pipeline is setup by eliminating the bad datanode from the original pipeline. 
		 *       The DataStreamer now starts sending packets from the dataQueue.
		 *       eliminating:排除  pipeline:管线，比如3个节点的管线，第一个节点存储后会将文件发给第二个节点，第二个节点存储后发给你三个节点
		 */
		
		/**
		 * DFSInputStream:
		 * DFSInputStream provides bytes from a named file. 
		 * It handles negotiation of the namenode and various datanodes as necessary.
		 */
		
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
