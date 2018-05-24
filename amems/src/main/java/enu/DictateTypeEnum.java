package enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description 指令类型枚举
 * @develop date 2017.03.14
 */
public enum DictateTypeEnum {
	
	TECHNICAL_BULLETIN("维护提示", 1)
	, TECHNICAL_ORDER("技术指令", 2)
	, AMENDMENT_NOTICE("维修方案", 3)
	, ATTACH_WORK_ORDER("附加工单", 4)
	, TROUBLESHOOTING_WORK_ORDER("排故工单", 5)
	, ENGINEERING_ORDER("工程指令", 6)
	, FLIGHT_DEPT("转交飞行部", 7)
	, AMENDMENT_MEL("修订MEL", 8)
	, AMENDMENT_CARD("修订工卡", 9)
	, OTHER("其它", 10)
	;
     
    private String name;
    private Integer id;

    
    private DictateTypeEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (DictateTypeEnum c : DictateTypeEnum.values()) {
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
    	DictateTypeEnum[] enums = DictateTypeEnum.values();
    	
    	for (DictateTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
    
    public static void main(String[] args) {
    	
    	
    	System.out.println(DictateTypeEnum.enumToListMap());
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