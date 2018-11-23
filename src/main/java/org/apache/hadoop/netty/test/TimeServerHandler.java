package org.apache.hadoop.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by wangdecheng on 07/06/2018.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
	  private static final Logger logger = Logger.getLogger(TimeServerHandler.class.getName());

    private int counter;
@Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
       System.out.println("channelRead...");
/*       ByteBuf bb = (ByteBuf) msg;
       byte[] bytes = new byte[bb.readableBytes()];
       bb.readBytes(bytes);
        String body = new String(bytes);*/
       String body = (String)msg;
        System.out.println("the time server receive order:"+body + ";the counter is:" + ++counter);
        String currentTime = "query time order".equalsIgnoreCase(body)? new Date().toString():"BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable throwable){
    	System.out.println("exceptionCaught...:"+throwable.toString());
    	ctx.close();
    }
}
