package io.netty.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.netty.AbstractClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;

/**
 * @author wenchao.meng
 *
 *         Nov 10, 2016
 */
public class FileChannelClient extends AbstractClient {

	private String fileName = System.getProperty("fileName", "/opt/logs/test.log");
	private FileChannel fileChannel;
	private int eachMessage = 1 << 20;
	private byte[] message;

	@SuppressWarnings("resource")
	@Before
	public void beforeFileChannelClient() throws FileNotFoundException {
		fileChannel = new RandomAccessFile(fileName, "rw").getChannel();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < eachMessage; i++) {
			sb.append('a');
		}
		message = sb.toString().getBytes();
	}

	@Test
	public void testright() throws InterruptedException, IOException {

		createClient(new FileChannelHandler(), new InetSocketAddress("localhost", getDefaultPort()));

	}

	@Test
	public void testReOpen() throws InterruptedException, IOException {

		List<Channel> channels = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			Channel channel = createClient(new FileChannelHandler(), new InetSocketAddress("localhost", getDefaultPort()));
			channels.add(channel);
		}
		
		waitForAnyKeyToContinue();
		
		for(Channel c : channels){
			c.close();
		}

	}

	@Test
	public void testClose() throws InterruptedException, IOException {

		fileChannel.close();
		createClient(new FileChannelHandler(), new InetSocketAddress("localhost", getDefaultPort()));

	}

	@Test
	public void testBigFile() throws IOException, InterruptedException {

		long size = fileChannel.size();
		ByteBuffer bytebuffer = ByteBuffer.allocate(eachMessage);
		bytebuffer.put(message);

		logger.info("[testBigFile][begin write]{}", size);
		for (long i = size; i < Integer.MAX_VALUE - eachMessage; i += eachMessage) {

			bytebuffer.flip();
			fileChannel.write(bytebuffer);
		}
		logger.info("[testBigFile][end write]");

		createClient(new FileChannelHandler(), new InetSocketAddress("localhost", getDefaultPort()));
	}

	private byte[] getBytes(int eachWriteSize) {

		return null;
	}

	class FileChannelHandler extends ChannelDuplexHandler {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {

			logger.info("[channelActive]{}", ctx.channel());
			File file = new File(fileName);
			file.length();

			ctx.channel().writeAndFlush(new DefaultFileRegion(fileChannel, 0, file.length()) {

				@Override
				protected void deallocate() {
					logger.info("[deallocate]{}", 0);
					super.deallocate();
				}

			}).addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {

					if (!future.isSuccess()) {
						logger.error("[operationComplete]", future.cause());
					}
				}
			});

			super.channelActive(ctx);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

			ByteBuf byteBuf = (ByteBuf) msg;
			byte[] dst = new byte[byteBuf.readableBytes()];
			byteBuf.readBytes(dst);
			logger.info("{}", new String(dst));
		}
		
		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			super.channelInactive(ctx);
			logger.info("[channelInactive]{}", ctx.channel());
		}
	}

	@After
	public void afterFileChannelClient() throws IOException {
		waitForAnyKeyToContinue();

	}

}
