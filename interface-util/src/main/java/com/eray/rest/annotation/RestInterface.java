package com.eray.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eray.rest.common.SysConfig;

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD) 
public @interface RestInterface {
	RestInfo restInfo() ;
    public enum RestInfo{ 
    	DEFERRED_DEFECT("1000", "故障保留单", SysConfig.HHYK_DD_PATH, SysConfig.REST_HHYK)
    	,PLANE_PARKING("1001", "飞机停场", SysConfig.HHYK_PP_PATH, SysConfig.REST_HHYK)
    	;
    	private String bh;	// 编号
    	private String name;	// 名称
    	private String path;	// 路径
    	private String type;	// 接口类型
    	
    	private RestInfo(String bh, String name, String path, String type) {
			this.setBh(bh);
			this.setName(name);
			this.setPath(path);
			this.setType(type);
		}
		public String getBh() {
			return bh;
		}
		public void setBh(String bh) {
			this.bh = bh;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getUrl(){
			if(SysConfig.REST_HHYK.equals(type)){
				return SysConfig.getHhykUrl() + path;
			}
			return "";
		}
    };

}
