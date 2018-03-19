package showcase.dw.glbg.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PushbackInputStream;
import java.io.SequenceInputStream;
import java.io.StringBufferInputStream;

public class Day20180112 {
	public static void main(String[] args) {
		
	}

	
	private void testByteArrayInputStream() {
		byte[] buf = new byte[1024];
		
		//InputStream里面主要的方法是read，伴随的其他方法有
		InputStream inputStream;
		/*  InputStream所有涉及的子类如下所示：
		    java.io.BufferedInputStream
			java.io.ByteArrayInputStream
			java.io.DataInputStream
			java.io.FilterInputStream
			java.io.InputStream#read()
			java.io.OutputStream
			java.io.PushbackInputStream
		 */
		
		
		ByteArrayInputStream bais ;//= new ByteArrayInputStream(buf);
		
		//Only the low eight bits of each character in the string are used by this class.
		//只有低8位的
		StringBufferInputStream sbis;
		
		FileInputStream fis;
		PipedInputStream pis;
		SequenceInputStream sis;
		FilterInputStream filteris;
		/*
		 * 回退：给了用户第二次读的机会。
                           回退流
		    在JAVA IO中所有的数据都是采用顺序的读取方式，
		    即对于一个输入流来讲都是采用从头到尾的顺序读取的，
		    如果在输入流中某个不需要的内容被读取进来，则只能通过程序将这些不需要的内容处理掉，
		    为了解决这样的处理问题，在JAVA中提供了一种回退输入流（PushbackInputStream、PushbackReader），
		    可以把读取进来的某些数据重新回退到输入流的缓冲区之中。
		 */
		PushbackInputStream pushis;
		
		
		OutputStream outputStream;
		ByteArrayOutputStream baos;
		PipedOutputStream pos;
		FileOutputStream fos;
		FilterOutputStream filterOs;
		
		
		
		
		
	}
}
