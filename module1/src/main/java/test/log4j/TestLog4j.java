package test.log4j;

import java.nio.charset.Charset;

import org.apache.logging.log4j.ThreadContext;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

/**
 * @author wenchao.meng
 *
 *         Sep 6, 2016
 */
public class TestLog4j {

	public static final String log4jConfig = "log4j-t.xml";
	private Logger logger;

	@Before
	public void beforeTestLog4() {

		System.setProperty("log4j.configurationFile", log4jConfig);
		logger = LoggerFactory.getLogger(getClass());
	}

	@Test
	public void testLookup() {

		Exception e = new Exception("message");
		e.printStackTrace();
		
	}

	@Test
	public void testLookupPlugin() {

		ThreadContext.put("loginId", "12345");

		logger.info("[info]", new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "statusCode",
				"responseBodyExample".getBytes(), Charset.defaultCharset()));

		logger.info("[info]", new Exception("simple exception"));
	}
	
	public static void main(String []argc){
		
		TestLog4j testLog4j = new TestLog4j();
		testLog4j.beforeTestLog4();
		testLog4j.testLookupPlugin();
	}
}
