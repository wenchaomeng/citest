package test.spring.boot.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * @author wenchao.meng
 *
 * Aug 10, 2016
 */
@Component
public class ApplicationListenerTest implements GenericApplicationListener{

	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("onApplicationEvent:" + event);
	}

	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	public boolean supportsEventType(ResolvableType eventType) {
		
//		boolean result = ContextRefreshedEvent.class.isAssignableFrom(eventType.getRawClass()); 
////		return true;
//		System.out.println("[supportsEventType]{}" + result + "," + eventType.getRawClass());
		return true;
	}

	public boolean supportsSourceType(Class<?> sourceType) {
		return true;
	}

}
