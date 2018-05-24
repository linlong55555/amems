package com.eray.thjw.mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/** 
 * @Description jackson日期解析
 * @CreateTime 2017年10月11日 上午10:46:40
 * @CreateBy 韩武	
 */
public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

	@Override  
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {  
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");  
        String date = jp.getText();
        if(StringUtils.isNotBlank(date)){
        	try { 
        		if(date.contains(":")){
        			return format1.parse(date);  
        		}else{
        			return format2.parse(date);  
        		}
            } catch (ParseException e) {  
                return null;
            }
        }
        return null;
    }  
}
