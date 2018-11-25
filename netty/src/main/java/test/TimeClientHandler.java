package  test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

/**
 * Created by wangdecheng on 24/05/2018.
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

    private int counter;

    private byte[] req;
    public TimeClientHandler(){
    	System.out.println("TimeClientHandler init req...");
         req = ("Query Time Order" + System.getProperty("line.separator")).getBytes() ;

    }
    @Override
    public void channelActive(ChannelHandlerContext ctx){
    	System.out.println("channelActive...");
        ByteBuf message = null;
        for(int i=0;i<1;i++){
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
            System.out.println("writeAndFlush...");
        }
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        System.out.println("channelRead...");
        //如果client scoketchannel 里面添加了 pipelineLineBasedFrameDecoder StringDecoder，就直接转为String，不然会报错
  /*      ByteBuf bb = (ByteBuf) msg;
        byte[] bytes = new byte[bb.readableBytes()];
        bb.readBytes(bytes);
         String body = new String(bytes);*/
        String body = (String)msg;
        System.out.println("NOW is :" + body +"; the counter is:"+ ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        logger.warning("unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }
}
