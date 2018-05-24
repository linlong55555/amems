package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pingxiaojun
 * @description 修订通知书状态枚举
 * @develop date 2016.08.23
 */

public enum RevisionNoticeBookZtEnum {
	SAVE(0, "保存"),
	SUBMIT(1, "提交"),
	SHENHE(2, "已审核"),
	PIFU(3, "已批准"),
	ZHONGZHI(4, "中止(关闭)"),
	SHENHEBOHUI(5, "审核驳回"),
	PIFUBOHUI(6, "批准驳回"),
	INVALID(8, "作废"),
	CLOSE(9, "指定结束"),
	/*FINISH11(10, "b"),*/
	FINISH(10, "关闭");
	
	
	private Integer id;
    private String name;
    
    private RevisionNoticeBookZtEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (RevisionNoticeBookZtEnum c : RevisionNoticeBookZtEnum.values()) {
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
    	RevisionNoticeBookZtEnum[] enums = RevisionNoticeBookZtEnum.values();
    	
    	for (RevisionNoticeBookZtEnum enumItem : enums) {
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
