package io.netty;


import org.junit.Test;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wenchao.meng
 *
 *         Nov 10, 2016
 */
public class EchoServer extends AbstractServer {

	@Test
	public void startEchoServer() throws InterruptedException {

		startServer(new EchoHandler());
	}
	
	@Sharable
	public class EchoHandler extends ChannelDuplexHandler{
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			
			ByteBuf byteBuf = (ByteBuf) msg;
			logger.info("[channelRead]{}", byteBuf.readableBytes());
			ctx.channel().writeAndFlush(msg);
		}
		
	}
}
