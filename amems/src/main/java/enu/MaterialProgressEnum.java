package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description 航材进度枚举
 * @develop date 2016.10.10
 */
public enum MaterialProgressEnum {
	SAVE(1, "保存"),
	SUBMIT(2, "提交"),
	AUDITING(3, "审核中"),
	AUDITED(4, "已审核"),
	CONTRACTED(5, "已有合同号"),
	ARRIVED_NO_STORAGE(6, "到货未入库"),
	PART_STORAGED(7, "部分入库"),
	ALL_STORAGED(8, "全部入库"),
	STOP(9, "指定结束")
	;
	
	
	private Integer id;
    private String name;
    
    private MaterialProgressEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (MaterialProgressEnum c : MaterialProgressEnum.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name;
            }
        }
        return "";
    }
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	 /**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	MaterialProgressEnum[] enums = MaterialProgressEnum.values();
    	
    	for (MaterialProgressEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	 /*Collections.sort(list, new Comparator<Map<String, Object>>() {
    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	            return o1.get("name").toString().compareTo(o2.get("name").toString()) ;
    	        }
    	    });*/
    	
    	return list;
	}
     
}
