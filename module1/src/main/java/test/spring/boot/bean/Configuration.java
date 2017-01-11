package test.spring.boot.bean;

import org.springframework.context.annotation.Bean;

/**
 * @author wenchao.meng
 *
 * Sep 5, 2016
 */
@org.springframework.context.annotation.Configuration
public class Configuration {
	
	@Bean
	public NewBean createBean(){
		System.out.println("createBean");
		return new NewBean();
	}

}
