package enu.produce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @Description 工包状态
 * @CreateTime 2017年9月27日 下午2:28:57
 * @CreateBy 岳彬彬
 */
public enum WorkpackageStatusEnum {
		  SAVE("保存(预组包)", 1)
		, SUBMIT("提交", 2)
		, TAKEEFFECT("生效(下发)", 7)
		, CLOSETOEND("指定结束", 9)
		, CLOSETOFINISH("完工关闭", 10)
		;
	    private String name;
	    private Integer id;

	    
	    private WorkpackageStatusEnum(String name, Integer id) {
	        this.name = name;
	        this.id = id;
	         
	    }

	    public static String getName(Integer id) {
	        for (WorkpackageStatusEnum c : WorkpackageStatusEnum.values()) {
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
	    	WorkpackageStatusEnum[] enums = WorkpackageStatusEnum.values();
	    	
	    	for (WorkpackageStatusEnum enumItem : enums) {
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
