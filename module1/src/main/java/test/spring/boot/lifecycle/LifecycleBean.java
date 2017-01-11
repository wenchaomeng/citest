package test.spring.boot.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

/**
 * @author wenchao.meng
 *
 * Aug 30, 2016
 */
@Component
public class LifecycleBean {
	
	@PostConstruct
	public void construct(){
		System.out.println("LifecycleBean: construct");

	}
	
	@PreDestroy
	public void close(){
		System.out.println("LifecycleBean: close");
		
	}

}
