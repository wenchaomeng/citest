package io.netty;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wenchao.meng
 *
 * Nov 10, 2016
 */
public class AbstractNetty {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected int getDefaultPort(){
		return Integer.parseInt(System.getProperty("port", "8080"));
	}

	protected void waitForAnyKeyToContinue() throws IOException {
		
		System.out.println("press any key to continue...");
		System.in.read();
	}

}
