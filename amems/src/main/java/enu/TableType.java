package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuchao
 * @description 
 */
public enum TableType {
	MASTER(1,"主表"),
	SLAVE(2, "从表")
	;
	
	private Integer id;
    private String name;
    
    private TableType(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (TableType c : TableType.values()) {
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
    	TableType[] enums = TableType.values();
    	
    	for (TableType enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	            return o1.get("name").toString().compareTo(o2.get("name").toString()) ;
    	        }
    	    });
    	
    	return list;
	}
    
    public static Boolean contain(String type) {
    	Boolean result = Boolean.FALSE;
    	if (!"".equals(type) && type.toUpperCase().equals(TableType.MASTER.toString())|| type.toUpperCase().equals(TableType.SLAVE.toString())) {
    		result = Boolean.TRUE;
		}
    	return result;
	}
    
    public static Boolean notContain(String type) {
    	return !contain(type);
	}
    
    public static Boolean isMaster(String type) {
    	Boolean result = Boolean.FALSE;
    	if (type!=null && type.toUpperCase().equals(TableType.MASTER.toString())) {
    		result = Boolean.TRUE;
		}
    	return result;
	}
    
     
}
