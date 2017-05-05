package org.vc.go;



import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * @author wenchao.meng
 *
 * 2016年3月28日 下午5:44:47
 */
public class AbstractTest {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected ExecutorService executors = Executors.newCachedThreadPool();

	protected ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(4);
	
	@Rule
	public TestName name = new TestName();
	
	private static Properties properties = new Properties();
	
	private Properties orginProperties;
	
	
	@Before
	public void beforeAbstractTest() throws Exception{

		orginProperties = (Properties) System.getProperties().clone();
		setProperties();

		logger.info(remarkableMessage("[begin test][{}]{}") , getClass().getSimpleName(), name.getMethodName());

		logger.info("[beforeAbstractTest][process]{}", ManagementFactory.getRuntimeMXBean().getName());

	}
	
	protected String getTestName(){
		return name.getMethodName();
	}

	protected String getTestFile(){
		return getTestFileDir() + "/" + currentTestName();
	}


	protected String getTestFileDir() {

		String userHome = getUserHome();
		String result = userHome + "/tmp/citest";
		return result + "/" + getClass().getSimpleName() + "-" + currentTestName();
	}

	public static String getUserHome() {

		return System.getProperty("user.home");
	}


	protected void setProperties() {
		
	}

	protected boolean deleteTestDir() {
		return true;
	}

	public static String randomString(){
		
		return randomString(1 << 10);
	}
	
	public static String randomString(int length){
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < length ; i++){
			sb.append((char)('a' + (int)(26*Math.random())));
		}
		
		return sb.toString();
		
	}
	
	
	protected String getTestFileDir(){
		
		String userHome = getUserHome();
		String result = userHome + "/test";
		
		String testDir = properties.getProperty("test.file.dir");
		if(testDir != null){
			result = testDir.replace("~", userHome);
		}
		return result + "/" + currentTestName();
	}
	
	public static String getUserHome(){
		
		return System.getProperty("user.home");
	}

	
	protected void sleepSeconds(int seconds){
		sleep(seconds * 1000);
	}

	protected void sleepIgnoreInterrupt(int time) {
		long future = System.currentTimeMillis() + time;

		while(true){
			long left = future - System.currentTimeMillis();
			if(left <= 0){
				break;
			}
			if(left > 0){
				try {
					TimeUnit.MILLISECONDS.sleep(left);
				} catch (InterruptedException e) {
				}
			}
		}
		
	}

	protected void sleep(int miliSeconds){
		
		try {
			TimeUnit.MILLISECONDS.sleep(miliSeconds);
		} catch (InterruptedException e) {
		}
	}

	protected String readFileAsString(String fileName, Charset charset) {
		
		FileInputStream fins = null;
		try {
			byte []data = new byte[2048];
			ByteArrayOutputStream baous = new ByteArrayOutputStream();
			fins = new FileInputStream(new File(fileName));
			
			while(true){
				int size = fins.read(data);
				if(size > 0){
					baous.write(data, 0, size);
				}
				if(size == -1){
					break;
				}
			}
			return new String(baous.toByteArray(), charset);
		} catch (FileNotFoundException e) {
			logger.error("[readFileAsString]" + fileName, e);
		} catch (IOException e) {
			logger.error("[readFileAsString]" + fileName, e);
		}finally{
			if(fins != null){
				try {
					fins.close();
				} catch (IOException e) {
					logger.error("[readFileAsString]", e);
				}
			}
		}
		return null;
	}
	
	protected String currentTestName(){
		return name.getMethodName();
	} 
	
	public static int portUsable(int fromPort){
		
		for(int i=fromPort;i<fromPort + 100;i++){
			if(isUsable(i)){
				return i;
			}
		}
		
		throw new IllegalStateException("unfonud usable port from %d" + fromPort);
	}

	
	public static int randomPort(){
		return randomPort(10000, 20000); 
	}
	
	
	/**
	 * find an available port from min to max
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomPort(int min, int max) {

		int i = min;
		for(;i<=max;i++){
			if(isUsable(i)){
				return i;
			}
		}
		
		throw new IllegalStateException(String.format("random port not found:(%d, %d)", min , max));
	}
	
	private static boolean isUsable(int port) {
		
		try(ServerSocket s = new ServerSocket()){
			s.bind(new InetSocketAddress(port));
			return true;
		} catch (IOException e) {
		}
		return false;
	}

	protected static int randomInt(int begin, int end){
		return (int) (begin + Math.random() * (end - begin));
	}

	protected Integer randomInt() {
		
		return (int)(Math.random() * Integer.MAX_VALUE);
	}

	protected String remarkableMessage(String msg) {
		return String.format("--------------------------%s--------------------------\r\n", msg);
	}

	protected void waitForAnyKeyToExit() throws IOException{
		System.out.println("type any key to exit..................");
		waitForAnyKey();
	}

	protected void waitForAnyKey() throws IOException {
		System.in.read();
	}

	
	public static int defaultZkPort(){
		return 2181;
	}

	public static int defaultMetaServerPort(){
		return 9747;
	}

	protected void close(Closeable randomAccessFile) {
		
		try {
			randomAccessFile.close();
		} catch (IOException e) {
			logger.error("[close]" + randomAccessFile, e);
		}
	}

	@After
	public void afterAbstractTest() throws Exception{

			logger.info(remarkableMessage("[end   test][{}]{}"), getClass().getSimpleName(), name.getMethodName());

		System.setProperties(orginProperties);
	}

}
