package com.eray.util.source;
/**
 * 数据库动态切换
 * @author 
 *
 */
public class DBContextHolder {
	public static final String DATA_SOURCE = "dataSource";  
    public static final String DATA_SOURCE_SHIFT = "dataSourceShift";  
      
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
      
    public static void setDBType(String dbType) {  
        contextHolder.set(dbType);  
    }  
      
    public static String getDBType() {  
        return contextHolder.get();  
    }  
      
    public static void clearDBType() {  
        contextHolder.remove();  
    }  
}
