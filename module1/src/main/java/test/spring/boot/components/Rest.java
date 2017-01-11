package test.spring.boot.components;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import simpleclass.Person;
import simpleclass.PersonInterface;
import test.spring.boot.conditional.Component1;

/**
 * @author wenchao.meng
 *
 *         Jul 30, 2016
 */
@RestController
public class Rest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public Rest(){
		System.out.println(getClass());
	}
	
	@ModelAttribute
	public void  getPerson(Model model, @RequestHeader(name = "header", required = false) HeaderInfo headerInfo){
		
		System.out.println("modelAttribute called... " + headerInfo);
		
		model.addAttribute("person", new Person("module", 1111));
		if(headerInfo != null){
			model.addAttribute(headerInfo);
		}
	}
	
	
	@RequestMapping(path="/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Person getPerson(@ModelAttribute("person") PersonInterface person, @PathVariable String id, @ModelAttribute Component1 empty){
		
		logger.info("[getPerson][given attribute name]");
		logger.info("[getPerson]{}, {}, {}", id, person, empty);
		return new Person("getPerson", 111); 
	}

	@RequestMapping(path="/header", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void getPerson(@ModelAttribute HeaderInfo headerInfo){
		System.out.println(headerInfo);
	}

	
	
	@RequestMapping(path = "/person/{id}", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String person(@PathVariable int id, Person person, @RequestHeader(name = "myheader", required = false) String headerValue){
		System.out.println("---" + id + "," + person);
		System.out.println("header:" + headerValue);
		return person.toString();
	}

	@RequestMapping(path = "/nothing", method = RequestMethod.POST)
	public void person(@RequestHeader(name = "myheader", required = false) HeaderInfo type){
		System.out.println("person called...");
		System.out.println(type);
	}
	

	@RequestMapping(path = "/exception", method = RequestMethod.POST)
	public void exception(){
		throw new IllegalStateException("illegal state");
	}

	@RequestMapping(path = "/path/print", method = RequestMethod.POST)
	public void pathPrint(HttpServletRequest request){
		
		System.out.println("uri:" + request.getRequestURI());
		System.out.println("url:" + request.getRequestURL());
		System.out.println("query string:" + request.getQueryString());
		
	}
	
//	@ExceptionHandler
//	@ResponseBody
//	public Person handleException(Exception e){
//		e.printStackTrace();
//		return new Person("exception", 1000);
//	}

	@ResponseBody
	@RequestMapping(path = "/exception", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> giveException(){
		
		ResponseEntity<Person> response = new ResponseEntity<Person>(new Person("exception", 1000), HttpStatus.BAD_GATEWAY);
		return response;
	}
}