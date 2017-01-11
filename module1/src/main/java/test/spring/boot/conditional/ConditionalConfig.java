package test.spring.boot.conditional;


import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author wenchao.meng
 *
 * Aug 10, 2016
 */
@Configuration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
public class ConditionalConfig {


	@Bean
	@ConditionalOnMissingBean(ConditionClass.class)
	public Component1 getComponent1(){
		System.out.println("create component1--------------------");
		return new Component1();
	}
	

}
