package test.spring.restclient;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author wenchao.meng
 *
 * Nov 2, 2016
 */
public class AbstractRestTemplateTest {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected RestTemplate restTemplate;
	

	@Before
	public void beforeAbstractRestTemplateTest() {
		
		HttpClient httpClient = HttpClientBuilder.create().setMaxConnPerRoute(1).build();
		
		ClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		restTemplate = new RestTemplate(factory);
	}
	
	protected  String getUrl(String path){
		return getHost() + "/" + getBasePath() + "/" + path;
	}

	protected String getBasePath() {
		return "";
	}

	protected String getHost() {
		return "http://localhost:2345";
	} 


}
