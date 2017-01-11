package test.spring.restclient;

import java.io.IOException;

import org.junit.Test;

/**
 * @author wenchao.meng
 *
 *         Nov 2, 2016
 */
public class RedirectTemplate extends AbstractRestTemplateTest {

	@Override
	protected String getBasePath() {
		return "dir";
	}

	@Test
	public void testRedirecrt() throws InterruptedException, IOException {

		for (int i = 0; i < 5; i++) {

			String result = restTemplate.getForObject(getUrl("get/1"), String.class);
			System.out.println(result);

			System.in.read();
		}
	}

}
