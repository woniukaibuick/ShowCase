package showcase.dw.glbg.netty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**   
* @Title: NettyTest.java 
* @Package showcase.dw.glbg.netty 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年11月1日 下午9:03:36   
*/
public class NettyTest {
	public static void main(String[] args) throws IOException {
		testIntBuffer();
	}
	
	private  static void testFileChannel() throws Exception {
		RandomAccessFile aFile = new RandomAccessFile("C:\\Users\\gongxuesong\\Desktop\\待设计指标.txt","rw" );
		FileChannel inChannel = aFile.getChannel();
		System.out.println("Channe Size:"+inChannel.size());
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {
			buf.flip();
			while (buf.hasRemaining()) {
				System.err.println((char)buf.get());
			}
			buf.clear();
			bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}
	private static void testIntBuffer() {
		IntBuffer ib = IntBuffer.allocate(2);
		ib.put(1);
		ib.put(2);
		
		IntBuffer ib1 = IntBuffer.allocate(2);
		ib1.put(1);
		ib1.put(2);
		System.err.println(ib == ib1);
		System.err.println(ib.equals(ib1));
//		ib.put(3);
//		ib.rewind();
//		System.err.println(ib.capacity());
//		System.err.println(ib.position());
//		System.err.println(ib.limit());
//		ib.flip();
//		System.err.println("after flip:--------------");
//		System.err.println(ib.capacity());
//		System.err.println(ib.position());
//		System.err.println(ib.limit());
//		System.err.println("get element:--------------");
//		System.err.println(ib.get());
//		ib.mark();
//		System.err.println(ib.get());
//		ib.reset();
//		System.err.println(ib.get());
//		System.err.println(ib.get());
//		System.err.println("after get element----------");
//		System.err.println(ib.capacity());
//		System.err.println(ib.position());
//		System.err.println(ib.limit());
	}

}
