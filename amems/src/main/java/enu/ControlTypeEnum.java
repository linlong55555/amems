package enu;

/**
 * 控制类型
 * @author hanwu
 *
 */
public enum ControlTypeEnum {
	
	
	TIME_CONTROL("时控件", 1) , 
	TIME_LIMITED("时寿件", 2) ,
	NON_CONTROL("非控制件", 3)
	; 
	
     
    private String name;
    private Integer code;

    
    private ControlTypeEnum(String name, Integer code) {
        this.name = name;
        this.setCode(code);
    }

    public static String getName(Integer code) {
        for (ControlTypeEnum c : ControlTypeEnum.values()) {
            if (c.getCode().intValue() == code.intValue()) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

    
}