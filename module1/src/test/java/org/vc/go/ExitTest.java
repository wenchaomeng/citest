package org.vc.go;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class ExitTest {

	@Test
	public void test() {

		// System.exit(0);
	}

	@Test
	public void testXmx() {
		
		String commandLineInput = System.getProperty("sun.java.command");
		Pattern pattern = Pattern.compile("-Xmx(\\w*)");
		Matcher matcher = pattern.matcher(commandLineInput);
		while (matcher.find()) {
			System.out.println(matcher.group(1));
		}
		System.out.println("max:" + getMb(Runtime.getRuntime().maxMemory()));
		System.out.println("total:" + getMb(Runtime.getRuntime().totalMemory()));
		System.out.println("free:" + getMb(Runtime.getRuntime().freeMemory()));
	}

	private long getMb(long maxMemory) {
		
		return maxMemory/(1<<20);
	}

}
