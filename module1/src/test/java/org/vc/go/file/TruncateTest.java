package org.vc.go.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vc.go.AbstractTest;

/**
 * @author wenchao.meng
 *
 *         Jan 4, 2017
 */
public class TruncateTest extends AbstractTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private int dataSize = 1 << 10;
	private int trancateSize = 40;
	private boolean equalPrint = Boolean.parseBoolean(System.getProperty("equalPrint", "false"));
	private String file = "file.log";
	private RandomAccessFile randomAccessFile;
	private Object truncateLock = new Object();

	
	@Before
	public void beforeTruncateTest() throws FileNotFoundException{
		
		file = getTestName();
		 randomAccessFile = new RandomAccessFile(file, "rw");
	}
	
	@Test
	public void start() throws IOException {

		FileChannel fileChannel = randomAccessFile.getChannel();

		scheduled.scheduleAtFixedRate(new Runnable() {

			private FileChannel fileChannel;
			private RandomAccessFile randomAccessFile;
			
			{
				randomAccessFile = new RandomAccessFile(file, "rw");
				fileChannel = randomAccessFile.getChannel();
			}

			@Override
			public void run() {
				try {
					synchronized (truncateLock) {
						long size = fileChannel.size();
						long sizeFile = new File(file).length();
						if (size > dataSize || (size == dataSize && equalPrint)) {
							logger.warn("[run]channel:{}, file:{}, dataSize:{}", size, sizeFile, dataSize);
						}
					}
				} catch (Throwable e) {
					logger.error("[run]", e);
				}finally{
				}
			}
		}, 0, 1, TimeUnit.MICROSECONDS);

		for (int i = 0; i < 1 << 5; i++) {
			
			long fileSize = 0;
			long leftSize = 0;
			try {
				fileChannel.write(ByteBuffer.wrap(randomString(dataSize).getBytes()));
				fileSize = fileChannel.size();
				leftSize =  fileSize - trancateSize;
				logger.info("[begin truncate]{}", leftSize);
				synchronized (truncateLock) {
					fileChannel.truncate(leftSize);
				}
				logger.info("[end truncate]{}", leftSize);
				sleep(1);
				logger.info("[begin truncate][0]");
				fileChannel.truncate(0);
				logger.info("[end truncate][0]");
			} catch (Throwable th) {
				logger.error("[start]" + leftSize + "," + fileSize, th);
			}
		}
	}

	
}
