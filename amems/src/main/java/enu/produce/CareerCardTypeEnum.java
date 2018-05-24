package enu.produce;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 履历卡类型枚举
 * @CreateTime 2017年9月25日 下午3:01:54
 * @CreateBy 韩武
 */
public enum CareerCardTypeEnum {
	NONE(1, "无履历卡"),
	ORIGINAL(2, "原装履历卡"),
	DIY(3, "自制履历卡"),
	;
	
	private Integer id;
    private String name;
    
    private CareerCardTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (CareerCardTypeEnum c : CareerCardTypeEnum.values()) {
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
	 * @CreateTime 2017年9月23日 下午3:35:46
	 * @CreateBy 韩武
	 * @return
	 */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	CareerCardTypeEnum[] enums = CareerCardTypeEnum.values();
    	
    	for (CareerCardTypeEnum enumItem : enums) {
    		Map<String, Object>map = new LinkedHashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
     
}
