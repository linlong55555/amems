package enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description 新增修改记录枚举
 * @develop date 2016.08.18
 */
public enum UpdateTypeEnum {
	
	SAVE("新增", 1)
	, UPDATE("修改", 2)
	, DELETE("删除", 3)
	, AUDIT("审核", 4)
	, APPROVE("批准", 5)
	, CLOSE("关闭", 6)
	 
	;
     
    private String name;
    private Integer id;

    
    private UpdateTypeEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (UpdateTypeEnum c : UpdateTypeEnum.values()) {
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
    	UpdateTypeEnum[] enums = UpdateTypeEnum.values();
    	
    	for (UpdateTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
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