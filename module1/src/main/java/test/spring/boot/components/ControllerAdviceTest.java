package test.spring.boot.components;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author wenchao.meng
 *
 * Aug 4, 2016
 */
@ControllerAdvice(assignableTypes = Rest.class)
public class ControllerAdviceTest {
	
	@InitBinder
	public void init(WebDataBinder webDataBinder){
		System.out.println("init called!!");
	}

}
