package enu.aerialmaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 航材提订单状态枚举
 * @author xu.yong
 *
 */
public enum RequisitionStatusEnum {
	SAVED(1, "保存", true),
	SUBMITED(2, "提交", true),
	INVALID(8, "作废", false),
	CLOSED(9, "关闭", true),
	FINISHED(10, "完成", true)
	;
	
	
	private Integer id;
    private String name;
    private Boolean isShow;//是否在界面显示
    
    private RequisitionStatusEnum(Integer id, String name, Boolean isShow) {
    	this.id = id;
        this.name = name;
        this.isShow = isShow;
    }
    
    public static String getName(Integer id) {
        for (RequisitionStatusEnum c : RequisitionStatusEnum.values()) {
            if (c.getId().intValue() == id.intValue()) {
            	if(id.intValue() == 9){
            		return "指定结束";
            	}
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

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	/**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	RequisitionStatusEnum[] enums = RequisitionStatusEnum.values();
    	
    	for (RequisitionStatusEnum enumItem : enums) {
    		if(enumItem.getIsShow()){
    			Map<String, Object>map = new HashMap<String, Object>();
    			map.put("id", enumItem.getId());
    			map.put("name", enumItem.getName());
    			list.add(map);
    		}
		}
    	
    	Collections.sort(list, new Comparator<Map<String, Object>>() {
	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	            return Integer.valueOf(o1.get("id").toString()).compareTo(Integer.valueOf(o1.get("id").toString())) ;
	        }
	    });
    	
    	return list;
	}
     
}
