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
public class ConditionalConfig1 {


	
	@Bean
	public ConditionClass getConditional(){
		System.out.println("create getConditional--------------------");

		return new ConditionClass();
	}


}
