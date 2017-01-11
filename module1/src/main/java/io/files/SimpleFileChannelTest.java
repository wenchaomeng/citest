package io.files;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

import org.junit.Before;
import org.junit.Test;

/**
 * @author wenchao.meng
 *
 * Nov 8, 2016
 */
public class SimpleFileChannelTest {
	
	
	private RandomAccessFile writeFile;
	private FileChannel channel;
	
	@Before
	public void beforeFileChannelTest() throws FileNotFoundException{
		
		writeFile = new RandomAccessFile("/opt/logs/test.log", "rw");
		channel = writeFile.getChannel();
	}
	
	
	@Test
	public void test() throws IOException{
		
		System.out.println(channel.toString());
		channel.close();
		System.out.println(channel.size());
	}
	
	@Test
	public void testReadOverFileSize() throws IOException{
		
		channel.write(ByteBuffer.wrap("12345".getBytes()));
		
		channel.transferTo(1, channel.size() - 2, new WritableByteChannel() {
			
			@Override
			public boolean isOpen() {
				return true;
			}
			
			@Override
			public void close() throws IOException {
				
			}
			
			@Override
			public int write(ByteBuffer src) throws IOException {
				
				int remain = src.remaining();
				byte[] dst = new byte[remain];
				src.get(dst);
				System.out.println(new String(dst));
				return dst.length;
			}
		});
	}

}
