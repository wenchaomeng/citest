package org.vc.go.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vc.go.AbstractTest;

/**
 * @author wenchao.meng
 *
 * Jan 6, 2017
 */
public class FileSizeTest extends AbstractTest{
	
	private RandomAccessFile randomAccessFile;
	private int writeSize = 1024;
	
	private String file;
	
	
	@Before
	public void beforeFileSizeTest() throws FileNotFoundException{
		
		file = getTestName();
		 randomAccessFile = new RandomAccessFile(file, "rw");
	}

	
	@Test
	public void testRegularFileSize() throws IOException{
		
		logger.info("[testRegularFileSize]{}", size());
		randomAccessFile.getChannel().write(ByteBuffer.wrap(randomString(writeSize).getBytes()));
		logger.info("[testRegularFileSize]{}", size());
		
	}

	private String size() throws IOException {
		return String.format("current:%d, new:%d, fileLen:%d", randomAccessFile.getChannel().size(), createAndGetSize(), new File(file).length());
	}

	
	private long createAndGetSize() throws FileNotFoundException, IOException{
		
		try(RandomAccessFile random = new RandomAccessFile(file, "rw")){
			
			return random.getChannel().size();
		}
	}

	@Test
	public void testTruncateFileSize() throws IOException{
		
		logger.info("[testTruncateFileSize]{}", size());
		randomAccessFile.getChannel().write(ByteBuffer.wrap(randomString(writeSize).getBytes()));
		
		logger.info("[testTruncateFileSize]{}", size());
		
		randomAccessFile.getChannel().truncate(writeSize - 40);
		logger.info("[testTruncateFileSize][after truncate]{}", size());
		
		
	}


	@After
	public void afterFileSizeTest(){
		close(randomAccessFile);
	}
}
