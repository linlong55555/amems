package enu;

/** 
 * @Description 区域和飞机站位的枚举
 * @CreateTime 2017年8月26日 上午11:44:13
 * @CreateBy 李高升	
 */
public enum ZoneStationEnum {
 
	
	  ZONE(1,"区域")
	, STATION(3,"飞机站位")	
	;
	
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
	private ZoneStationEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
}
