package enu.project2;

/**
 * @Description 客户项目信息枚举
 * @CreateTime 2017-9-30 上午10:10:39
 * @CreateBy 甘清
 */
public enum CustProjectEnum {
	ST_1(1, "保存"), 
	ST_2(2, "提交"), 
	ST_9(9, "关闭"), 
	ST_10(10, "完成"), 
	CUST_TYPE_1(1, "是"),  //外部标识：0否、1是
	CUST_TYPE_0(0, "否");

	private Integer id;
	private String name;

	private CustProjectEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public static String getName(Integer id) {
		for (CustProjectEnum c : CustProjectEnum.values()) {
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

}
