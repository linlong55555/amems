package enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AnnunciateZtEnum {
	SAVE(0, "保存"),
	SUBMIT(1, "提交"),
	SHENHE(2, "审核"),
	PIFU(3, "批复"),
	ZHONGZHI(4, "中止"),
	REVIEW_THE_REJECTED(5, "审核驳回"),
	APPROVE_TURN_(6, "审批驳回"),
	INVALID(8, "作废"),
	CLOSE(9, "指定结束"),
	FINISH(10, "完成");
	
	private Integer id;
    private String name;
    
    private AnnunciateZtEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (AnnunciateZtEnum c : AnnunciateZtEnum.values()) {
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
    	AnnunciateZtEnum[] enums = AnnunciateZtEnum.values();
    	
    	for (AnnunciateZtEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
}

