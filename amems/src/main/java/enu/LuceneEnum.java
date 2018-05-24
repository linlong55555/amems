package enu;

/**
 * @author liub
 * @description lucene全文检索枚举
 * @develop date 2017.02.04
 */
public enum LuceneEnum {

	/** 维修档案 */
	MAINTENANCE_RECORD(1,"维修档案", "maintenance")
	;
	
	private Integer id;
	private String description;
	//文件夹名称
	private String name;
	
	private LuceneEnum(Integer id, String description, String name) {
		this.id = id;
		this.description = description;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
