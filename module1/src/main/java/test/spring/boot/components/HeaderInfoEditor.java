package test.spring.boot.components;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author wenchao.meng
 *
 * Aug 3, 2016
 */
public class HeaderInfoEditor extends PropertyEditorSupport implements PropertyEditor{

	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		
		ObjectMapper om = new ObjectMapper();
		try{
			setValue(om.readValue(text, HeaderInfo.class));
		}catch(Exception e){
			throw new IllegalArgumentException("error format", e);
		}
	}
	
	@Override
	public String getAsText() {
		
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(getValue());
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("error format:" + getValue(), e);
		}
	}

}
