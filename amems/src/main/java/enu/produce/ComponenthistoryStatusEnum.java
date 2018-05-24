package enu.produce;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 履历当前状态枚举
 * @CreateTime 2017年9月25日 下午3:01:54
 * @CreateBy 孙霁
 */
public enum ComponenthistoryStatusEnum {
	ZAIJI(1, "在机"),
	WAICHANG(2, "外场"),
	ZAIKU(3, "在库"),
	ZAIXIU(4, "送修"),
	BAOCUO(5, "报废"),
	JIECHU(6, "借出"),
	DIY(7, "销售"),
	;
	
	private Integer id;
    private String name;
    
    private ComponenthistoryStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ComponenthistoryStatusEnum c : ComponenthistoryStatusEnum.values()) {
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
    	ComponenthistoryStatusEnum[] enums = ComponenthistoryStatusEnum.values();
    	
    	for (ComponenthistoryStatusEnum enumItem : enums) {
    		Map<String, Object>map = new LinkedHashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
     
}
