package io.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wenchao.meng
 *
 *         Nov 23, 2016
 */
public class HeavyFileChannelTest {

	private static Logger logger = LoggerFactory.getLogger(HeavyFileChannelTest.class);

	private ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(3);

	private String dir = System.getProperty("dir", "/opt/data/testdata");
	private String fileName = System.getProperty("file", "test.log");
	private Long fileSizeRotate = Long.parseLong(System.getProperty("fileSizeRotate", String.valueOf(Long.MAX_VALUE)));
	private int writtenSize = Integer.parseInt(System.getProperty("size", "102400"));
	private int sleepMilli = Integer.parseInt(System.getProperty("sleep", "1"));
	private int logMilli = Integer.parseInt(System.getProperty("log", "10"));
	private boolean flush = Boolean.parseBoolean(System.getProperty("force", "false"));
	private long max = 0;
	private int logMaxInterval = Integer.parseInt(System.getProperty("logMax", "5"));

	public static void main(String[] args) throws IOException, InterruptedException {

		new HeavyFileChannelTest().start();
	}

	@SuppressWarnings("resource")
	private void start() throws FileNotFoundException, IOException, InterruptedException {

		startLogMax();

		byte[] data = newData(writtenSize).getBytes();
		int fileIndex = 0;

		makeParentDirIfNotExist();

		while (true) {

			File currentFile = new File(dir, fileName + "-" + fileIndex);
			logger.info("[start][rotate]{}", currentFile);
			try (FileChannel fileChannel = new RandomAccessFile(currentFile, "rw").getChannel()) {

				long written = 0;
				while (true) {

					long begin = System.nanoTime();
					fileChannel.write(ByteBuffer.wrap(data));
					long end = System.nanoTime();

					long timeUsed = (end - begin) / 1000000;
					delay(timeUsed);

					if (flush) {
						fileChannel.force(false);
					}
					written += data.length;
					if (written >= fileSizeRotate) {
						break;
					}
					TimeUnit.MILLISECONDS.sleep(sleepMilli);
				}
			}
			fileIndex++;
		}
	}

	private void makeParentDirIfNotExist() {
		File dirMake = new File(dir);
		if (!dirMake.exists()) {
			if (!dirMake.mkdirs()) {
				throw new IllegalStateException("make dir fail:" + dir);
			} else {
				logger.info("[make dir]{}", dir);
			}
		}
	}

	private void delay(long timeUsed) {

		if (timeUsed > logMilli) {
			logger.info("[too long]{}", timeUsed);
		}
		if (timeUsed > max) {
			max = timeUsed;
		}
	}

	private void startLogMax() {
		scheduled.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				logger.info("[startLogMax]{}", max);
				max = 0;
			}
		}, logMaxInterval, logMaxInterval, TimeUnit.SECONDS);
	}

	private static String newData(int size) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append("c");
		}
		return sb.toString();
	}
}
