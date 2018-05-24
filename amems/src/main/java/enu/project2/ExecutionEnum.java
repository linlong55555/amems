package enu.project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 执行方式枚举
 * @CreateTime 2017-9-27 下午5:36:47
 * @CreateBy 刘兵
 */
public enum ExecutionEnum {
	SINGLE("单次", 1)
	, REPEAT("重复", 2)
	, SECTION("分段", 3)
	;
	private String name;
	private Integer id;

	private ExecutionEnum(String name, Integer id) {
		this.name = name;
		this.id = id;

	}

	public static String getName(Integer id) {
		for (ExecutionEnum c : ExecutionEnum.values()) {
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
		ExecutionEnum[] enums = ExecutionEnum.values();

		for (ExecutionEnum enumItem : enums) {
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
	
	/**
	 * 
	 * @Description 是EO单次或分段
	 * @CreateTime 2017年10月14日 下午2:25:14
	 * @CreateBy 朱超
	 * @param val
	 * @return
	 */
	public static Boolean isEoOnceOrSection(String val) {
		if ((null != val) && (SINGLE.getId().equals(val) || SECTION.getId().equals(val))) {
			return Boolean.TRUE;
		}
		else {
			return Boolean.FALSE;
		}
		 
	}
}
