package enu.training;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description 计划状态枚举
 */
public enum TrainingPlanStatusEnum {
	SAVE(0,"保存")
	, LSSUED(1,"下发")
	, CANCEL(8,"作废")
	, CLOSE(9,"指定结束")
	, FINISH(10,"完成")
	;
	
	private Integer id;
    private String name;
    
    private TrainingPlanStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (TrainingPlanStatusEnum c : TrainingPlanStatusEnum.values()) {
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
    	TrainingPlanStatusEnum[] enums = TrainingPlanStatusEnum.values();
    	
    	for (TrainingPlanStatusEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
     
}
