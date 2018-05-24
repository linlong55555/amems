package enu.produce;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 入库操作类型枚举
 * @CreateTime 2017年9月25日 下午3:01:54
 * @CreateBy 孙霁
 */
public enum StorageTypeEnum {
	ZHUANGSHANG(1, "装上"),
	CAIXIA(2, "拆下"),
	BAOFEI(5, "报废"),
	RUKU(10, "入库"),
	CAIGOURUKU(11, "采购入库"),
	SONGXIURUKU(12, "送修入库"),
	JIEYONGRUKU(13, "借用入库"),
	GUIHUANRUKU(14, "归还入库"),
	CHUKU(20, "出库"),
	LINGYONGCHUKU(21, "领用出库"),
	SONGXIUCHUKU(22, "送修出库"),
	GUIHUANCHUKU(23, "归还出库"),
	JIEDIAOCHUKU(24, "借调出库"),
	;
	private Integer id;
    private String name;
    
    private StorageTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (StorageTypeEnum c : StorageTypeEnum.values()) {
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
    	StorageTypeEnum[] enums = StorageTypeEnum.values();
    	
    	for (StorageTypeEnum enumItem : enums) {
    		Map<String, Object>map = new LinkedHashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
     
}
