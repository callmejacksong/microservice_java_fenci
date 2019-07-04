package com.melink.microservice.propertyeditor;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePropertyEditor extends PropertiesEditor {

	private Logger logger = LoggerFactory.getLogger(DatePropertyEditor.class);
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(!StringUtils.hasText(text)){
			setValue(null);
		}
		else{
			try{
				setValue(DateUtils.parseDate(text, new String[]{"yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
						"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"}));
			}
			catch(Exception ex){
				setValue(null);
				logger.error("Parse Date Property: [" + text +"] Failed.", ex);
			}
		}
	}
	
	@Override
	public String getAsText() {
		Date date = (Date)getValue();
		try{
			return sdf1.format(date);
		}catch(Exception ex){
			return sdf.format(date);
		}
	}
}
