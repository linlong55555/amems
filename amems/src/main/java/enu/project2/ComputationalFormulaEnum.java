package enu.project2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 计算公式枚举
 * @author hanwu
 *
 */
public enum ComputationalFormulaEnum {
	FORMULA_1(1, "计划与实际取小加周期"),
	FORMULA_2(2, "实际加周期"),
	FORMULA_3(3, "计划加周期"),
	;
	
	private Integer id;
    private String name;
    
    private ComputationalFormulaEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ComputationalFormulaEnum c : ComputationalFormulaEnum.values()) {
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
    	ComputationalFormulaEnum[] enums = ComputationalFormulaEnum.values();
    	
    	for (ComputationalFormulaEnum enumItem : enums) {
    		Map<String, Object>map = new LinkedHashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
     
}
