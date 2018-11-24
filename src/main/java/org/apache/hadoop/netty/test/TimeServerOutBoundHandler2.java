package org.apache.hadoop.netty.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TimeServerOutBoundHandler2 extends ChannelOutboundHandlerAdapter{

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("TimeServerOutBoundHandler2 read...");
		super.read(ctx);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("TimeServerOutBoundHandler2 write...");
		super.write(ctx, msg, promise);
	}

}
