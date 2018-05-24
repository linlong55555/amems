package enu.produce;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 装机位置枚举
 * @CreateTime 2017年9月23日 下午3:34:56
 * @CreateBy 韩武
 */
public enum InstalledPositionEnum {
	BODY(11, "机身"),
	ENG1(21, "1#发"),
	ENG2(22, "2#发"),
	ENG3(23, "3#发"),
	ENG4(24, "4#发"),
	APU(31, "APU"),
	;
	
	private Integer id;
    private String name;
    
    private InstalledPositionEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (InstalledPositionEnum c : InstalledPositionEnum.values()) {
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
	 * @Description 枚举转listmap
	 * @CreateTime 2017年9月23日 下午3:35:46
	 * @CreateBy 韩武
	 * @return
	 */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	InstalledPositionEnum[] enums = InstalledPositionEnum.values();
    	
    	for (InstalledPositionEnum enumItem : enums) {
    		Map<String, Object>map = new LinkedHashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
     
	/**
	 * 
	 * @Description 是否发动机位置
	 * @CreateTime 2017年10月11日 上午11:46:27
	 * @CreateBy  徐勇
	 * @param wz
	 * @return
	 */
	public static boolean isEngine(Integer wz) {
		return wz==ENG1.getId() || wz==ENG2.getId() || wz==ENG3.getId() || wz==ENG4.getId();
	}
}
