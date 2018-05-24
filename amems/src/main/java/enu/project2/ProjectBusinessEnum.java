package enu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 工程技术业务枚举（适用于工程技术通用表的业务类型）
 * @CreateTime 2017年8月16日 下午4:42:42
 * @CreateBy 韩武
 */
public enum ProjectBusinessEnum {
	SHIHA(0, "适航性资料"),
	TECHNICAL_BULLETIN(1, "维护提示"),
	TECHNICAL_ORDER(2, "技术指令"),
	MP(3, "维修方案"),
	NRC(4, "非例行工单"),
	EO(6, "工程指令EO"),
	MEL(7, "MEL"),
	WORK_CARD(8, "工卡"),
	TECHNICAL_ASSESSMENT(9, "技术评估单"),
	WORKORDER(21, "工单"),
	FAILUREKEEP(31, "故障保留"),
	PROJECTEKEEP(32, "项目保留"),
	DEFECTEKEEP(33, "缺陷保留"),
	;
	
	private Integer id;
    private String name;
    
    private ProjectBusinessEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ProjectBusinessEnum c : ProjectBusinessEnum.values()) {
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
    	ProjectBusinessEnum[] enums = ProjectBusinessEnum.values();
    	
    	for (ProjectBusinessEnum enumItem : enums) {
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
