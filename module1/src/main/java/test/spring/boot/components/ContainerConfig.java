package test.spring.boot.components;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author wenchao.meng
 *
 * Jul 30, 2016
 */
@Component
public class ContainerConfig implements EmbeddedServletContainerCustomizer{

	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(2345);
	}

}
