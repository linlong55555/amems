package com.eray.rest.enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @Description 故障保留单状态
 * @CreateTime 2018年2月2日 下午5:08:13
 * @CreateBy 韩武	
 */
public enum DeferredDefectStatusEnum {
	
	APPROVED("已审批", 70)
	, COLSED("已关闭", 20)
	;
     
    private String name;
    private Integer id;

    
    private DeferredDefectStatusEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (DeferredDefectStatusEnum c : DeferredDefectStatusEnum.values()) {
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	DeferredDefectStatusEnum[] enums = DeferredDefectStatusEnum.values();
    	
    	for (DeferredDefectStatusEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
}
