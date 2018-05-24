package enu.produce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description 故障保留保留类型
 * @CreateTime 2017年9月25日 下午2:55:26
 * @CreateBy 林龙
 */
public enum FailureKeepTypeEnum {
	A(1, "A"),
	B(2, "B"),
	C(3, "C"),
	D(4, "D"),
	CDL(5, "CDL"),
	OTHER(9, "其它"),
	;
	
	private Integer id;
    private String name;
    
    private FailureKeepTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (FailureKeepTypeEnum c : FailureKeepTypeEnum.values()) {
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
	 * @Description 枚举转listmap
	 * @CreateTime 2017-9-13 下午2:19:21
	 * @CreateBy 刘兵
	 * @return
	 */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	FailureKeepTypeEnum[] enums = FailureKeepTypeEnum.values();
    	
    	for (FailureKeepTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	            return o2.get("id").toString().compareTo(o1.get("id").toString()) ;
    	        }
    	    });
    	
    	return list;
	}
     
}
