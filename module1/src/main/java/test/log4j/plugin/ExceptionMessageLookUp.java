package test.log4j.plugin;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * @author wenchao.meng
 *
 * Oct 31, 2016
 */
@Plugin(name = "ex", category = StrLookup.CATEGORY)
public class ExceptionMessageLookUp implements StrLookup{
	
	public static final String HTTP_BODY = "httpbody";

	@Override
	public String lookup(String key) {
		return lookup(null, key);
	}

	@Override
	public String lookup(LogEvent event, String key) {
		
		if(event == null){
			return "";
		}
		
		if(key.equalsIgnoreCase(HTTP_BODY)){
			
			Throwable th = event.getThrown();
			if(th instanceof HttpStatusCodeException){
				return ((HttpStatusCodeException) th).getResponseBodyAsString(); 
			}
		}
		
		return "";
	}
}
