package io.netty;


import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wenchao.meng
 *
 *         Nov 10, 2016
 */
public class AbstractClient extends AbstractNetty {


	EventLoopGroup slaveEventLoopGroup = new NioEventLoopGroup();
	
	protected Channel createClient(final ChannelDuplexHandler handler, InetSocketAddress address) throws InterruptedException {
		
		logger.debug("[createClient]{}", address);
		Bootstrap b = new Bootstrap();
		b.group(slaveEventLoopGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
		.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(new LoggingHandler(LogLevel.DEBUG));
				p.addLast(handler);
			}
		});
		
		return b.connect(address).sync().channel();
	}
	
}
