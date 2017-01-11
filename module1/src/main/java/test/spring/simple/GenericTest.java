package test.spring.simple;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wenchao.meng
 *
 * Sep 2, 2016
 */
public class GenericTest {
	
	public Set<? extends Number> function(){
		
		return new HashSet<Integer>();
		
	}

}
