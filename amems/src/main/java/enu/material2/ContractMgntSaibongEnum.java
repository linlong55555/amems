package enu.material2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 合同采番枚举
 * @CreateTime 2018-3-9 上午10:41:45
 * @CreateBy 刘兵
 */
public enum ContractMgntSaibongEnum {
	
	PURCHASE(10, "5_HT1_CG"),//采购
	REPAIR(20, "5_HT3_XL"),//修理
	RENT_IN(31, "5_HT5_ZJ"),//租进
	LEASE(32, "5_HT6_ZC"),//租出
	EXCHANGE(40, "5_HT4_JH"),//交换
	SELL(50, "5_HT2_CS")//出售
	;
	
	
	private Integer id;
    private String name;
    
    private ContractMgntSaibongEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ContractMgntSaibongEnum c : ContractMgntSaibongEnum.values()) {
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
    	ContractMgntSaibongEnum[] enums = ContractMgntSaibongEnum.values();
    	
    	for (ContractMgntSaibongEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return o1.get("id").toString().compareTo(o2.get("id").toString());
			}
		});
    	
    	return list;
	}
     
}
