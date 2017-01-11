package org.vc.go;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author wenchao.meng
 *
 * Nov 9, 2016
 */
public class MemoryTest extends AbstractTest{

	
	@Test
	public void testXmx() throws InterruptedException {
		
		scheduled.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				
				logger.info("max:{}, total:{}, free:{}", getMb(Runtime.getRuntime().maxMemory()),
						getMb(Runtime.getRuntime().totalMemory()),
						getMb(Runtime.getRuntime().freeMemory())
						);
				
			}
		}, 0, 1, TimeUnit.SECONDS);
		
		userMemoryPerSecond(1);
		TimeUnit.SECONDS.sleep(10);
	}

	private void userMemoryPerSecond(final int memoryMb) {
		
		final List<byte[]> data = new LinkedList<>();
		
		scheduled.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try{
					data.add(new byte[memoryMb * (1 << 20)]);
				}catch(Throwable th){
					logger.error("[add]", th);
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
		
	}

	private String getMb(long maxMemory) {
		
		return String.format("%dMb", maxMemory/(1<<20));
	}

}
