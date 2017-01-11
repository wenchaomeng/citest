package jdk.concurrent;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Before;
import org.junit.Test;

/**
 * @author wenchao.meng
 *
 * Nov 14, 2016
 */
public class ScheduleTest {
	
	private AtomicLong count = new AtomicLong();
	private ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);
	
	@Before
	public void beforeScheduleTest(){
		
		scheduled.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(count.get());
			}
		}, 0, 1, TimeUnit.SECONDS);
	}
	
	@Test
	public void testSleep() throws IOException, InterruptedException{
		
		
		while(true){
			
			count.incrementAndGet();
			TimeUnit.MICROSECONDS.sleep(1);
		}
		
	}
	
	@Test
	public void testFixedDelay() throws IOException{
		
		scheduled.scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				count.incrementAndGet();
			}
		}, 0, 1, TimeUnit.MICROSECONDS);
		
		
		System.in.read();
	}

	@Test
	public void testFixedRate() throws IOException{
		
		scheduled.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				
				count.incrementAndGet();
			}
		}, 0, 1, TimeUnit.MICROSECONDS);
		
		System.in.read();
	}

}
