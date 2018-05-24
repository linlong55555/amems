package com.eray.rest.advice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.eray.rest.annotation.RestInterface;
import com.eray.rest.annotation.RestInterface.RestInfo;
import com.eray.rest.enu.StatusCodeEnum;

/** 
 * @Description rest接口日志
 * @CreateTime 2018年2月5日 下午2:35:42
 * @CreateBy 韩武	
 */
//声明这是一个组件
@Component
//声明这是一个切面Bean
@Aspect
public class RestLogAdvice {
	
	private static Logger logger = LoggerFactory.getLogger(RestLogAdvice.class);

	/**
	 * @Description 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	 * @CreateTime 2018年2月5日 下午2:42:22
	 * @CreateBy 韩武
	 */
	@Pointcut("execution(* com.eray.rest.service.impl.*.*(..))")
	public void aspect() {
		
	}
	
	/**
	 * @Description  配置环绕通知,使用在方法aspect()上注册的切入点
	 * @CreateTime 2018年2月5日 下午2:44:02
	 * @CreateBy 韩武
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("aspect()")
    public Object around(JoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object object = null;
        try {
        	// 打印日志
        	object = logDetail(joinPoint);
		} catch (Exception e) {
			RestInfo restInfo = getRestInfo(joinPoint);
			logger.error("{}接口执行失败！", restInfo.getName());
			e.printStackTrace();
		}
        long end = System.currentTimeMillis();
        logger.info("接口共耗时：{}ms\r\n", end - start);
        return object;
    }
	
	/**
	 * @Description 打印日志
	 * @CreateTime 2018年2月5日 下午4:16:43
	 * @CreateBy 韩武
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	private Object logDetail(JoinPoint joinPoint) throws Throwable{
		
		// 返回值
		Object object = null;
		
		// 获取注解
        RestInfo restInfo = getRestInfo(joinPoint);
        ProceedingJoinPoint tempJoinPoint = (ProceedingJoinPoint)joinPoint;
        
        // 获取参数
        Object[] params = tempJoinPoint.getArgs();
        // 接口url
        String interfaceUrl = restInfo != null ? restInfo.getUrl() : "未定义";
        // 接口名称
        String interfaceName = restInfo != null ? restInfo.getName() : "未定义";
        
        // 日志 - 执行程序之前
    	logger.info("****************{}同步接口开始****************\r\n", interfaceName);
    	logger.info("请求地址为：{}\r\n", interfaceUrl);
    	if(params != null && params.length == 1){
    		logger.info("接收的参数为：{}\r\n", params[0].toString());
    		logger.info("请求参数为：{}\r\n", JSON.toJSONString(params[0]));
    	}
        
        // 执行程序
        object = tempJoinPoint.proceed();
        
        // 日志 - 返回消息
        if(object != null && object instanceof String && StringUtils.isNotBlank(String.valueOf(object))){
        	String responseMsg = (String)object;
        	logger.info("返回消息为：{}\r\n", responseMsg);
    		Map<String, Object> record = null;
    		try {
    			if(StringUtils.isNotBlank(responseMsg)){
    				record = JSON.parseObject(responseMsg, new TypeReference<HashMap<String,Object>>(){});
    			}
    		} catch (JSONException e) {
    			logger.error("返回消息异常，无法解析！\r\n");
    		}
    		if(record != null){
    			// 响应码
    			String resultCode = String.valueOf(record.get("resultCode"));
    			// 消息
    			String msg = record.get("msg") != null ? String.valueOf(record.get("msg")) : "";
    			if(StringUtils.isNotBlank(resultCode)){
    				
    				if(StatusCodeEnum.SUCCESS.getId().equals(resultCode)){
    					logger.info("接口运行成功!\r\n");
    					logger.info("返回码：{}({})\r\n", resultCode, StatusCodeEnum.getName(resultCode));
    				}else{
    					logger.error("接口运行失败!\r\n");
    					logger.error("返回码：{}({})\r\n", resultCode, StatusCodeEnum.getName(resultCode));
    					logger.error("错误消息：{}\r\n", msg);
    				}
    			}
    		}
        }else {
        	logger.info("无返回消息!\r\n");
        }
        
        // 日志 - 执行程序之后
    	logger.info("****************{}同步接口结束****************\r\n", interfaceName);
        
        return object;
	}
	
	/**
	 * @Description 获取注解
	 * @CreateTime 2018年2月5日 下午3:30:07
	 * @CreateBy 韩武
	 * @param joinPoint
	 * @return
	 */
	private RestInfo getRestInfo(JoinPoint joinPoint){
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		RestInterface restInterface = method.getAnnotation(RestInterface.class);
		return restInterface != null ? restInterface.restInfo() : null;
	}
}
