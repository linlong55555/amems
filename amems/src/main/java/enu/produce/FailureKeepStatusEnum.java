package enu.produce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description 故障保留单状态枚举
 * @CreateTime 2017年8月17日 下午10:38:25
 * @CreateBy 林龙
 */
public enum FailureKeepStatusEnum {
		SAVE("保存", 1)
		, ASSESSMENT("提交", 2)
		, APPROVAL("已批准", 4)
		, APPROVALDOWN("批准驳回", 6)
		, CLOSE("指定结束", 9)
		, WANCHENG("完成", 10)
		;
	    private String name;
	    private Integer id;

	    
	    private FailureKeepStatusEnum(String name, Integer id) {
	        this.name = name;
	        this.id = id;
	         
	    }

	    public static String getName(Integer id) {
	        for (FailureKeepStatusEnum c : FailureKeepStatusEnum.values()) {
	            if (c.getId().intValue() == id.intValue()) {
	                return c.name;
	            }
	        }
	        return "";
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
		
		 /**
	     * 枚举转listmap
	     * @return
	     */
	    public static List<Map<String, Object>> enumToListMap() {
	    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    	FailureKeepStatusEnum[] enums = FailureKeepStatusEnum.values();
	    	
	    	for (FailureKeepStatusEnum enumItem : enums) {
	    		Map<String, Object>map = new HashMap<String, Object>();
	    		map.put("id", enumItem.getId());
	    		map.put("name", enumItem.getName());
	    		list.add(map);
			}
	    	
	    	/* Collections.sort(list, new Comparator<Map<String, Object>>() {
	    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	    	            return o1.get("name").toString().compareTo(o2.get("name").toString()) ;
	    	        }
	    	    });*/
	    	
	    	return list;
		}
}
