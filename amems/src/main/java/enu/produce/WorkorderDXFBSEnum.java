package enu.produce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description工单145待下发标识
 * @CreateTime 2017-8-17 下午5:59:39
 * @CreateBy 雷伟
 */
public enum WorkorderDXFBSEnum {
	  INITIAL("初始状态", 10)
	, PENDING("待下发", 20)
	, PROCESS("处理中", 30)
	;
	private String name;
	private Integer id;

	private WorkorderDXFBSEnum(String name, Integer id) {
		this.name = name;
		this.id = id;

	}

	public static String getName(Integer id) {
		for (WorkorderDXFBSEnum c : WorkorderDXFBSEnum.values()) {
			if (c.getId().intValue() == id.intValue()) {
				return c.name;
			}
		}
		return "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 枚举转listMap
	 * @return
	 */
	public static List<Map<String, Object>> enumToListMap() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		WorkorderDXFBSEnum[] enums = WorkorderDXFBSEnum.values();

		for (WorkorderDXFBSEnum enumItem : enums) {
			Map<String, Object>map = new HashMap<String, Object>();
			map.put("id", enumItem.getId());
			map.put("name", enumItem.getName());
			list.add(map);
		}

		/*Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return o1.get("name").toString().compareTo(o2.get("name").toString()) ;
			}
		});*/

		return list;
	}
}
