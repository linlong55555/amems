package enu.aerialmaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存状态枚举
 * @author xu.yong
 *
 */
public enum StockStatusEnum {
	RECEIVED(1, "收货"),
	NORMAL(2, "可用"),
	LOCKED(3, "冻结"),
	SCRAP(4, "待报废"),
	SEQUESTRATION(11, "长期封存"),
	CHECKING(12, "校验中"),
	REPAIRING(13, "修理中"),
	CHECKEDING(14, "外借中");
	
	private Integer id;
    private String name;
    
    private StockStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (StockStatusEnum c : StockStatusEnum.values()) {
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
    	StockStatusEnum[] enums = StockStatusEnum.values();
    	
    	for (StockStatusEnum enumItem : enums) {
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
