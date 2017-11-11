package showcase.dw.glbg.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

/**   
* @Title: NettyServer.java 
* @Package showcase.dw.glbg.netty 
* @Description: TODO(description) 
* @author gong_xuesong@126.com
* @date 2017年11月7日 下午6:40:59   
*/
public class NettyServer {
	private static final int BUF_SIZE = 256;
	private static final int TIMEOUT = 3000;
	    public static void main(String[] args) throws IOException {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			Selector selector = Selector.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(8089));
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			while (true) {
				if (selector.select(TIMEOUT) == 0) {
					System.err.println(".");
					continue;
				}
				Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
				while (keyIterator.hasNext()) {
					SelectionKey key = keyIterator.next();
					keyIterator.remove();
					if (key.isAcceptable()) {
						SocketChannel clientChannel = ((ServerSocketChannel)key.channel()).accept();
						clientChannel.configureBlocking(false);
						clientChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(BUF_SIZE));
					}
					if (key.isReadable()) {
						SocketChannel clientChannel = (SocketChannel) key.channel();
						ByteBuffer buf =  (ByteBuffer) key.attachment();
						long bytesRead = clientChannel.read(buf);
						if (bytesRead == -1) {
							clientChannel.close();
						} else {
							key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
							System.out.println("get data length:"+bytesRead+", and data:"+getStringByByteBuffer(buf));

						}
					}
					if (key.isValid() && key.isWritable()) {
						ByteBuffer buf = (ByteBuffer) key.attachment();
						buf.flip();
						SocketChannel clientChannel = (SocketChannel)key.channel();
						clientChannel.write(buf);
						if (!buf.hasRemaining()) {
							key.interestOps(SelectionKey.OP_READ);
						}
						buf.compact();
					}
				}
			}
			
		}
	    
	    private static String getStringByByteBuffer(ByteBuffer bb) {
	    	String result = null;
	    	Charset charset = null;
	    	CharsetDecoder decoder = null;
	    	CharBuffer charBuffer = null;
	    	try {
				charset = Charset.forName("UTF-8");
				decoder = charset.newDecoder();
				charBuffer = decoder.decode(bb.asReadOnlyBuffer());
				bb.flip();
				return charBuffer.toString();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    	return result;
	    }
}
