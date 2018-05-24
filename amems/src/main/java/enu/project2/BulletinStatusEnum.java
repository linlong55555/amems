package enu.project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @Description 技术通告状态
 * @CreateTime 2017年8月15日 下午2:30:04
 * @CreateBy 岳彬彬
 */
public enum BulletinStatusEnum {
		  SAVE("保存", 1)
		, SUBMIT("提交", 2)
		, AUDIT("已审核", 3)
		, APPROVAL("已批准", 4)
		, AUDITDOWN("审核驳回", 5)
		, APPROVALDOWN("批准驳回", 6)
		, CLOSED("指定结束", 9)
		, FINISHED("完成", 10)
		;
	    private String name;
	    private Integer id;

	    
	    private BulletinStatusEnum(String name, Integer id) {
	        this.name = name;
	        this.id = id;
	         
	    }

	    public static String getName(Integer id) {
	        for (BulletinStatusEnum c : BulletinStatusEnum.values()) {
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
	    	BulletinStatusEnum[] enums = BulletinStatusEnum.values();
	    	
	    	for (BulletinStatusEnum enumItem : enums) {
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
