package enu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description 待办工作类型
 * @CreateTime 2017年9月9日 下午5:20:19
 * @CreateBy 朱超
 */
public enum TodoEnum {
	TECHNICAL_BULLETIN(1, "维护提示"),
	TECHNICAL_ORDER(2, "技术指令"),
	MP(3, "维修方案"),
	NRC(4, "下达工单(维修指令)"),
	EO(6, "工程指令EO"),
	MEL(7, "MEL"),
	WORK_CARD(8, "工卡"),
	TECHNICAL_ASSESSMENT(9, "技术评估单"),
	PRODUCTION_ORDER(10, "生产指令"),
	GZBL(21,"故障保留"),
	QXBL(22,"缺陷保留"),
	GWSQ(41,"岗位授权"),
	ZJ(42,"质检"),
	ZLNSJH(43,"质量内审计划"),
	NSWTZG(44,"内审问题整改"),
	HCSJ(61,"航材上架"),
	GJSJ(62,"工具上架"),
	HCXHXJ(63,"航材销毁下架"),
	GJXHXJ(64,"工具销毁下架"),
	BF(65,"报废");
	;
	
	private Integer id;
    private String name;
    
    private TodoEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (TodoEnum c : TodoEnum.values()) {
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
    	TodoEnum[] enums = TodoEnum.values();
    	
    	for (TodoEnum enumItem : enums) {
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
