package enu.mel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description Mel状态枚举
 */
public enum MelStatusEnum {
	
	SAVE(1,"保存")
	, SUBMIT(2,"提交")
	, AUDITED(3,"已审核")
	, APPROVED(4,"已批准")
	, AUDIT_DISMISSED(5,"审核驳回")
	, APPROVED_DISMISSED(6,"批准驳回")
	, CANCEL(8,"作废")
	, CLOSE(9,"指定结束")
	, FINISH(10,"完成")
	;
	
	private Integer id;
    private String name;
    
    private MelStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (MelStatusEnum c : MelStatusEnum.values()) {
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
    	MelStatusEnum[] enums = MelStatusEnum.values();
    	
    	for (MelStatusEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
     
}
