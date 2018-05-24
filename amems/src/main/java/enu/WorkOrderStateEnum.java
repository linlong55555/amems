package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author meizhiliang
 * @description 工单状态枚举
 * @develop date 2016.09.12
 */
public enum WorkOrderStateEnum {
	 SAVE_WO(0, "保存"),
	 SUBMIT_WO(1, "提交"),
	YISHENHE_WO(2, "已审核"),
	YIPIZHUN_WO(3, "已批准"),
	SHOUTDOWN_WO(4, "中止（关闭）"),
	SHENHEBOHUI_WO(5, "审核驳回"),
	SHENPIBOHUI_WO(6, "批准驳回"),
	ZUOFEI(8, "作废"),
	GUANBI(9, "指定结束"),
	WANCHEN(10, "完成"),
	REVOKE(11, "撤销"),
	;
	
	private Integer id;
    private String name;
    
    private WorkOrderStateEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (WorkOrderStateEnum c : WorkOrderStateEnum.values()) {
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
    	WorkOrderStateEnum[] enums = WorkOrderStateEnum.values();
    	
    	for (WorkOrderStateEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	            return o1.get("name").toString().compareTo(o2.get("name").toString()) ;
    	        }
    	    });
    	
    	return list;
	}
     
}
