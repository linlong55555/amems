package enu.project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 生产指令状态枚举
 * @CreateTime 2018年5月3日 下午4:36:26
 * @CreateBy 韩武
 */
public enum ProductionOrderStatusEnum {
	SAVE("保存", 1)
	, SUBMIT("提交", 2)
	, AUDITED("已审核", 3)
	, APPROVED("已批准", 4)
	, AUDITDOWN("审核驳回", 5)
	, APPROVALDOWN("批准驳回", 6)
	, TAKEEFFECT("生效", 7)
	, CLOSE("关闭", 9)
	;
	private String name;
	private Integer id;

	private ProductionOrderStatusEnum(String name, Integer id) {
		this.name = name;
		this.id = id;

	}

	public static String getName(Integer id) {
		for (ProductionOrderStatusEnum c : ProductionOrderStatusEnum.values()) {
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
		ProductionOrderStatusEnum[] enums = ProductionOrderStatusEnum.values();

		for (ProductionOrderStatusEnum enumItem : enums) {
			Map<String, Object>map = new HashMap<String, Object>();
			map.put("id", enumItem.getId());
			map.put("name", enumItem.getName());
			list.add(map);
		}

		return list;
	}
}
