package io.netty;

import java.io.IOException;

import org.junit.After;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wenchao.meng
 *
 *         Nov 10, 2016
 */
public class AbstractServer extends AbstractNetty {

	protected ServerSocketChannel startServer(ChannelDuplexHandler handler) throws InterruptedException {
		return startServer(getDefaultPort(), handler);
	}

	protected ServerSocketChannel startServer(int port, final ChannelDuplexHandler handler)
			throws InterruptedException {

		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline p = ch.pipeline();
						p.addLast(new LoggingHandler(LogLevel.DEBUG));
						p.addLast(handler);
					}
				});
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) b.bind(port).sync().channel();
		return serverSocketChannel;
	}
	
	@After
	public void afterAbstractServer() throws IOException{
		
		waitForAnyKeyToContinue();
	}

}
