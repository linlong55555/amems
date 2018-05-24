package enu.material2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 收货类型枚举
 * @CreateTime 2018年3月7日 上午9:42:46
 * @CreateBy 韩武
 */
public enum ReceiptTypeEnum {
	PURCHASE(10, "采购"),
	REPAIR(20, "修理"),
	RENT_IN(31, "租进"),
	RENT_OUT(32, "租出"),
	EXCHANGE(40, "交换"),
	RETURN_MATERIAL(60, "退料"),
	OTHER(90, "其他")
	;
	
	
	private Integer id;
    private String name;
    
    private ReceiptTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ReceiptTypeEnum c : ReceiptTypeEnum.values()) {
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
    	ReceiptTypeEnum[] enums = ReceiptTypeEnum.values();
    	
    	for (ReceiptTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
     
}
