package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description 仓库枚举
 * @develop date 2016.09.12仓库类别：0其他、1待检库、2报废库、3待修库、4危险品、5工具、6航材、7设备、8低值易耗品
 */
public enum StoreTypeEnum {
	OTHER(0, "其它"),
	QUARANTINE_STORE(1, "待检库"),
	DESTROY_STORE(2, "报废库"),
	REPAIRED_STORE(3, "待修库"),
	DANGEROUS_GOODS(4, "危险品"),
	TOOL(5, "工具"),
	MATERIAL(6, "航材"),
	APPOINTED(7, "设备"),
	LOW_PRICE(8, "低值易耗品")
	;
	
	private Integer id;
    private String name;
    
    private StoreTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (StoreTypeEnum c : StoreTypeEnum.values()) {
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
    	StoreTypeEnum[] enums = StoreTypeEnum.values();
    	
    	for (StoreTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	            return o2.get("id").toString().compareTo(o1.get("id").toString()) ;
    	        }
    	    });
    	
    	return list;
	}
     
}
