package test.spring.boot.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import simpleclass.Person;

/**
 * @author wenchao.meng
 *
 *         Jul 30, 2016
 */
@RestController
@RequestMapping("/dir")
public class RedirectRest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public RedirectRest(){
		System.out.println(getClass());
	}
	
	@RequestMapping(path="/get/{id}", method = RequestMethod.GET)
	public ModelAndView getPerson(@PathVariable int id){
		
		logger.info("[getPerson]{}, {}, {}", id);
		if( id == 1 ){
			RedirectView view = new RedirectView("http://10.8.161.105:8080/health");
			view.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
			ModelAndView mv = new ModelAndView(view);
			return mv;
		}
		
		MappingJackson2JsonView view = new MappingJackson2JsonView();
//		view.setModelKey("person");
		ModelAndView mv = new ModelAndView(view, "person", new Person("json", 28));
		return mv;
	}
}