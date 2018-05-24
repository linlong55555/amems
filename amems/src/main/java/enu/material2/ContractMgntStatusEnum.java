package enu.material2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 合同状态枚举
 * @CreateTime 2018-3-12 上午11:12:27
 * @CreateBy 刘兵
 */
public enum ContractMgntStatusEnum {
	SAVE(1, "保存"),
	SUBMIT(2, "提交"),
	HEISHI(4, "批准"),
	CLOSE(9, "指定结束"),
	FINISH(10, "完成"),
	REVISION(12, "修订")
	;
	
	
	private Integer id;
    private String name;
    
    private ContractMgntStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ContractMgntStatusEnum c : ContractMgntStatusEnum.values()) {
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
    	ContractMgntStatusEnum[] enums = ContractMgntStatusEnum.values();
    	
    	for (ContractMgntStatusEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
//    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
//    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
//    	            return o1.get("name").toString().compareTo(o2.get("name").toString()) ;
//    	        }
//    	    });
    	
    	return list;
	}
     
}
