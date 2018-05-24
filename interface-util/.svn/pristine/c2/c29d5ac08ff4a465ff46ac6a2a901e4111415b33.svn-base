package com.eray.rest.enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 启用状态
 * @CreateTime 2018年2月5日 上午11:43:18
 * @CreateBy 韩武
 */
public enum EnableEnum {
	  ENABLED("启用", "YES")
	, DISABLED("禁用", "NO")
	;
     
    private String name;
    private String id;

    
    private EnableEnum(String name, String id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(String id) {
        for (EnableEnum c : EnableEnum.values()) {
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
    	EnableEnum[] enums = EnableEnum.values();
    	
    	for (EnableEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
    
}