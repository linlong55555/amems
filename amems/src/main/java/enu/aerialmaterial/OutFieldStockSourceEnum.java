package enu.aerialmaterial;

/**
 * @Description 外场库存数据来源
 * @CreateTime 2017年10月20日 下午3:09:01
 * @CreateBy 徐勇
 */
public enum OutFieldStockSourceEnum {
	REQUISITION(1, "领用"),
	REMOVE(2, "拆解")
	;
	
	private Integer id;
    private String name;
    
    private OutFieldStockSourceEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
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
