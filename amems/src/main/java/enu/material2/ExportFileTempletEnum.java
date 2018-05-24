package enu.material2;


/**
 * @Description 导出文件模板枚举
 * @CreateTime 2018-3-28 下午2:02:09
 * @CreateBy 刘兵
 */
public enum ExportFileTempletEnum {
	PURCHASE(10, 1, "purchase"),
	PURCHASE_Y(10, 2, "purchase_y"),
	REPAIR(20, 1, "repair"),
	REPAIR_Y(20, 2, "repair_y"),
	EXCHANGE_Y(40, 2, "exchange_y"),
	MONTHLYREPORT(100, 1, "monthlyreport"),
	;
	
	
	private Integer id;
    private Integer type;
    private String name;
    
    private ExportFileTempletEnum(Integer id, Integer type, String name) {
    	this.id = id;
        this.type = type;
        this.name = name;
    }
    
    public static String getName(Integer id, Integer type) {
        for (ExportFileTempletEnum c : ExportFileTempletEnum.values()) {
            if (c.getId().intValue() == id.intValue() && c.getType().intValue() == type.intValue()) {
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
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
