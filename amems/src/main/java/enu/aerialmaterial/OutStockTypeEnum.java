package enu.aerialmaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出库状态枚举
 * @author xu.yong
 *
 */
public enum OutStockTypeEnum {
	TYPE0(0, "其他"),
	TYPE1(1, "领用出库"),
	TYPE2(2, "送修出库"),
	TYPE3(3, "归还出库"),
	TYPE4(4, "借调出库"),
	TYPE5(5, "报废出库"),
	TYPE6(5, "出库");
	
	
	private Integer id;
    private String name;
    
    private OutStockTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (OutStockTypeEnum c : OutStockTypeEnum.values()) {
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
    	OutStockTypeEnum[] enums = OutStockTypeEnum.values();
    	
    	for (OutStockTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	Collections.sort(list, new Comparator<Map<String, Object>>() {
	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	            return Integer.valueOf(o1.get("id").toString()).compareTo(Integer.valueOf(o2.get("id").toString())) ;
	        }
	    });
    	
    	return list;
	}
}
