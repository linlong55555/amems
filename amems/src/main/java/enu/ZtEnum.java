package enu;

/**
 * @author liub
 * @description 状态枚举
 * @develop date 2016.08.18
 */
public enum ZtEnum {
	  NOT_EVALUATED("未评估", 0)
	, EVALUATED("已评估", 1)
	, CHECKED("已审核", 2)
	, APPROVE("已批准", 3)
	, SUSPEND("中止（关闭）", 4)
	, REVIEW_THE_REJECTED("审核驳回", 5)
	, APPROVE_TURN_("审批驳回", 6)
	, CANCELLATION("作废", 8)
	, CLOSE("指定结束", 9)
	;
     
    private String name;
    private Integer id;

    
    private ZtEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (ZtEnum c : ZtEnum.values()) {
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
    
}