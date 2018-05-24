package enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 飞行记录单状态枚举
 * @author hanwu
 *
 */
public enum FlightRecordSheetStatusEnum {
	SAVE(1, "保存"),
	SUBMIT(2, "提交"),
	INVALID(8, "作废"),
	CLOSE(9, "关闭"),
	FINISH(10, "完成"),
	AMENDMENTVOID(11, "修订作废"),
	REVISE(12, "修订");
	
	private Integer id;
    private String name;
    
    private FlightRecordSheetStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (FlightRecordSheetStatusEnum c : FlightRecordSheetStatusEnum.values()) {
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
    	FlightRecordSheetStatusEnum[] enums = FlightRecordSheetStatusEnum.values();
    	
    	for (FlightRecordSheetStatusEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
}

