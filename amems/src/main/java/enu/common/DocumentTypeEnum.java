package enu.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 文件类型
 * @CreateTime 2018年1月26日 上午9:54:01
 * @CreateBy 韩武
 */
public enum DocumentTypeEnum {
	
	FOLDER("1", "文件夹")
	,FILE("2", "文件")
	;
     
	private String code;
    private String name;

    private DocumentTypeEnum(String code, String name) {
    	this.code = code;
        this.name = name;
    }

    public static String getName(String code) {
        for (DocumentTypeEnum c : DocumentTypeEnum.values()) {
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
 
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	DocumentTypeEnum[] enums = DocumentTypeEnum.values();
    	
    	for (DocumentTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getCode());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
}