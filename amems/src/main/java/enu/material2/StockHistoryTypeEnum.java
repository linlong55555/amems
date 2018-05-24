package enu.material2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 库存履历主信息-操作类型
 * @CreateTime 2018年3月19日 上午10:14:24
 * @CreateBy 韩武
 */
public enum StockHistoryTypeEnum {
	STOCK_RECEIPT(10, "收货"),
	STOCK_OUT(20, "出库"),
	STOCK_TRANSFER(30, "移库"),
	STOCK_COUNT(40, "盘点"),
	STOCK_DESTROY (50, "销毁"),
	STOCK_EDIT(990, "库存修改")
	;
	
	
	private Integer id;
    private String name;
    
    private StockHistoryTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (StockHistoryTypeEnum c : StockHistoryTypeEnum.values()) {
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
    	StockHistoryTypeEnum[] enums = StockHistoryTypeEnum.values();
    	
    	for (StockHistoryTypeEnum enumItem : enums) {
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
