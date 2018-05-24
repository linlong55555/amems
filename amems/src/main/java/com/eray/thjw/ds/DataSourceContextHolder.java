package com.eray.thjw.ds;

import enu.DataSource;

public class DataSourceContextHolder {  
	 
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
  
    public static void setDbType(DataSource dbType) {  
        contextHolder.set(dbType.name());  
    }  
  
    public static String getDbType() {  
        return ((String) contextHolder.get());  
    }  
  
    public static void clearDbType() {  
        contextHolder.remove();  
    }  
}  