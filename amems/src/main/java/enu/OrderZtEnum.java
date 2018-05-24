package enu;

/**
 * @author liub
 * @description 状态枚举
 * @develop date 2016.08.18
 */
public enum OrderZtEnum {
	  NOT_EVALUATED("保存", 0)
	, EVALUATED("提交", 1)
	, CHECKED("已审核", 2)
	, APPROVE("已批准", 3)
	, SUSPEND("中止（关闭）", 4)
	, REVIEW_THE_REJECTED("审核驳回", 5)
	, APPROVE_TURN_("审批驳回", 6)
	, CANCELLATION("作废", 8)
	, CLOSE("指定结束", 9)
	, ACCOMPLISH("完成", 10)
	;
     
    private String name;
    private Integer id;

    
    private OrderZtEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (OrderZtEnum c : OrderZtEnum.values()) {
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