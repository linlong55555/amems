package enu;

/**
 * 节点层级
 * @author hanwu
 *
 */
public enum NodeLevelEnum {
	
	LEVEL_ROOT("根节点", 0) , 
	LEVEL_1("1级节点", 1),
	LEVEL_2("2级节点", 2)
	; 
     
    private String name;
    private Integer code;

    
    private NodeLevelEnum(String name, Integer code) {
        this.name = name;
        this.setCode(code);
    }

    public static String getName(Integer code) {
        for (NodeLevelEnum c : NodeLevelEnum.values()) {
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