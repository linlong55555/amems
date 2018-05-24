package enu.aerialmaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存履历操作类型
 *
 */
public enum StockHistoryOperationEnum {
sh(10,"收货"),
ck(20,"出库"),
yk(30,"移库"),
pd(40,"盘点"),
xh(50,"销毁"),
kcxg(990,"库存修改"); 


private Integer id;
private String name;
   
   
   
   
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




private StockHistoryOperationEnum(Integer id, String name) {
   	   this.id = id;
       this.name = name;
   }


public static String getName(Integer id) {
    for (StockHistoryOperationEnum c : StockHistoryOperationEnum.values()) {
        if (c.getId().intValue() == id.intValue()) {
            return c.name;
        }
    }
    return "";
}


/**
* 枚举转listmap
* @return
*/
public static List<Map<String, Object>> enumToListMap() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StockHistoryOperationEnum[] enums = StockHistoryOperationEnum.values();
		for (StockHistoryOperationEnum enumItem : enums) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", enumItem.getId());
			map.put("name", enumItem.getName());
			list.add(map);
		}
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return Integer.valueOf(o1.get("id").toString()).compareTo(
						Integer.valueOf(o2.get("id").toString()));
			}
		});

		return list;
}
   
}
