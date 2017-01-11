package test.spring.boot.bean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wenchao.meng
 *
 * Sep 5, 2016
 */
public class NewBean {
	
	@Autowired
	Apple apple;
	
	
	@PostConstruct
	public void postConstruct(){
		System.out.println(apple);
	}
}
