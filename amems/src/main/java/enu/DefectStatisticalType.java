package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuchao
 * @description 统计类型
 */
public enum DefectStatisticalType {
	/**
	 * 航材缺件统计
	 */
	HC_DEFECT(1, "航材缺件统计") {
		@Override
		public List<String> getHcLxs() {
			List<String> hclxs = new ArrayList<String>();
			hclxs.add("1");
			hclxs.add("5");
			return hclxs;
		}
	},
	/**
	 * 工具缺件统计
	 */
	TOOL_DEFECT(2, "工具缺件统计") {
		@Override
		public List<String> getHcLxs() {
			List<String> hclxs = new ArrayList<String>();
			hclxs.add("2");
			hclxs.add("3");
			return hclxs;
		}
	}
	;
	
	
	private Integer id;
    private String name;
    
    private DefectStatisticalType(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (DefectStatisticalType c : DefectStatisticalType.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name;
            }
        }
        return "";
    }
    
    public abstract List<String> getHcLxs();
    
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
    	DefectStatisticalType[] enums = DefectStatisticalType.values();
    	
    	for (DefectStatisticalType enumItem : enums) {
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
