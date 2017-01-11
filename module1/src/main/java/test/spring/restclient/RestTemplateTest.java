package test.spring.restclient;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import simpleclass.Person;
import test.spring.boot.components.HeaderInfo;
import test.spring.boot.components.HeaderType;

/**
 * @author wenchao.meng
 *
 *         Jul 30, 2016
 */
public class RestTemplateTest extends AbstractRestTemplateTest{
	

	private String baseUrl = "http://localhost:2345"; 
	private String url =  baseUrl + "/person/{id}";
	private String urlNothing = baseUrl + "/nothing";
	

	@Test
	public void testBuild() {

		Person value = new Person("eric", 1);
		MappingJacksonValue mappingJackson = new MappingJacksonValue(value);
		
		HttpHeaders requestHeaders = new HttpHeaders(); 
		requestHeaders.set("myheader", "MyValue");
//		HttpEntity<MappingJacksonValue> entity = new HttpEntity<MappingJacksonValue>(, requestHeaders);
		HttpEntity<Person> entity = new HttpEntity<Person>(value, requestHeaders);
		
		for(int i=0;i<3;i++){
			restTemplate.postForEntity(url, entity, String.class, 11);
		}
		
	}
	
	@Test
	public void testHeader(){

		String dest = baseUrl + "/header";
		HttpHeaders requestHeaders = new HttpHeaders(); 
		requestHeaders.set("header", serial(new HeaderInfo(HeaderType.FORWARDING, 10000000)));
		HttpEntity<String> entity = new HttpEntity<String>("nihao", requestHeaders);
		restTemplate.postForEntity(dest, entity, String.class);
	}
	
	
	@Test
	public void testGetException(){
		
		try{
			Person ret = restTemplate.getForObject("http://localhost:2345/exception", Person.class);
		}catch(HttpStatusCodeException e){
			logger.error("[error]" + e.getResponseBodyAsString(), e);
		}
	}

	private String serial(HeaderInfo headerInfo) {
		
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(headerInfo);
		} catch (JsonProcessingException e) {
			throw  new IllegalStateException(headerInfo.toString(), e);
		}
	}
}
