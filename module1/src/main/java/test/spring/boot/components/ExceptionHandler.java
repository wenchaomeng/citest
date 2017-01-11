package test.spring.boot.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wenchao.meng
 *
 * Aug 1, 2016
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandler implements HandlerExceptionResolver{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("[resolveException]", ex);
		return null;
	}

}
