package test.spring.boot.components;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import simpleclass.Person;
import test.spring.boot.conditional.Component1;

/**
 * @author wenchao.meng
 *
 *         Jul 30, 2016
 */
@RestController
@RequestMapping("/pool")
public class RestLongpoll {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private ExecutorService executors = Executors.newCachedThreadPool();

	public RestLongpoll() {
		System.out.println(getClass());
	}
	
	@RequestMapping(path = "/person", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Person getPerson() {
		return new Person("hi", 23); 
	}

	
	@RequestMapping(path = "/sleep", method = RequestMethod.GET)
	public ModelAndView getPerson(@PathVariable String id, @ModelAttribute Component1 empty) {

		logger.info("[getPerson]{}, {}, {}", id, empty);
		RedirectView view = new RedirectView("http://10.8.161.105:8080/health");
		view.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
		ModelAndView mv = new ModelAndView(view);

		return mv;
	}

	@RequestMapping(path = "/call", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Callable<String> call() {

		logger.info("[call]{}");

		return new Callable<String>() {

			@Override
			public String call() throws Exception {
				try {
					logger.info("[call][begin]{}");
					TimeUnit.SECONDS.sleep(5);
					return "nihao";
				} finally {
					logger.info("[call][end]{}");
				}
			}
		};
	}

	@RequestMapping(path = "/defer/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public DeferredResult<ModelAndView> defer(@PathVariable int id) {

		logger.info("[call]{}", id);
		final DeferredResult<ModelAndView> result = new DeferredResult<>(3L);
		if (id == 1) {
			result.setResult(createRedirect());
			return result;
		}
		
		executors.execute(new Runnable() {

			@Override
			public void run() {

				try {
					logger.info("[run][begin]{}");
					TimeUnit.SECONDS.sleep(6);
					result.setResult(createStringModelAndView("just return..."));
				} catch (Exception e) {
					logger.error("[run]", e);
				} finally {
					logger.info("[run][end]{}");
				}
			}

		});
		return result;
	}

	private ModelAndView createRedirect() {
		
		RedirectView view = new RedirectView("http://www.baidu.com");
		view.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
		return new ModelAndView(view);
	}

	private ModelAndView createStringModelAndView(String message) {
		return null;
	}

}