package com.eray.rest.enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 状态码
 * @CreateTime 2018年2月2日 下午2:13:19
 * @CreateBy 韩武
 */
public enum StatusCodeEnum {
	  SUCCESS("成功", "200")
	, FILE_NOT_FOUND("找不到资源", "400")
	, RESPONSE_FAIL("响应失败", "500")
	, PARAM_NOT_COMPLETE("输入参数不全", "501")
	, PLANE_NOT_EXIST("飞机不存在", "502")
	, DB_ACCESS_ERROR("数据库访问错误", "800")
	;
     
    private String name;
    private String id;

    
    private StatusCodeEnum(String name, String id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(String id) {
        for (StatusCodeEnum c : StatusCodeEnum.values()) {
            if (c.getId().equals(id)) {
                return c.name;
            }
        }
        return "";
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	StatusCodeEnum[] enums = StatusCodeEnum.values();
    	
    	for (StatusCodeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
    
}