package test.spring.restclient;


import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.AsyncRestTemplate;

/**
 * @author wenchao.meng
 *
 *         Jul 30, 2016
 */
public class AsyncRestTemplateTest extends AbstractRestTemplateTest{
	

	private String baseUrl = "http://localhost:2345"; 
	private String urlGet =  baseUrl + "/get/{id}";
	private String urlPost =  baseUrl + "/person/{id}";
	private String urlNothing = baseUrl + "/nothing";
	private AsyncRestOperations asyncRestOperations;
	
	@Before
	public void beforeAsyncRestTemplateTest(){
		asyncRestOperations = new AsyncRestTemplate();
	}
	

	@Test
	public void test405() throws IOException {
		
		asyncRestOperations.getForEntity(urlPost, String.class, 1).addCallback(new LogFuture());
		
	}
	
	@Test
	public void testRight(){
		
		asyncRestOperations.getForEntity(urlGet, String.class, 1).addCallback(new LogFuture());
		
	}
	
	class LogFuture implements ListenableFutureCallback<ResponseEntity<String>> {

		@Override
		public void onFailure(Throwable ex) {
			logger.error("[onFailure]", ex);
		}

		@Override
		public void onSuccess(ResponseEntity<String> result) {
			logger.error("[onSuccess]{}", result.getBody());
		}
	}
	
	@After
	public void afterAsyncRestTemplateTest() throws IOException{
		System.in.read();
	}
	
}
