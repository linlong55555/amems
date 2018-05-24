package com.eray.thjw.exception.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class ExceptionHandler extends SimpleMappingExceptionResolver{

	//日志记录器
	private  final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	
	@Override  
    protected ModelAndView doResolveException(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex) {  
        logger.error("", ex); 
		
        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request  
                .getHeader("X-Requested-With")!= null && request  
                .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {  
        	 String viewName = determineViewName(ex, request);
            // 如果不是异步请求  
        	if (viewName != null) {// JSP格式返回  
        		Integer statusCode = determineStatusCode(request, viewName);  
                if (statusCode != null) {  
                    applyStatusCodeIfPossible(request, response, statusCode);  
                }  
                return getModelAndView(viewName, ex, request); 
               
            } else {  
            	logger.error("没有处理此异常".concat(ex.getClass().toString()));
                return null;  
            }
            
        } else {
            try {  
                PrintWriter writer = response.getWriter();  
                writer.write(ex.getMessage());
                writer.flush();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            return null;  
        }
          
    }  
	
	
}
