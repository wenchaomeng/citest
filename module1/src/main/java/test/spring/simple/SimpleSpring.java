package test.spring.simple;

import org.springframework.context.annotation.ComponentScan;

/**
 * @author wenchao.meng
 *
 * Aug 11, 2016
 */
@ComponentScan
public class SimpleSpring {

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		System.out.println(String.format("%x", 15));
		
//		new AnnotationConfigApplicationContext(SimpleSpring.class);
	}

}
