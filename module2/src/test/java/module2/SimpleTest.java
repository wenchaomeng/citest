package module2;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author wenchao.meng
 *
 * Nov 8, 2016
 */
public class SimpleTest extends AbstractTest{
	
	
	@Test
	public void simpleTest(){
		System.out.println("hello");
	}
	
	@Test
	public void testScheduled(){
		
		ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(1);
		scheduled.setKeepAliveTime(10, TimeUnit.SECONDS);
		scheduled.allowCoreThreadTimeOut(true);
		
		
		scheduled.schedule(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("running");
			}
		}, 1, TimeUnit.SECONDS);

		System.out.println("end");
	}
}
