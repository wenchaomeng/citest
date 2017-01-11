package org.vc.go;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wenchao.meng
 *
 * Aug 31, 2016
 */
public class Log4jTest1 {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Test
	public void test(){
		logger.info("[test]");
	}

}
