package jdk.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author wenchao.meng
 *
 * Nov 9, 2016
 */
public class SemaphoreTest {
	
	@Test
	public void test() throws InterruptedException{
		
		int count = 3;
		final Semaphore semaphore = new Semaphore(-count);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				for(int i=0;;i++){
					try {
						System.out.println("release:" + (i + 1));
						semaphore.release();
						TimeUnit.SECONDS.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		
		semaphore.acquire();

	}

}
