package enu;

/**
 * 部件类型
 * @author hanwu
 *
 */
public enum ComponentTypeEnum {
	
	COMPONENT("部件", 1) , 
	WHOLE("整机", 2)
	; 
     
    private String name;
    private Integer code;

    
    private ComponentTypeEnum(String name, Integer code) {
        this.name = name;
        this.setCode(code);
    }

    public static String getName(Integer code) {
        for (ComponentTypeEnum c : ComponentTypeEnum.values()) {
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