package test.spring.boot;

import java.net.URL;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class Application {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) throws Exception {

		URL url = Application.class.getClassLoader().getResource(".");
		
		SpringApplication application = new SpringApplication(Application.class);
		application.setRegisterShutdownHook(false);
		final ConfigurableApplicationContext applicationContext = application.run(args);
		
		System.out.println("after run");
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("close context:" + applicationContext);
				applicationContext.close();
			}
		}));
		
	}
}