package enu;

/**
 * 机务内部审核类型枚举
 * @author hanwu
 *
 */
public enum VerifyMechanismEnum {
	
	NEWMECHANISMREPORT("newMechanismReport","新建机务内部报告"),
	
	FINDMECHANISMREPORT("findMechanismReport","查询机务内部报告"),
	
	NEWMECHANISMAUDIT("newMechanismAudit","新建机务内部审核计划"),
	
	FINDMECHANISMAUDIT("findMechanismAudit","查询机务内部审核计划"),
	
	FINDMECHANISMCHECKREPORT("findMechanismCheckReport","查询机务内部审核检查单")
	;
    private String mark;
    
    private String desc;
    
    private VerifyMechanismEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}