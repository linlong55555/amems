package enu.produce;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum UndoStatusEnum {
DCL(1,"待处理"),
YCL(2,"已处理");

private Integer id;
private String name;

private UndoStatusEnum(Integer id,String name){
	this.id=id;
	this.name=name;
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

public static String getName(Integer id) {
    for (UndoStatusEnum c : UndoStatusEnum.values()) {
        if (c.getId().intValue() == id.intValue()) {
            return c.name;
        }
    }
    return "";
}

public static List<Map<String, Object>> enumToListMap() {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	UndoStatusEnum[] enums = UndoStatusEnum.values();
	
	for (UndoStatusEnum enumItem : enums) {
		Map<String, Object>map = new LinkedHashMap<String, Object>();
		map.put("id", enumItem.getId());
		map.put("name", enumItem.getName());
		list.add(map);
	}
	
	return list;
}

}
