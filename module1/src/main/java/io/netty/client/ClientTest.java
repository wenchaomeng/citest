package io.netty.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.junit.After;
import org.junit.Test;

import io.netty.AbstractClient;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wenchao.meng
 *
 *         Dec 9, 2016
 */
public class ClientTest extends AbstractClient {

	@Test
	public void testEchoClinet() throws InterruptedException {

		createClient(new EchoClientHandler(), new InetSocketAddress("localhost", getDefaultPort()));

	}

	public class EchoClientHandler extends ChannelDuplexHandler {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {

			logger.info("[channelActive]{}", ctx.channel());

			for (int i = 0; i < Integer.MAX_VALUE; i++) {

				ctx.channel().write(Unpooled.wrappedBuffer("111111111111111111111".getBytes()));
			}
			super.channelActive(ctx);
		}
	}

	@Test
	public void testActiveTime() throws InterruptedException {

		for (int i = 0; i < 1000; i++) {
			
			createClient(new TimeHandler(), new InetSocketAddress("10.2.54.228", 6000));
		}

	}

	public class TimeHandler extends ChannelDuplexHandler {

		private long createTime = System.currentTimeMillis();

		public TimeHandler() {
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			super.channelActive(ctx);
			
			logger.info("[channelActive]{}", ctx.channel());
			long activeTime = System.currentTimeMillis();
			if (activeTime - createTime > 100) {
				logger.info("[channelActive][too long]{}, {}", activeTime - createTime, ctx.channel());
			}

			ctx.channel().close();
		}

	}

	@After
	public void after() throws IOException {

		waitForAnyKeyToContinue();
	}

}
