package enu.produce;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 同步状态
 * @CreateTime 2017年10月9日 上午9:55:42
 * @CreateBy 韩武
 */
public enum SynchronzeStatusEnum {
	
	
	SYNCHRONZED("无需同步", 0) , 
	TO_BE_SYNCHRONZE("修改待同步", 1)
	; 
	
     
    private String name;
    private Integer code;

    
    private SynchronzeStatusEnum(String name, Integer code) {
        this.name = name;
        this.setCode(code);
    }

    public static String getName(Integer code) {
        for (SynchronzeStatusEnum c : SynchronzeStatusEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
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
}