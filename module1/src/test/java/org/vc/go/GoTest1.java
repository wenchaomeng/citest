package org.vc.go;

import java.net.URL;

import org.junit.Test;


/**
 * @author wenchao.meng
 *
 * Jun 16, 2016
 */
public class GoTest1 extends AbstractTest{
	
	@Test
	public void testGo(){
		
	}
	
	@Test
	public void testFile(){
		
		URL url = getClass().getResource("GoTest.class");
		System.out.println("url:" + url);
		url = getClass().getResource("keeper.xml");
		System.out.println("url:" + url);
		url = getClass().getResource("keeper1.xml");
		System.out.println("url:" + url);
}

}
