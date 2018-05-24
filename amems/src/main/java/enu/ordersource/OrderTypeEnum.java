package enu.ordersource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description Mel状态枚举
 */
public enum OrderTypeEnum {
	JSTG(1,"维护提示")
	, JSZL(2,"技术指令")
	, XDTZS(3,"修订通知书")
	, FLXGD(4,"非例行工单")
	, GCZL(6,"工程指令EO")
	, XDMEL(7,"修订MEL")
	, XDGK(8,"修订工卡")
	;
	
	private Integer id;
    private String name;
    
    private OrderTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (OrderTypeEnum c : OrderTypeEnum.values()) {
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
    	OrderTypeEnum[] enums = OrderTypeEnum.values();
    	
    	for (OrderTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
     
}
