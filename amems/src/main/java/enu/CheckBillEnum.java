package enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description 状态枚举
 * @develop date 2016.08.18
 */
public enum CheckBillEnum {
	
	APPROVE("已批准", 3)
	 
	;
     
    private String name;
    private Integer id;

    
    private CheckBillEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (CheckBillEnum c : CheckBillEnum.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name;
            }
        }
        return "";
    }
    
    /**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	CheckBillEnum[] enums = CheckBillEnum.values();
    	
    	for (CheckBillEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
    
    public static void main(String[] args) {
    	
    	
    	System.out.println(CheckBillEnum.enumToListMap());
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
    
}