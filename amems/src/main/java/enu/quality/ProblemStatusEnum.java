package enu.quality;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 发现问题状态枚举
 * @CreateTime 2018年1月8日 下午2:32:32
 * @CreateBy 韩武
 */
public enum ProblemStatusEnum {
	SAVE(0, "保存"),
	SUBMIT(1, "提交（下发）"),
	EXECUTE(2, "已执行"),
	FEEDBACK(3, "已反馈"),
	TO_BE_VERIFIED(4, "待验证"),
	TO_BE_CLOSED(5, "待关闭"),
	REJECT(6, "评估驳回"),
	CLOSED(9, "关闭"),
	;
	
	private Integer id;
    private String name;
    
    private ProblemStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ProblemStatusEnum c : ProblemStatusEnum.values()) {
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
    	ProblemStatusEnum[] enums = ProblemStatusEnum.values();
    	
    	for (ProblemStatusEnum enumItem : enums) {
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
