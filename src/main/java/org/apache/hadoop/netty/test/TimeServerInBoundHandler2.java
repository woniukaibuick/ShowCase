package org.apache.hadoop.netty.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerInBoundHandler2 extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		//super.channelRead(ctx, msg);
		System.out.println("TimeServerInBoundHandler2...");
		ctx.fireChannelRead(msg);
	}

}
