package com.eray.thjw.advice;

import org.aspectj.lang.JoinPoint;

import com.eray.thjw.dao.AuthMapper;
import com.eray.thjw.dao.UpMapper;
import com.eray.thjw.ds.DataSourceContextHolder;

import enu.DataSource;

public class MultipleDataSourceAdvice {

	public void before(JoinPoint jp) {
		 
		if (AuthMapper.class.isAssignableFrom(jp.getSignature().getDeclaringType())) {
			DataSourceContextHolder.setDbType(DataSource.DS_AUTH);
		}else if (UpMapper.class.isAssignableFrom(jp.getSignature().getDeclaringType())) {
			DataSourceContextHolder.setDbType(DataSource.DS_UP);
		}else{
			DataSourceContextHolder.setDbType(DataSource.DS_BUSINESS);
		}
	}
	
	public void after() {
		DataSourceContextHolder.clearDbType();
	}
}
