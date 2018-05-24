package enu.quality;

public enum QualityAuditReportEnum {
	SAVE(0, "保存"),
	SUBMIT(1, "提交"),
	AUDIT(2, "已审核"),
	APPROVE(3, "已批准"),
	AUDIT_BACK(5, "审核驳回"),
	APPROVE_BACK(6, "审批驳回"),
	CLOSED(9, "关闭");
	
	private Integer id;
	private String name;
	
	private QualityAuditReportEnum(Integer id,String name){
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
        for (QualityAuditReportEnum c : QualityAuditReportEnum.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name();
            }
        }
        return "";
    }
	
}
