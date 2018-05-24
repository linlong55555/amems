package enu.produce;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum NodeEnum {
NODE1(1,"技术评估填写、EO填写、维护提示填写、技术指令填写、维修方案填写/改版、MEL变更/修订、NRC填写、工卡填写/改版"),
NODE2(2,"技术评估审核、EO审核、维护提示审核、技术指令审核、维修方案审核、MEL审核、岗位授权审核、内审计划审核、工卡审核"),
NODE3(3,"技术评估审批、EO审批、维护提示审批、技术指令审批、维修方案审批、MEL审批、故障保留批准、缺陷保留批准、岗位授权评估、内审计划批准、工卡审批"),
NODE5(5,"驳回"),
NODE11(11,"维修方案提交生产"),
NODE12(12,"维修方案生产确认"),
NODE21(21,"发现问题整改"),
NODE22(22,"问题整改执行"),
NODE23(23,"发现问题评估"),
NODE24(24,"发现问题验证"),
NODE31(31,"上架"),
NODE32(32,"销毁"),
NODE33(33,"质检");
	
private Integer id;	
private String name;

private NodeEnum(Integer id,String name){
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
    for (NodeEnum c : NodeEnum.values()) {
        if (c.getId().intValue() == id.intValue()) {
            return c.name;
        }
    }
    return "";
}

public static List<Map<String, Object>> enumToListMap() {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	NodeEnum[] enums = NodeEnum.values();
	
	for (NodeEnum enumItem : enums) {
		Map<String, Object>map = new LinkedHashMap<String, Object>();
		map.put("id", enumItem.getId());
		map.put("name", enumItem.getName());
		list.add(map);
	}
	
	return list;
}

}
