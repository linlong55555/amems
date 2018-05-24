package enu.material2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 工具/设备借还状态枚举
 * @CreateTime 2018年4月2日 下午4:07:29
 * @CreateBy 韩武
 */
public enum ToolBorrowReturnTypeEnum {
	BORROW("10", "借用"),
	RETURN("20", "归还"),
	;
	
	
	private String id;
    private String name;
    
    private ToolBorrowReturnTypeEnum(String id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(String id) {
        for (ToolBorrowReturnTypeEnum c : ToolBorrowReturnTypeEnum.values()) {
            if (c.getId().equals(id)) {
                return c.name;
            }
        }
        return "";
    }
    
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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
    	ToolBorrowReturnTypeEnum[] enums = ToolBorrowReturnTypeEnum.values();
    	
    	for (ToolBorrowReturnTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
     
}
