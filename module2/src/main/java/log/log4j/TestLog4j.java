package log.log4j;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wenchao.meng
 *
 * Oct 25, 2016
 */
public class TestLog4j {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Test
	public void testLog(){
		
		logger.info("[testLog]");
	}
	
	@Test
	public void test() throws InterruptedException{
		
		for(int i=0;i<Integer.MAX_VALUE;i++){
			logger.info("nihao-{}", i);
			TimeUnit.MILLISECONDS.sleep(100);
		}
	}
	
	@Test
	public void testPathMatcher(){
		
		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:{**/*.txt, *.txt}");
		Path path1 = Paths.get("a/a.txt");
		System.out.println(pathMatcher.matches(path1));

		Path path2 = Paths.get("a.txt");
		System.out.println(pathMatcher.matches(path2));

		
		
	}
}
