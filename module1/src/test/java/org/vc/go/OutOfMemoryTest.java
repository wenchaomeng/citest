package org.vc.go;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class OutOfMemoryTest {

	@Test
	public void test() {

		List<byte[]> ls = new LinkedList<byte[]>();
		for (int i = 0; i < 1024; i++) {
			ls.add(new byte[1024]);
		}

	}

}
