package test.spring.boot.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * @author wenchao.meng
 *
 *         Jul 30, 2016
 */
@Component
public class TestBean {
	
	@Autowired
	public TestBean(ApplicationArguments args) {
		
		boolean debug = args.containsOption("debug");
		List<String> files = args.getNonOptionArgs();
		// if run with "--debug logfile.txt" debug=true, files=["logfile.txt"]
		
		for(String name : args.getOptionNames()){
			System.out.println(String.format("%s : %s", name, args.getOptionValues(name)));
		}
		
	}
}